package cn.xk.xcode.service.impl;

import cn.hutool.crypto.digest.MD5;
import cn.xk.xcode.core.OssClient;
import cn.xk.xcode.entity.*;
import cn.xk.xcode.entity.dto.BigFileMergeUploadChunkFliesReqDto;
import cn.xk.xcode.entity.po.SysFilesPo;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.BigSysFileService;
import cn.xk.xcode.service.SysFilesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/5/22 12:40
 * @Version 1.0.0
 * @Description BigSysFileImpl
 **/
@Service
@Slf4j
public class BigSysFileServiceImpl implements BigSysFileService {

    @Resource
    private OssClient ossClient;

    @Resource
    private SysFilesService sysFilesService;

    @Override
    public CommonResult<InitUploadChunkFileRespVo> initUploadChunkFile(InitUploadChunkFileReqDto initUploadChunkFileReqDto) {
        return ossClient.initUploadChunkFile(initUploadChunkFileReqDto);
    }

    @Override
    public CommonResult<Boolean> checkChunkFileExists(CheckChunkFileExistsReqDto checkChunkFileExistsReqDto) {
        return ossClient.checkChunkFileExists(checkChunkFileExistsReqDto);
    }

    @Override
    public CommonResult<UploadChunkFileRespVo> uploadChunkFile(UploadChunkFileReqDto uploadChunkFileReqDto) {
        return ossClient.uploadChunkFile(uploadChunkFileReqDto);
    }

    @Override
    public CommonResult<FileResultVo> mergeChunkFile(BigFileMergeUploadChunkFliesReqDto bigFileMergeUploadChunkFliesReqDto) throws IOException {
        MergeUploadChunkFliesRespVo mergeUploadChunkFliesRespVo = ossClient.mergerChunkFile(bigFileMergeUploadChunkFliesReqDto).getData();
        SysFilesPo sysFilesPo = new SysFilesPo();
        File tempFile = File.createTempFile(mergeUploadChunkFliesRespVo.getFilename(), ".tmp");
        OutputStream outputStream = Files.newOutputStream(tempFile.toPath());
        ossClient.download(mergeUploadChunkFliesRespVo.getObjectName(), bigFileMergeUploadChunkFliesReqDto.getBucketName(), outputStream);
        sysFilesPo.setId(MD5.create().digestHex(Files.newInputStream(tempFile.toPath())));
        sysFilesPo.setFileName(mergeUploadChunkFliesRespVo.getFilename());
        sysFilesPo.setBucket(bigFileMergeUploadChunkFliesReqDto.getBucketName());
        sysFilesPo.setObjectName(mergeUploadChunkFliesRespVo.getObjectName());
        sysFilesPo.setFileUrl(mergeUploadChunkFliesRespVo.getUrl());
        sysFilesPo.setContentType(mergeUploadChunkFliesRespVo.getContextType());
        sysFilesPo.setFileSize(tempFile.getAbsoluteFile().length());
        sysFilesPo.setETag(mergeUploadChunkFliesRespVo.getETag());
        sysFilesPo.setCreateTime(LocalDateTime.now());
        sysFilesPo.setCreateUser(bigFileMergeUploadChunkFliesReqDto.getUsername());
        sysFilesService.save(sysFilesPo);
        FileResultVo fileResultVo = new FileResultVo();
        fileResultVo.setFileId(sysFilesPo.getId());
        fileResultVo.setFilePath(sysFilesPo.getFileUrl());
        return CommonResult.success(fileResultVo);
    }
}
