package cn.xk.xcode.service;

import cn.xk.xcode.entity.*;
import cn.xk.xcode.entity.dto.BigFileMergeUploadChunkFliesReqDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.pojo.CommonResult;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2025/5/22 12:40
 * @Version 1.0.0
 * @Description BigSysFileService
 **/
public interface BigSysFileService {
    CommonResult<InitUploadChunkFileRespVo> initUploadChunkFile(InitUploadChunkFileReqDto initUploadChunkFileReqDto);

    CommonResult<Boolean> checkChunkFileExists(CheckChunkFileExistsReqDto checkChunkFileExistsReqDto);

    CommonResult<UploadChunkFileRespVo> uploadChunkFile(UploadChunkFileReqDto uploadChunkFileReqDto);

    CommonResult<FileResultVo> mergeChunkFile(BigFileMergeUploadChunkFliesReqDto bigFileMergeUploadChunkFliesReqDto) throws IOException;
}
