package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.xk.xcode.config.XcodeInfraProperties;
import cn.xk.xcode.core.OssClient;
import cn.xk.xcode.entity.UploadResult;
import cn.xk.xcode.entity.dto.QuerySysFilesDto;
import cn.xk.xcode.entity.po.SysFilesPo;
import cn.xk.xcode.entity.po.SysFilesProcessPo;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.entity.vo.SysFilesVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.SysFilesMapper;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.SysFilesProcessService;
import cn.xk.xcode.service.SysFilesService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.time.LocalDateTime;

import static cn.xk.xcode.config.InfraGlobalErrorCodeConstants.*;
import static cn.xk.xcode.entity.def.SysFilesProcessTableDef.SYS_FILES_PROCESS_PO;
import static cn.xk.xcode.entity.def.SysFilesTableDef.SYS_FILES_PO;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-06-26
 */
@Service
@Slf4j
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFilesPo> implements SysFilesService {

    @Resource
    private OssClient ossClient;

    @Resource
    private SysFilesProcessService sysFilesProcessService;

    @Resource
    private XcodeInfraProperties xcodeInfraProperties;

    @Override
    public FileResultVo uploadFile(MultipartFile file, String bucket, String username, Boolean isNeedConvertToMp4) {
        if (ObjectUtil.isNull(file)) {
            ExceptionUtil.castServerException(UPLOAD_FILE_IS_EMPTY);
        }
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            ExceptionUtil.castServerException(UPLOAD_FILE_NAME_IS_EMPTY);
        }
        UploadResult result = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            result = ossClient.upload(file, bucket);
        } catch (Exception e) {
            log.error("上传文件失败：{}", e.getMessage());
            ExceptionUtil.castServerException(UPLOAD_FILE_ERROR);
        }
        SysFilesPo sysFilesPo = new SysFilesPo();
        sysFilesPo.setId(MD5.create().digestHex(inputStream));
        sysFilesPo.setFileName(originalFilename);
        sysFilesPo.setBucket(bucket);
        sysFilesPo.setObjectName(result.getObjectName());
        sysFilesPo.setFileUrl(result.getUrl());
        sysFilesPo.setContentType(result.getContextType());
        sysFilesPo.setFileSize(result.getSize());
        sysFilesPo.setETag(result.getETag());
        sysFilesPo.setCreateUser(username);
        save(sysFilesPo);
        if (isNeedConvertToMp4) {
            SysFilesProcessPo sysFilesProcessPo = new SysFilesProcessPo();
            sysFilesProcessPo.setFileId(sysFilesPo.getId());
            sysFilesProcessPo.setFileName(sysFilesPo.getFileName());
            sysFilesProcessPo.setBucket(bucket);
            sysFilesProcessPo.setObjectName(sysFilesPo.getObjectName());
            sysFilesProcessPo.setStatus("0");
            sysFilesProcessPo.setFailCount(0);
            sysFilesProcessPo.setUploadTime(LocalDateTime.now());
            sysFilesProcessService.save(sysFilesProcessPo);
        }
        FileResultVo fileResultVo = new FileResultVo();
        fileResultVo.setFileId(sysFilesPo.getId());
        fileResultVo.setFilePath(sysFilesPo.getFileUrl());
        return fileResultVo;
    }

    @Override
    public Boolean delFile(String fileId) {
        SysFilesPo sysFilesPo = getById(fileId);
        if (ObjectUtil.isNull(sysFilesPo)) {
            return true;
        }
        if (sysFilesProcessService.exists(SYS_FILES_PROCESS_PO.FILE_ID
                .eq(fileId)
                .and(SYS_FILES_PROCESS_PO.STATUS.eq("0")
                        .or(SYS_FILES_PROCESS_PO.STATUS
                                .eq("3")
                                .and(SYS_FILES_PROCESS_PO.FAIL_COUNT
                                        .lt(xcodeInfraProperties.getFileProcessMaxFailCount())))))) {
            ExceptionUtil.castServerException(FILE_HAS_PROCESSING);
        }
        CommonResult<Boolean> commonResult = ossClient.deleteFile(sysFilesPo.getBucket(), sysFilesPo.getObjectName());
        if (!commonResult.isSuccess()) {
            ExceptionUtil.castServerException(DELETE_FILE_FAIL);
        }
        return removeById(fileId);
    }

    @Override
    public Boolean updateFile(MultipartFile file, String fileId) {
        SysFilesPo sysFilesPo = getById(fileId);
        if (ObjectUtil.isNull(sysFilesPo)) {
            ExceptionUtil.castServerException(FILE_NOT_EXISTS);
        }
        CommonResult<Boolean> commonResult = ossClient.deleteFile(sysFilesPo.getBucket(), sysFilesPo.getObjectName());
        if (!commonResult.isSuccess()) {
            ExceptionUtil.castServerException(UPDATE_FILE_ERROR);
        }
        UploadResult result = null;
        try {
            result = ossClient.upload(file, sysFilesPo.getBucket());
        } catch (Exception e) {
            log.error("上传文件失败：{}", e.getMessage());
            ExceptionUtil.castServerException(UPDATE_FILE_ERROR);
        }
        sysFilesPo.setFileName(file.getOriginalFilename());
        sysFilesPo.setObjectName(result.getObjectName());
        sysFilesPo.setFileUrl(result.getUrl());
        sysFilesPo.setContentType(result.getContextType());
        sysFilesPo.setFileSize(result.getSize());
        sysFilesPo.setETag(result.getETag());
        return updateById(sysFilesPo);
    }

    @Override
    public void downloadFile(String fileId, HttpServletResponse response) {
        SysFilesPo sysFilesPo = getById(fileId);
        if (ObjectUtil.isNull(sysFilesPo)) {
            ExceptionUtil.castServerException(FILE_NOT_EXISTS);
        }
        try {
            ossClient.download(sysFilesPo.getBucket(), sysFilesPo.getObjectName(), sysFilesPo.getFileName(), sysFilesPo.getETag(), response);
        } catch (Exception e) {
            log.error("下载文件失败：{}", e.getMessage());
            ExceptionUtil.castServerException(DOWNLOAD_FILE_ERROR);
        }
    }

    @Override
    public PageResult<SysFilesVo> querySysFiles(QuerySysFilesDto querySysFilesDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYS_FILES_PO.ALL_COLUMNS)
                .from(SYS_FILES_PO)
                .where("1=1")
                .and(SYS_FILES_PO.ID.eq(querySysFilesDto.getFileId()).when(StrUtil.isNotBlank(querySysFilesDto.getFileId())))
                .and(SYS_FILES_PO.BUCKET.likeRight(querySysFilesDto.getBucket()).when(StrUtil.isNotBlank(querySysFilesDto.getBucket())))
                .and(SYS_FILES_PO.FILE_NAME.likeRight(querySysFilesDto.getFileName()).when(StrUtil.isNotBlank(querySysFilesDto.getFileName())))
                .and(SYS_FILES_PO.OBJECT_NAME.likeRight(querySysFilesDto.getObjectName()).when(StrUtil.isNotBlank(querySysFilesDto.getObjectName())))
                .and(SYS_FILES_PO.CONTENT_TYPE.likeRight(querySysFilesDto.getContentType()).when(StrUtil.isNotBlank(querySysFilesDto.getContentType())))
                .and(SYS_FILES_PO.CREATE_TIME.le(querySysFilesDto.getEndTime()).when(ObjectUtil.isNotNull(querySysFilesDto.getEndTime())))
                .and(SYS_FILES_PO.CREATE_TIME.ge(querySysFilesDto.getStartTime()).when(ObjectUtil.isNotNull(querySysFilesDto.getStartTime())));
        Page<SysFilesPo> flexPage = PageUtil.toFlexPage(querySysFilesDto);
        Page<SysFilesPo> page = this.page(flexPage, queryWrapper);
        return PageUtil.toPageResult(page, SysFilesVo.class);
    }
}
