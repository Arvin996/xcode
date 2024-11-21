package cn.xk.xcode.factory;

import cn.xk.xcode.client.SystemDictClient;
import cn.xk.xcode.entity.SystemDictResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/11/18 16:05
 * @Version 1.0.0
 * @Description SystemDictClientFallback
 **/
public class SystemDictClientFallback implements FallbackFactory<SystemDictClient> {
    @Override
    public SystemDictClient create(Throwable cause) {
        return new SystemDictClient() {
            @Override
            public CommonResult<String> getDictValue(String dictType, String dictCode) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<SystemDictResultVo>> getDictTypeValues(String dictType) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
