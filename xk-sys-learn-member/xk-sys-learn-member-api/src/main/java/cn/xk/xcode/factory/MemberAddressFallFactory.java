package cn.xk.xcode.factory;

import cn.xk.xcode.client.MemberAddressClient;
import cn.xk.xcode.entity.vo.MemberAddressResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/8/8 14:46
 * @Version 1.0
 * @Description MemberAddressFallFactory
 */
public class MemberAddressFallFactory implements FallbackFactory<MemberAddressClient> {
    @Override
    public MemberAddressClient create(Throwable cause) {
        return new MemberAddressClient() {
            @Override
            public CommonResult<List<MemberAddressResultVo>> getMemberAddressList(Long userId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<MemberAddressResultVo> getMemberDefaultAddress(Long userId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
