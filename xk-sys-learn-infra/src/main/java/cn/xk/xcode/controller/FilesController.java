package cn.xk.xcode.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author xuk
 * @Date 2024/6/27 15:07
 * @Version 1.0
 * @Description FilesController
 */
@RestController
@RequestMapping("/files")
@Tag(name = "文件接口")
public class FilesController
{
    @Operation(description = "上传文件")
    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("file")MultipartFile file){

    }
}
