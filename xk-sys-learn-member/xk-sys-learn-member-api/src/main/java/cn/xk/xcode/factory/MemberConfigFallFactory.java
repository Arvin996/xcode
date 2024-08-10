package cn.xk.xcode.factory;

import cn.xk.xcode.client.MemberConfigClient;
import cn.xk.xcode.entity.vo.MemberConfigResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/8/8 14:49
 * @Version 1.0
 * @Description MemberConfigClientFallFactory
 */
public class MemberConfigFallFactory implements FallbackFactory<MemberConfigClient> {
    @Override
    public MemberConfigClient create(Throwable cause) {
        return new MemberConfigClient() {
            @Override
            public CommonResult<MemberConfigResultVo> getMemberConfig() {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
