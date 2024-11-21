package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/18 15:42
 * @Version 1.0.0
 * @Description SystemUserResultVo
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统用户返回 Vo")
public class SystemUserResultVo {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long id;

    /**
     * 用户名 不能重复
     */
    @Schema(description = "用户名")
    private String username;


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


    /**
     * 用户角色
     */
    @Schema(description = "用户角色")
    private List<SystemRole> roles;

    @NoArgsConstructor
    @Accessors(chain = true)
    @Data
    public static class SystemRole {

        @Schema(description = "角色id")
        private Integer id;

        /**
         * 角色名称
         */
        @Schema(description = "角色名称")
        private String name;
    }
}
