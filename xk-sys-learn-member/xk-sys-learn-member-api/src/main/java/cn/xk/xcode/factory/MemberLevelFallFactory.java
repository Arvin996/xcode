package cn.xk.xcode.factory;

import cn.xk.xcode.client.MemberLevelClient;
import cn.xk.xcode.entity.dto.MemberBaseReqDto;
import cn.xk.xcode.entity.vo.MemberLevelResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/8/8 14:54
 * @Version 1.0
 * @Description MemberLevelFallFactory
 */
public class MemberLevelFallFactory implements FallbackFactory<MemberLevelClient> {

    @Override
    public MemberLevelClient create(Throwable cause) {
        return new MemberLevelClient() {
            @Override
            public CommonResult<MemberLevelResultVo> getMemberUserLevel(MemberBaseReqDto memberBaseReqDto) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
