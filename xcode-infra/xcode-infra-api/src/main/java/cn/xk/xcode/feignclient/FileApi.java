package cn.xk.xcode.feignclient;

import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.fallback.FileApiFallback;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/6/27 15:27
 * @Version 1.0
 * @Description FileApi
 */
@FeignClient(name = "xcode-infra", fallbackFactory = FileApiFallback.class)
@Tag(name = "RPC 服务 - 文件")
@RequestMapping("/infra/file")
public interface FileApi
{
    @Operation(summary = "上传文件")
    @PostMapping("/uploadFile")
    CommonResult<FileResultVo> uploadFile(@RequestPart("file") MultipartFile file,
                                          @RequestParam("bucketType") String bucketType,
                                          @RequestParam(value = "isNeedConvertToMp4", required = false) Integer isNeedConvertToMp4);

    @Operation(summary = "删除文件")
    @GetMapping("/delFile/{fileId}")
    CommonResult<String> delFile(@PathVariable(name = "fileId") String fileId);

    @Operation(summary = "更新文件")
    @PostMapping("/updateFile")
    CommonResult<String> updateFile(@RequestPart("file") MultipartFile file,
                                    @RequestParam("fileId") String fileId);

    @Operation(summary = "下载文件")
    @GetMapping("/downloadFile/{fileId}")
    void downloadFile(@PathVariable(name = "fileId") String fileId, HttpServletResponse response) throws IOException;
}
