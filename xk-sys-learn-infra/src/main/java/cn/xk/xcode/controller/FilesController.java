package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.feignclient.FileApi;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SysFilesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

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
public class FilesController implements FileApi
{
    @Resource
    private SysFilesService sysFilesService;

    @Override
    public CommonResult<FileResultVo> uploadFile(UploadFileDto uploadFileDto) {
        return CommonResult.success(sysFilesService.uploadFile(uploadFileDto));
    }

    @Override
    public CommonResult<String> delFile(String fileId) {
        return CommonResult.success(sysFilesService.delFile(fileId));
    }

    @Override
    public CommonResult<String> updateFile(UpdateFileDto updateFileDto) {
        return CommonResult.success(sysFilesService.updateFile(updateFileDto));
    }

    @Override
    public CommonResult<byte[]> downloadFile(String fileId) throws IOException {
        return CommonResult.success(sysFilesService.downloadFile(fileId));
    }
}
