package cn.xk.xcode.controller;

import cn.xk.xcode.entity.*;
import cn.xk.xcode.entity.dto.BigFileMergeUploadChunkFliesReqDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.BigSysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2025/5/22 12:39
 * @Version 1.0.0
 * @Description BigSysFileController
 **/
@RestController
@RequestMapping("/infra/bigfile")
@Tag(name = "BigSysFileController", description = "大文件分片上传")
public class BigSysFileController {

    @Resource
    private BigSysFileService bigSysFileService;

    @PostMapping("/chunk/init")
    @Operation(summary = "初始化分片上传")
    public CommonResult<InitUploadChunkFileRespVo> initUploadChunkFile(@Validated @RequestBody InitUploadChunkFileReqDto initUploadChunkFileReqDto) {
        return bigSysFileService.initUploadChunkFile(initUploadChunkFileReqDto);
    }

    @PostMapping("/check/chunk")
    @Operation(summary = "检查分片是否已经上传")
    public CommonResult<Boolean> checkChunkFileExists(@Validated @RequestBody CheckChunkFileExistsReqDto checkChunkFileExistsReqDto) {
        return bigSysFileService.checkChunkFileExists(checkChunkFileExistsReqDto);
    }

    @PostMapping("/chunk/upload")
    @Operation(summary = "上传分片")
    public CommonResult<UploadChunkFileRespVo> uploadChunkFile(@Validated @RequestBody UploadChunkFileReqDto uploadChunkFileReqDto) {
        return bigSysFileService.uploadChunkFile(uploadChunkFileReqDto);
    }

    @PostMapping("/chunk/merge")
    @Operation(summary = "合并分片")
    public CommonResult<FileResultVo> mergeChunkFile(@Validated @RequestBody BigFileMergeUploadChunkFliesReqDto bigFileMergeUploadChunkFliesReqDto) throws IOException {
        return bigSysFileService.mergeChunkFile(bigFileMergeUploadChunkFliesReqDto);
    }
}
