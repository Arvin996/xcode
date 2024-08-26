package cn.xk.xcode.entity.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author Administrator
 * @Date 2024/8/21 15:41
 * @Version V1.0.0
 * @Description MemberGroupBaseDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员组更新基础dto")
public class MemberGroupBaseDto {
    @NotNull(message = "组名id不能为空")
    @Schema(description = "组名id", example = "1")
    private Integer id;
}
