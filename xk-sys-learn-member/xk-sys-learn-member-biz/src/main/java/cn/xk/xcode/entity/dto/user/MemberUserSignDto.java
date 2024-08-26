package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @Author Administrator
 * @Date 2024/8/23 16:29
 * @Version V1.0.0
 * @Description MemberUserSignDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户签到  dto")
public class MemberUserSignDto extends MemberUserBaseDto{

    @NotNull(message = "签到天数不能为空")
    @Schema(description = "签到第几天")
    private Integer day;
}
