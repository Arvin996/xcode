package cn.xk.xcode.entity.dto;

import cn.xk.xcode.enums.MemberExperienceChangeBizTypeEnum;
import cn.xk.xcode.validation.InIntEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/8/6 14:06
 * @Version 1.0
 * @Description MemberExperienceChangeReqDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户会员经验变化dto")
public class MemberExperienceChangeReqDto extends MemberBaseReqDto{

    @Schema(description = "变更经验")
    private Integer experience;

    @NotNull
    @Schema(description = "何种业务引起的经验变更")
    @InIntEnum(MemberExperienceChangeBizTypeEnum.class)
    private Integer bizType;

    @NotNull
    @Schema(description = "业务id")
    private String bizId;
}
