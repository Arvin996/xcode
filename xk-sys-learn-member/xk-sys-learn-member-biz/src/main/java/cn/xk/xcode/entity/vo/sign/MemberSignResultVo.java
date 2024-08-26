package cn.xk.xcode.entity.vo.sign;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author Administrator
 * @Date 2024/8/21 11:30
 * @Version V1.0.0
 * @Description MemberSignResultVo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员签到信息返回 vo")
public class MemberSignResultVo {

    @Schema(description = "签到第几天 星期一到星期天1-7", example = "1")
    private Integer day;

    @Schema(description = "奖励积分", example = "10")
    private Integer point;

    @Schema(description = "奖励经验", example = "10")
    private Integer experience;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间", example = "2024-08-21 11:30:00")
    private LocalDateTime updateTime;
}
