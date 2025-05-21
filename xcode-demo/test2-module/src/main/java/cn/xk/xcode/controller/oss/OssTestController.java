package cn.xk.xcode.controller.oss;

import cn.xk.xcode.core.OssClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2025/5/21 14:56
 * @Version 1.0.0
 * @Description OssTestController
 **/
@RestController
@RequestMapping("/oss")
public class OssTestController {

    @Resource
    private OssClient ossClient;

    @RequestMapping("/test1")
    public Object test1(@RequestPart("file") MultipartFile file) throws IOException {
        return ossClient.uploadToDefaultBucket(file);
    }
}
