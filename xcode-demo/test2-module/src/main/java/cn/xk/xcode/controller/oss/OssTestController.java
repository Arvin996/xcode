package cn.xk.xcode.controller.oss;

import cn.xk.xcode.core.OssClient;
import cn.xk.xcode.entity.CheckChunkFileExistsReqDto;
import cn.xk.xcode.entity.InitUploadChunkFileReqDto;
import cn.xk.xcode.entity.MergeUploadChunkFliesReqDto;
import cn.xk.xcode.entity.UploadChunkFileReqDto;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        return ossClient.upload(file);
    }

    @RequestMapping("/test2")
    public void test2(HttpServletResponse response) throws IOException {
        ossClient.download("/655fedb237acac56e88d5029ec9e9b97/20250522/alarm.sql", response);
    }

    @RequestMapping("/test3")
    public Object test3() throws IOException {
        CommonResult<Boolean> commonResult = ossClient.deleteFile("/655fedb237acac56e88d5029ec9e9b97/20250522/alarm.sql");
        return commonResult;
    }

    @RequestMapping("/test4")
    public Object test4() throws IOException {
        return ossClient.getPrivateUrl("/655fedb237acac56e88d5029ec9e9b97/20250522/alarm (092756.591).sql");
    }

    @RequestMapping("/test5")
    public Object test5() throws IOException {
        // CoinExchange-master.zip
        return ossClient.initUploadChunkFile(InitUploadChunkFileReqDto.builder()
                .contentType("application/msword").filename("asadas.doc").build());
    }

    @RequestMapping("/test6")
    public Object test6(@RequestPart("file") MultipartFile file) throws IOException {
        // CoinExchange-master.zip
        UploadChunkFileReqDto uploadChunkFileReqDto = UploadChunkFileReqDto
                .builder()
                .uploadId("988bc28f-5bf7-48cd-8ac9-1422f86c1d36")
                .currentChunk(1)
                .objectName("/1fe35b4dcef293f16bfcdf1babb373b7/20250522/asadas.doc")
                .file(file)
                .build();
        return ossClient.uploadChunkFile(uploadChunkFileReqDto);
    }

    @RequestMapping("/test7")
    public Object test7() throws IOException {
        // CoinExchange-master.zip
        CheckChunkFileExistsReqDto checkChunkFileExistsReqDto = CheckChunkFileExistsReqDto.builder()
                .eTag("\"d4d37b9051994a2ecd598b8ddd8adf26\"")
                .uploadId("988bc28f-5bf7-48cd-8ac9-1422f86c1d36")
                .currentChunk(1)
                .objectName("/1fe35b4dcef293f16bfcdf1babb373b7/20250522/asadas.doc")
                .build();
        return ossClient.checkChunkFileExists(checkChunkFileExistsReqDto);
    }

    @RequestMapping("/test8")
    public Object test8() {
        MergeUploadChunkFliesReqDto build = MergeUploadChunkFliesReqDto.builder()
                .uploadId("988bc28f-5bf7-48cd-8ac9-1422f86c1d36")
                .build();
        build.setObjectName("/1fe35b4dcef293f16bfcdf1babb373b7/20250522/asadas.doc");
        List<MergeUploadChunkFliesReqDto.OssMergeChunkFileInfo> chunkFileInfos = new ArrayList<>();
        chunkFileInfos.add(new MergeUploadChunkFliesReqDto.OssMergeChunkFileInfo(1, "\"d4d37b9051994a2ecd598b8ddd8adf26\""));
        build.setChunkFileInfos(chunkFileInfos);
        return ossClient.mergerChunkFile(build);
    }
}
