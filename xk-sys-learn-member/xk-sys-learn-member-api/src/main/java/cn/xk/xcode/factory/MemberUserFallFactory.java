package cn.xk.xcode.factory;

import cn.xk.xcode.client.MemberUserClient;
import cn.xk.xcode.entity.dto.MemberQueryDto;
import cn.xk.xcode.entity.dto.MemberUserListQueryDto;
import cn.xk.xcode.entity.vo.MemberUserResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/8/8 14:56
 * @Version 1.0
 * @Description MemberUserFallFactory
 */
public class MemberUserFallFactory implements FallbackFactory<MemberUserClient> {

    @Override
    public MemberUserClient create(Throwable cause) {
        return new MemberUserClient() {
            @Override
            public CommonResult<MemberUserResultVo> getMemberUser(MemberQueryDto memberUsersQueryDto) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<MemberUserResultVo>> getMemberUserList(MemberUserListQueryDto memberUserListQueryDto) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
