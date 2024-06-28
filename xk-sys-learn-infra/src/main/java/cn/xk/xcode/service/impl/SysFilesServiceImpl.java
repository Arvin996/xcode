package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.entity.po.SysFilesProcessPo;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.enums.MinioBucketType;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.service.MinioService;
import cn.xk.xcode.service.SysFilesProcessService;
import cn.xk.xcode.utils.MinioFileUtils;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SysFilesPo;
import cn.xk.xcode.mapper.SysFilesMapper;
import cn.xk.xcode.service.SysFilesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;

import static cn.xk.xcode.entity.def.SysFilesProcessTableDef.SYS_FILES_PROCESS_PO;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-06-26
 */
@Service
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFilesPo> implements SysFilesService {

    @Resource
    private SysFilesProcessService sysFilesProcessService;

    @Resource
    private MinioService minioService;

    @Override
    public FileResultVo uploadFile(UploadFileDto uploadFileDto) {
        MultipartFile file = uploadFileDto.getFile();
        String filename = file.getOriginalFilename();
        File tempFile;
        try {
            tempFile = File.createTempFile(filename, ".temp");
            file.transferTo(tempFile);
        } catch (Exception e) {
            throw new ServiceException(500, "创建文件失败");
        }
        String fileMd5Id = MinioFileUtils.getFileMd5Id(tempFile);
        String objectName = MinioFileUtils.getObjectName(filename, fileMd5Id);
        Boolean b = minioService.uploadFile(tempFile, objectName, uploadFileDto.getBucketType().getType(), file.getContentType());
        if (!b) {
            throw new ServiceException(500, "上传文件失败");
        }
        // 插入数据库
        SysFilesPo filesPo = new SysFilesPo();
        filesPo.setFileName(filename);
        filesPo.setFilePath(objectName);
        filesPo.setFileSize(file.getSize());
        filesPo.setFileType(file.getContentType());
        filesPo.setId(fileMd5Id);
        filesPo.setCreateUser(StpUtil.getLoginIdAsString());
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
            }
        }
        // 这里的filename 一定要是本服务器的ip地址 todo拼接ip地址给filePath
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String hostAddress = inetAddress.getHostAddress();
        return FileResultVo.builder().fileId(fileMd5Id).filePath("http://" + hostAddress + ":9000" + "/" + objectName).build();
    }

    @Override
    public String delFile(String fileId) {
        SysFilesPo filesPo = getById(fileId);
        if (ObjectUtil.isNull(filesPo)) {
            throw new ServiceException(500, "文件不存在");
        }
        boolean b = minioService.delFile(filesPo.getBucket(), filesPo.getFilePath());
        if (!b) {
            throw new ServiceException(500, "删除文件失败");
        }
        removeById(fileId);
        return "删除成功";
    }

    @Transactional
    @Override
    public String updateFile(UpdateFileDto updateFileDto) {
        // 1. 获取原来的数据文件
        SysFilesPo filesPo = getById(updateFileDto.getFileId());
        if (ObjectUtil.isNull(filesPo)) {
            throw new ServiceException(500, "文件不存在");
        }
        boolean b = minioService.delFile(filesPo.getBucket(), filesPo.getFilePath());
        if (!b) {
            throw new ServiceException(500, "操作失败");
        }
        MultipartFile file = updateFileDto.getFile();
        String filename = file.getOriginalFilename();
        File tempFile;
        try {
            tempFile = File.createTempFile(filename, ".temp");
            file.transferTo(tempFile);
        } catch (Exception e) {
            throw new ServiceException(500, "创建文件失败");
        }
        String fileMd5Id = MinioFileUtils.getFileMd5Id(tempFile);
        String objectName = MinioFileUtils.getObjectName(filename, fileMd5Id);
        b = minioService.uploadFile(tempFile, objectName, filesPo.getBucket(), file.getContentType());
        if (!b) {
            throw new ServiceException(500, "上传文件失败");
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
        return "更新成功";
    }

    @Override
    public byte[] downloadFile(String fileId) throws IOException {
        SysFilesPo filesPo = getById(fileId);
        if (ObjectUtil.isNull(filesPo)) {
            throw new ServiceException(500, "文件不存在");
        }
        byte[] bytes;
        InputStream inputStream = null;
        try {
            inputStream = minioService.downloadFile(filesPo.getBucket(), filesPo.getFilePath());
            if (ObjectUtil.isNull(inputStream)){
                throw new ServiceException(500, "文件下载失败");
            }
            bytes = IoUtil.readBytes(inputStream);
        } finally {
            if (!ObjectUtil.isNull(inputStream)) {
                inputStream.close();
            }
        }
        return bytes;
    }
}
