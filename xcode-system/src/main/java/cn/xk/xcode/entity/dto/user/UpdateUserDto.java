package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 19:26
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新用户信息实体类")
public class UpdateUserDto {

    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    /**
     * 用户名 不能重复
     */
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String userpic;

    /**
     * qq
     */
    @Schema(description = "qq")
    private String qq;

    /**
     * 微信
     */
    @Schema(description = "微信")
    private String wx;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 用户状态 0在线 1离线 2封禁
     */
    @Schema(description = "用户状态")
    private String status;

}
