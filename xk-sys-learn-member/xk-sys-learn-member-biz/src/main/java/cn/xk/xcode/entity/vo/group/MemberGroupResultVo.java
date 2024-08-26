package cn.xk.xcode.entity.vo.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Administrator
 * @Date 2024/8/21 15:44
 * @Version V1.0.0
 * @Description MemberGroupResultVo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户组信息返回 vo")
public class MemberGroupResultVo {

    @Schema(description = "组id")
    private Integer id;

    @Schema(description = "组名称")
    private String name;

    @Schema(description = "组状态")
    private String status;
}
