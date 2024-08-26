package cn.xk.xcode.entity.dto.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author Administrator
 * @Date 2024/8/21 14:23
 * @Version V1.0.0
 * @Description MemberSignUpdateDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新会员签到规则")
public class MemberSignUpdateDto extends MemberSignAddDto{

}
