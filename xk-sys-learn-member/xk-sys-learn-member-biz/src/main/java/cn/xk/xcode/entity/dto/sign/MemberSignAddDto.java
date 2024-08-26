package cn.xk.xcode.entity.dto.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author Administrator
 * @Date 2024/8/21 11:31
 * @Version V1.0.0
 * @Description MemberSignAddDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增会员签到")
public class MemberSignAddDto {

    @NotNull(message = "星期数不能为空")
    @Min(value = 1, message = "星期数不能小于1")
    @Max(value = 7, message = "星期数不能大于7")
    @Schema(description = "签到第几天 星期一到星期天1-7", example = "1")
    private Integer day;

    @NotNull(message = "奖励积分不能为空")
    @Min(value = 1, message = "奖励积分不能小于1")
    @Schema(description = "奖励积分", example = "10")
    private Integer point;

    @NotNull(message = "奖励经验不能为空")
    @Min(value = 1, message = "奖励经验不能小于1")
    @Schema(description = "奖励经验", example = "10")
    private Integer experience;
}
