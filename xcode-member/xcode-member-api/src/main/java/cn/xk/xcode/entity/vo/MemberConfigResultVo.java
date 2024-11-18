package cn.xk.xcode.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/8/6 12:03
 * @Version 1.0
 * @Description MemberConfigResultVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户收获地址返回")
public class MemberConfigResultVo {
    @Schema(description = "积分抵用开关0 开启 1关闭")
    private String pointDeductEnable;

    @Schema(description = "1积分抵扣多少钱 单位：分")
    private Integer pointDeductUnit;

    @Schema(description = "最大使用积分数")
    private Integer maxPointDeduct;

    @Schema(description = "一元赠送的积分数量")
    private Integer givenPointAdd;

}
