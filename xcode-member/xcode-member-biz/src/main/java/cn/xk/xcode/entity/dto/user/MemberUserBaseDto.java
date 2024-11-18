package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @Author Administrator
 * @Date 2024/8/23 16:15
 * @Version V1.0.0
 * @Description MemberUserBaseDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户 base dto")
public class MemberUserBaseDto {

    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id")
    private Long id;
}
