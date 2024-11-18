package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.typehandler.ListIntTypeHandler;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/23 16:56
 * @Version V1.0.0
 * @Description MemberUserUpdateDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员用户签到  dto")
public class MemberUserUpdateDto extends MemberUserBaseDto{

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户标签")
    @Column(typeHandler = ListIntTypeHandler.class)
    private List<Integer> tagIds;

    @Schema(description = "用户分组")
    private Integer groupId;

    @Schema(description = "用户性别")
    private String sex;

    @Schema(description = "用户生日")
    private String birthday;
}
