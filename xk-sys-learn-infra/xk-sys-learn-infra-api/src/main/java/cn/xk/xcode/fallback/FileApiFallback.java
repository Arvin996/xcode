package cn.xk.xcode.fallback;

import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.feignclient.FileApi;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Author xuk
 * @Date 2024/6/28 09:52
 * @Version 1.0
 * @Description FileApiFallback
 */
public class FileApiFallback implements FallbackFactory<FileApi> {
    @Override
    public FileApi create(Throwable cause) {
        return new FileApi() {

            @Override
            public CommonResult<FileResultVo> uploadFile(UploadFileDto uploadFileDto) {
                return CommonResult.error(500, "上传文件失败熔断");
            }

            @Override
            public CommonResult<String> delFile(String fileId) {
                return CommonResult.error(500, "删除文件失败熔断");
            }

            @Override
            public CommonResult<String> updateFile(UpdateFileDto updateFileDto) {
                return CommonResult.error(500, "更新文件失败熔断");
            }

            @Override
            public CommonResult<byte[]> downloadFile(String fileId) {
                return CommonResult.error(500, "下载文件失败熔断");
            }
        };
    }
}
