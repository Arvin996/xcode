package cn.xk.xcode.entity.dto.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author Administrator
 * @Date 2024/8/22 16:23
 * @Version V1.0.0
 * @Description MemberLevelAddDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员等级新增dto")
public class MemberLevelAddDto {

    @NotBlank(message = "等级名称不能为空")
    @Schema(description = "等级名称")
    private String name;

    @NotNull(message = "等级不能为空")
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
