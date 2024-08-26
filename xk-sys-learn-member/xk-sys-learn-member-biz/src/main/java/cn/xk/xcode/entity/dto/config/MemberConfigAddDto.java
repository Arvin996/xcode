package cn.xk.xcode.entity.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * @Author Administrator
 * @Date 2024/8/22 15:03
 * @Version V1.0.0
 * @Description MemberConfigAddDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员配置新增dto")
public class MemberConfigAddDto {

    @Schema(description = "积分抵用开关0 开启 1关闭", example = "0")
    private String pointDeductEnable;

    @Min(value = 1, message = "1积分抵扣的金额数量不能小于1")
    @Schema(description = "1积分抵扣多少钱 单位：分")
    private Integer pointDeductUnit;

    @Min(value = 1, message = "最小使用积分数不能小于1")
    @Schema(description = "最大使用积分数")
    private Integer maxPointDeduct;

    @Min(value = 1, message = "一元赠送的积分数量不能小于1")
    @Schema(description = "一元赠送的积分数量")
    private Integer givenPointAdd;
}
