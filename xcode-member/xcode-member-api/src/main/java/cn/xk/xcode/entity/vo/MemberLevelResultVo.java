package cn.xk.xcode.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/8/6 18:25
 * @Version 1.0
 * @Description MemberLevelResultVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户等级返回")
public class MemberLevelResultVo
{
    @Schema(description = "等级id")
    private Integer id;

    @Schema(description = "等级名称")
    private String name;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "升级所需经验")
    private Integer experience;

    @Schema(description = "享受折扣")
    private Integer discountPercent;

    @Schema(description = "等级头像")
    private String levelIcon;

    @Schema(description = "等级背景图")
    private String levelBackground;
}
