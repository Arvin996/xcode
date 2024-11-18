package cn.xk.xcode.entity.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/8/4 20:23
 * @Version 1.0
 * @Description MemberListQueryDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "查询用户所有tag")
public class MemberListQueryDto {
    @Schema(description = "用户tags")
    @NotEmpty(message = "用户tags不能为空")
    private List<Integer> tagIds;
}
