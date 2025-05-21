package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.enums.MinioBucketType;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SysFilesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/6/27 15:07
 * @Version 1.0
 * @Description FilesController
 */
@RestController
@Validated
@RequestMapping("/infra/file")
public class SysFileController {
    @Resource
    private SysFilesService sysFilesService;

    @Operation(summary = "上传文件")
    @PostMapping("/uploadFile")
    public CommonResult<FileResultVo> uploadFile(@RequestPart("file") MultipartFile file,
                                          @RequestParam("bucketType") String bucketType,
                                          @RequestParam(value = "isNeedConvertToMp4", required = false) Integer isNeedConvertToMp4){
        boolean b;
        if (isNeedConvertToMp4 == null){
            b = false;
        }else {
            b  = isNeedConvertToMp4 == 1;
        }
        return uploadFile(UploadFileDto.builder().file(file).bucketType(MinioBucketType.getByType(bucketType)).isNeedConvertToMp4(b).build());
    }

    public CommonResult<FileResultVo> uploadFile(UploadFileDto uploadFileDto){
        return CommonResult.success(sysFilesService.uploadFile(uploadFileDto));
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/delFile/{fileId}")
    public CommonResult<Boolean> delFile(@PathVariable(name = "fileId") String fileId) {
        return CommonResult.success(sysFilesService.delFile(fileId));
    }

    @Operation(summary = "更新文件")
    @PostMapping("/updateFile")
    public CommonResult<Boolean> updateFile(@RequestPart("file") MultipartFile file,
                                    @RequestParam("fileId") String fileId) {
        return updateFile(UpdateFileDto.builder().file(file).fileId(fileId).build());
    }

    public CommonResult<Boolean> updateFile(UpdateFileDto updateFileDto) {
        return CommonResult.success(sysFilesService.updateFile(updateFileDto));
    }

    @Operation(summary = "下载文件")
    @GetMapping("/downloadFile/{fileId}")
    public void downloadFile(@PathVariable(name = "fileId") String fileId, HttpServletResponse response) throws IOException {
        sysFilesService.downloadFile(fileId, response);
    }
}
