package cn.xk.xcode.entity.dto.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author Administrator
 * @Date 2024/8/23 15:03
 * @Version V1.0.0
 * @Description MemberLevelUpdateDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员等级更新 dto")
public class MemberLevelUpdateDto extends MemberLevelBaseDto{

    @Schema(description = "等级名称")
    private String name;

    @Schema(description = "等级")
    private Integer level;

    @NotNull(message = "升级所需经验不能为空")
    @Min(value = 1, message = "升级所需经验不能小于1")
    @Schema(description = "升级所需经验")
    private Integer experience;

    @NotNull(message = "享受折扣不能为空")
    @Min(value = 10, message = "享受折扣不能小于1折")
    @Max(value = 99, message = "享受折扣不能大于99折")
    @Schema(description = "享受折扣")
    private Integer discountPercent;

    @Schema(description = "等级头像")
    private String levelIcon;

    @Schema(description = "等级背景图")
    private String levelBackground;
}
