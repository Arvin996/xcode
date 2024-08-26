package cn.xk.xcode.entity.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Administrator
 * @Date 2024/8/21 15:47
 * @Version V1.0.0
 * @Description MemberGroupQueryDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "查询用户会员组dto")
public class MemberGroupQueryDto {

    @Schema(description = "组id")
    private String id;

    @Schema(description = "组名")
    private String name;

    @Schema(description = "组状态")
    private String status;
}
