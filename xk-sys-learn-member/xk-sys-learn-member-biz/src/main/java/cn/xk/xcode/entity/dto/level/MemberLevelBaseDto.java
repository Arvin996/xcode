package cn.xk.xcode.entity.dto.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author Administrator
 * @Date 2024/8/23 14:57
 * @Version V1.0.0
 * @Description MemberLevelBaseDto
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员base dto")
public class MemberLevelBaseDto {
    @NotBlank(message = "等级id不能为空")
    @Schema(description = "等级id")
    private String id;
}
