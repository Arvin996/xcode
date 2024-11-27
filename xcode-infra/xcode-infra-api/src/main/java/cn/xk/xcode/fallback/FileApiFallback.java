package cn.xk.xcode.fallback;

import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.feignclient.FileApi;
import cn.xk.xcode.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/6/28 09:52
 * @Version 1.0
 * @Description FileApiFallback
 */
@Slf4j
public class FileApiFallback implements FallbackFactory<FileApi> {
    @Override
    public FileApi create(Throwable cause) {
        return new FileApi() {

            @Override
            public CommonResult<FileResultVo> uploadFile(MultipartFile file, String bucketType, Integer isNeedConvertToMp4) {
                return CommonResult.error(500, "上传文件失败熔断");
            }

            @Override
            public CommonResult<String> delFile(String fileId) {
                return CommonResult.error(500, "删除文件失败熔断");
            }

            @Override
            public CommonResult<String> updateFile(MultipartFile file, String fileId) {
                return CommonResult.error(500, "更新文件失败熔断");
            }

            @Override
            public void downloadFile(String fileId, HttpServletResponse response) throws IOException {
               log.error("下载文件失败熔断");
            }
        };
    }
}
