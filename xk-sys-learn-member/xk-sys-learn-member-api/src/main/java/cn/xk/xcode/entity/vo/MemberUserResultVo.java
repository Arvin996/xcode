package cn.xk.xcode.entity.vo;

import com.xk.xcode.core.entity.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/8/8 13:46
 * @Version 1.0
 * @Description MemberUserResultVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户信息返回")
public class MemberUserResultVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "用户手机号")
    private String mobile;

    @Schema(description = "用户邮箱 用于绑定")
    private String email;

    @Schema(description = "用户状态 0 正常 1禁用")
    private String status;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户等级")
    private Integer levelId;

    @Schema(description = "用户标签id")
    private List<String> tagIds;

    @Schema(description = "用户经验")
    private Integer experience;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户积分")
    private Integer point;

    @Schema(description = "用户分组id")
    private Integer groupId;

    @Schema(description = " 用户性别 0男 1女 这里返回直接是男 女了")
    private String sex;

    @Schema(description = "用户生日")
    private String birthday;

    @Schema(description = "最近一次登录ip")
    private String loginIp;

    @Schema(description = "最近一次登录地区id")
    private Integer loginAreaId;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    @Schema(description = "具体等级")
    private MemberLevelResultVo memberLevelResultVo;

    @Schema(description = "具体地区")
    private Area area;

    @Schema(description = "具体标签")
    private List<String> tagNames;
}
