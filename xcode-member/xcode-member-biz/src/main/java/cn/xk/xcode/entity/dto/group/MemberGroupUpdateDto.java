package cn.xk.xcode.entity.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author Administrator
 * @Date 2024/8/21 15:25
 * @Version V1.0.0
 * @Description MemberGroupUpdateDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户会员组更新dto")
public class MemberGroupUpdateDto extends MemberGroupBaseDto{

    @Schema(description = "组名", example = "vip")
    private String name;

    @Schema(description = "组状态", example = "1")
    private String status;
}
