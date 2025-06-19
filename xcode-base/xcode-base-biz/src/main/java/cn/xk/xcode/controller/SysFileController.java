package cn.xk.xcode.controller;

import cn.xk.xcode.annotation.SaSystemCheckLogin;
import cn.xk.xcode.entity.dto.QuerySysFilesDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.entity.vo.SysFilesVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.SysFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "SysFileController", description = "文件管理")
public class SysFileController {

    @Resource
    private SysFilesService sysFilesService;

    @Operation(summary = "上传文件")
    @PostMapping("/file/uploadFile")
    public CommonResult<FileResultVo> uploadFile(@RequestPart("file") MultipartFile file,
                                                 @RequestPart("bucket") String bucket,
                                                 @RequestPart(value = "username") String username,
                                                 @RequestPart(value = "isNeedConvertToMp4", required = false) Boolean isNeedConvertToMp4) {
        return CommonResult.success(sysFilesService.uploadFile(file, bucket, username, isNeedConvertToMp4));
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/file/delFile/{fileId}")
    public CommonResult<Boolean> delFile(@PathVariable(name = "fileId") String fileId) {
        return CommonResult.success(sysFilesService.delFile(fileId));
    }

    @Operation(summary = "更新文件")
    @PostMapping("/file/updateFile")
    public CommonResult<Boolean> updateFile(@RequestPart("file") MultipartFile file,
                                            @RequestPart("fileId") String fileId) {
        return CommonResult.success(sysFilesService.updateFile(file, fileId));
    }

    @Operation(summary = "下载文件")
    @GetMapping("/file/downloadFile")
    public void downloadFile(@RequestPart(name = "fileId") String fileId,
                             HttpServletResponse response) throws IOException {
        sysFilesService.downloadFile(fileId, response);
    }

    @Operation(summary = "查询文件列表")
    @PostMapping("/manage/file/list")
    @SaSystemCheckLogin
    public CommonResult<PageResult<SysFilesVo>> querySysFiles(@RequestBody QuerySysFilesDto querySysFilesDto) {
        return CommonResult.success(sysFilesService.querySysFiles(querySysFilesDto));
    }
}
