package cn.xk.xcode.entity.vo.user;

import cn.xk.xcode.entity.vo.MemberLevelResultVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/27 15:57
 * @Version V1.0.0
 * @Description MemberUserLoginVo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户会员登录返回")
public class MemberUserLoginVo {

    @Schema(description = "用户id")
    private String id;

    @Schema(description = "用户手机号")
    private String mobile;

    @Schema(description = "用户邮箱 用于绑定")
    private String email;

    @Schema(description = "0 正常 1禁用")
    private String status;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户等级")
    private Integer levelId;

    @Schema(description = "用户点击实体类")
    private MemberLevelResultVo memberLevelResultVo;

    @Schema(description = "用户经验")
    private Integer experience;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户积分")
    private Integer point;

    @Schema(description = "用户标签")
    private List<Integer> tagIds;

    @Schema(description = "用户标签名称")
    private List<String> tagNames;

    @Schema(description = "用户分组id")
    private Integer groupId;

    @Schema(description = "用户分组名称")
    private String groupName;

    @Schema(description = "用户性别 0男 1女")
    private String sex;

    @Schema(description = "用户生日")
    private String birthday;

    @Schema(description = "最近一次登录ip")
    private String loginIp;

    @Schema(description = "最近一次登录地区")
    private Integer loginAreaId;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;
}
