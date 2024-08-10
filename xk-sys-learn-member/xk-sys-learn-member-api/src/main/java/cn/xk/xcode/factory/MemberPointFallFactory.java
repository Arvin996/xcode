package cn.xk.xcode.factory;

import cn.xk.xcode.client.MemberPointClient;
import cn.xk.xcode.entity.dto.MemberPointChangeReqDto;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/8/8 14:55
 * @Version 1.0
 * @Description MemberPointFallFactory
 */
public class MemberPointFallFactory implements FallbackFactory<MemberPointClient> {
    @Override
    public MemberPointClient create(Throwable cause) {
        return new MemberPointClient() {
            @Override
            public CommonResult<Boolean> memberPointChange(MemberPointChangeReqDto memberPointChangeReqDto) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
