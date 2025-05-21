package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.entity.po.SysFilesProcessPo;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.service.MinioService;
import cn.xk.xcode.service.SysFilesProcessService;
import cn.xk.xcode.utils.MinioFileUtils;
import cn.xk.xcode.utils.ip.IpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SysFilesPo;
import cn.xk.xcode.mapper.SysFilesMapper;
import cn.xk.xcode.service.SysFilesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static cn.xk.xcode.config.InfraGlobalErrorCodeConstants.*;
import static cn.xk.xcode.entity.def.SysFilesProcessTableDef.SYS_FILES_PROCESS_PO;

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
    private SysFilesProcessService sysFilesProcessService;

    @Resource
    private MinioService minioService;

    @Override
    public FileResultVo uploadFile(UploadFileDto uploadFileDto) {
        MultipartFile file = uploadFileDto.getFile();
        if (ObjectUtil.isNull(file)) {
            ExceptionUtil.castServerException(UPLOAD_FILE_IS_EMPTY);
        }
        String filename = file.getOriginalFilename();
        if (ObjectUtil.isNull(filename)) {
            ExceptionUtil.castServerException(UPLOAD_FILE_NAME_IS_EMPTY);
        }
        File tempFile = null;
        try {
            tempFile = File.createTempFile(filename, ".temp");
            file.transferTo(tempFile);
        } catch (Exception e) {
            log.error("创建临时文件失败:{}", e.getMessage());
            ExceptionUtil.castServerException(UPLOAD_FILE_ERROR);
        }
        String fileMd5Id = MinioFileUtils.getFileMd5Id(tempFile);
        String objectName = MinioFileUtils.getObjectName(filename, fileMd5Id);
        Boolean b = minioService.uploadFile(tempFile, objectName, uploadFileDto.getBucketType().getType(), file.getContentType());
        if (!b) {
            ExceptionUtil.castServerException(UPLOAD_FILE_ERROR);
        }
        // 插入数据库
        SysFilesPo filesPo = new SysFilesPo();
        filesPo.setFileName(filename);
        filesPo.setFilePath(objectName);
        filesPo.setFileSize(file.getSize());
        filesPo.setFileType(file.getContentType());
        filesPo.setId(fileMd5Id);
       // filesPo.setCreateUser(StpUtil.getLoginIdAsString());
        filesPo.setBucket(uploadFileDto.getBucketType().getType());
        this.save(filesPo);
        if (uploadFileDto.isNeedConvertToMp4()) {
            // 判断文件是否是视频文件
            String ext = filename.substring(filename.lastIndexOf(".") + 1);
            if ("avi".equals(ext) || "mp3".equals(ext) || "rmvb".equals(ext) || "wmv".equals(ext) || "mkv".equals(ext) || "flv".equals(ext) || "mov".equals(ext) || "rm".equals(ext) || "3gp".equals(ext) || "mpeg".equals(ext) || "mpg".equals(ext)) {
                SysFilesProcessPo sysFilesProcessPo = new SysFilesProcessPo();
                sysFilesProcessPo.setFileName(filename);
                sysFilesProcessPo.setFileId(fileMd5Id);
                sysFilesProcessPo.setFilePath(objectName);
                sysFilesProcessPo.setStatus("0");
                sysFilesProcessPo.setBucket(uploadFileDto.getBucketType().getType());
                sysFilesProcessService.save(sysFilesProcessPo);
                // 这里可以考虑使用消息队列异步处理也行 或者使用线程池异步 不需要使用定时任务
                // 定时任务更适合做一些补偿
            }
        }
        // 这里的filename 一定要是本服务器的ip地址
        String hostIp = IpUtil.getHostIp();
        return FileResultVo.builder().fileId(fileMd5Id).filePath("http://" + hostIp + ":9000" + "/" + objectName).build();
    }

    @Override
    public Boolean delFile(String fileId) {
        SysFilesPo filesPo = getById(fileId);
        if (ObjectUtil.isNull(filesPo)) {
            throw new ServiceException(FILE_NOT_EXISTS);
        }
        boolean b = minioService.delFile(filesPo.getBucket(), filesPo.getFilePath());
        if (!b) {
            throw new ServerException(DEL_FILE_ERROR);
        }
        return removeById(fileId);
    }



    @Transactional
    @Override
    public Boolean updateFile(UpdateFileDto updateFileDto) {
        MultipartFile file = updateFileDto.getFile();
        if (ObjectUtil.isNull(file)) {
            ExceptionUtil.castServerException(UPLOAD_FILE_IS_EMPTY);
        }
        String filename = file.getOriginalFilename();
        if (ObjectUtil.isNull(filename)) {
            ExceptionUtil.castServerException(UPLOAD_FILE_NAME_IS_EMPTY);
        }
        // 1. 获取原来的数据文件
        SysFilesPo filesPo = getById(updateFileDto.getFileId());
        if (ObjectUtil.isNull(filesPo)) {
            ExceptionUtil.castServerException(FILE_NOT_EXISTS);
        }
        boolean b = minioService.delFile(filesPo.getBucket(), filesPo.getFilePath());
        if (!b) {
            ExceptionUtil.castServerException(UPDATE_FILE_ERROR);
        }
        File tempFile = null;
        try {
            tempFile = File.createTempFile(filename, ".temp");
            file.transferTo(tempFile);
        } catch (Exception e) {
            log.error("创建临时文件失败:{}", e.getMessage());
            ExceptionUtil.castServerException(UPDATE_FILE_ERROR);
        }
        String fileMd5Id = MinioFileUtils.getFileMd5Id(tempFile);
        String objectName = MinioFileUtils.getObjectName(filename, fileMd5Id);
        b = minioService.uploadFile(tempFile, objectName, filesPo.getBucket(), file.getContentType());
        if (!b) {
            ExceptionUtil.castServerException(UPDATE_FILE_ERROR);
        }
        removeById(filesPo);
        QueryWrapper wrapper = QueryWrapper.create().where(SYS_FILES_PROCESS_PO.FILE_ID.eq(filesPo.getId()).and(SYS_FILES_PROCESS_PO.STATUS.eq("0")));
        long count = sysFilesProcessService.count(wrapper);
        if (count > 0) {
            sysFilesProcessService.remove(wrapper);
            SysFilesProcessPo sysFilesProcessPo = new SysFilesProcessPo();
            sysFilesProcessPo.setFileName(filename);
            sysFilesProcessPo.setFileId(fileMd5Id);
            sysFilesProcessPo.setFilePath(objectName);
            sysFilesProcessPo.setStatus("0");
            sysFilesProcessPo.setBucket(filesPo.getBucket());
            sysFilesProcessService.save(sysFilesProcessPo);
        }
        // 插入数据库
        SysFilesPo newfilesPo = new SysFilesPo();
        newfilesPo.setFileName(filename);
        newfilesPo.setFilePath(objectName);
        newfilesPo.setFileSize(file.getSize());
        newfilesPo.setFileType(file.getContentType());
        newfilesPo.setId(fileMd5Id);
        newfilesPo.setCreateUser(StpUtil.getLoginIdAsString());
        newfilesPo.setBucket(filesPo.getBucket());
        save(newfilesPo);
        return true;
    }

    @Override
    public void downloadFile(String fileId, HttpServletResponse response) throws IOException {
        SysFilesPo filesPo = getById(fileId);
        if (ObjectUtil.isNull(filesPo)) {
            ExceptionUtil.castServerException(FILE_NOT_EXISTS);
        }
        byte[] bytes;
        InputStream inputStream = null;
        try {
            inputStream = minioService.downloadFile(filesPo.getBucket(), filesPo.getFilePath());
            if (ObjectUtil.isNull(inputStream)){
                ExceptionUtil.castServerException(DOWNLOAD_FILE_ERROR);
            }
            bytes = IoUtil.readBytes(inputStream);
            response.setContentType(filesPo.getFileType());
            response.setHeader("Content-Disposition", "attachment; filename=" + filesPo.getFileName());
            response.getOutputStream().write(bytes);
        } finally {
            if (!ObjectUtil.isNull(inputStream)) {
                inputStream.close();
            }
        }
    }
}
