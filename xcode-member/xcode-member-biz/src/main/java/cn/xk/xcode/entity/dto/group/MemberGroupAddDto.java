package cn.xk.xcode.entity.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

/**
 * @Author Administrator
 * @Date 2024/8/21 14:59
 * @Version V1.0.0
 * @Description MemberGroupAddDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增用户会员组dto")
public class MemberGroupAddDto {

    @NotBlank(message = "会员组名不能为空")
    @Schema(description = "组名", example = "vip")
    private String name;
}
