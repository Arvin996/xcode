package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.feignclient.FileApi;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SysFilesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
public class FilesController implements FileApi {
    @Resource
    private SysFilesService sysFilesService;

    @Override
    @PostMapping("/uploadFile")
    public CommonResult<FileResultVo> uploadFile(UploadFileDto uploadFileDto) {
        return CommonResult.success(sysFilesService.uploadFile(uploadFileDto));
    }

    @Override
    @GetMapping("/delFile/{fileId}")
    public CommonResult<String> delFile(@PathVariable(name = "fileId") String fileId) {
        return CommonResult.success(sysFilesService.delFile(fileId));
    }

    @Override
    @PostMapping("/updateFile")
    public CommonResult<String> updateFile(UpdateFileDto updateFileDto) {
        return CommonResult.success(sysFilesService.updateFile(updateFileDto));
    }

    @Override
    @GetMapping("/downloadFile/{fileId}")
    public CommonResult<byte[]> downloadFile(@PathVariable(name = "fileId") String fileId) throws IOException {
        return CommonResult.success(sysFilesService.downloadFile(fileId));
    }
}
