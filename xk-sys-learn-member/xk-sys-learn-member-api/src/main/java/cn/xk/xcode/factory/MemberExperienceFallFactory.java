package cn.xk.xcode.factory;

import cn.xk.xcode.client.MemberExperienceClient;
import cn.xk.xcode.entity.dto.MemberExperienceChangeReqDto;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/8/8 14:51
 * @Version 1.0
 * @Description MemberExperienceFallFactory
 */
public class MemberExperienceFallFactory implements FallbackFactory<MemberExperienceClient> {
    @Override
    public MemberExperienceClient create(Throwable cause) {
        return new MemberExperienceClient() {
            @Override
            public CommonResult<Boolean> memberExperienceChange(MemberExperienceChangeReqDto memberExperienceChangeReqDto) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
