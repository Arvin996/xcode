package cn.xk.xcode.factory;

import cn.xk.xcode.client.TakeoutAddressClient;
import cn.xk.xcode.entity.vo.TakeoutAddressResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/10/29 15:35
 * @Version 1.0.0
 * @Description TakeoutAddressClientFallbackFactory
 **/
public class TakeoutAddressClientFallbackFactory implements FallbackFactory<TakeoutAddressClient> {
    @Override
    public TakeoutAddressClient create(Throwable cause) {
        return new TakeoutAddressClient() {
            @Override
            public CommonResult<List<TakeoutAddressResultVo>> getTakeoutAddressList(Long userId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<TakeoutAddressResultVo> getTakeoutDefaultAddress(Long userId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
