package cn.xk.xcode.entity.dto;

import cn.xk.xcode.enums.MemberExperienceChangeBizTypeEnum;
import cn.xk.xcode.enums.MemberPointChangeBizTypeEnum;
import cn.xk.xcode.validation.InIntEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/8/6 18:30
 * @Version 1.0
 * @Description MemberPointChangeReqDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户会员积分变化dto")
public class MemberPointChangeReqDto extends MemberBaseReqDto
{
    @Schema(description = "变更积分")
    private Integer point;

    @NotNull
    @Schema(description = "何种业务引起的积分变更")
    @InIntEnum(MemberPointChangeBizTypeEnum.class)
    private Integer bizType;

    @NotNull
    @Schema(description = "业务id")
    private String bizId;
}
