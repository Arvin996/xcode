package cn.xk.xcode.entity.vo.user;

import cn.xk.xcode.annotation.DictFieldTrans;
import cn.xk.xcode.core.annotation.FlexFieldTrans;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.entity.po.SystemRolePo;
import cn.xk.xcode.entity.vo.role.SystemRoleVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/5/13 10:40
 * @Version 1.0.0
 * @Description SystemUserVo
 **/
@Data
@Schema(name = "SystemUserVo", description = "SystemUserVo 系统用户返回")
public class SystemUserVo implements TransVo {

    /**
     * 登录用户名
     */
    @Schema(description = "username 登录用户名")
    private String username;

    /**
     * 角色id
     */
    @Schema(description = "roleId 角色id")
    @FlexFieldTrans(ref = SystemRolePo.class, targetField = "role", refConditionField = "id")
    private Integer roleId;

    /**
     * 角色
     */
    @Schema(description = "systemRolePo 角色")
    private SystemRoleVo role;


    /**
     * 用户昵称
     */
    @Schema(description = "nickname 用户昵称")
    private String nickname;

    /**
     * 0 男  1女 2未知
     */
    @Schema(description = "sex 0 男  1女 2未知")
    @DictFieldTrans(dictType = "sex", targetField = "sexName")
    private String sex;

    @Schema(description = "sexName 0 男  1女 2未知")
    private String sexName;

    /**
     * 年龄
     */
    @Schema(description = "age 年龄")
    private Integer age;

    /**
     * 手机号
     */
    @Schema(description = "mobile 手机号")
    private String mobile;

    /**
     * 邮件
     */
    @Schema(description = "email 邮件")
    private String email;

    /**
     * qqWebhookToken
     */
    @Schema(description = "qqWebhookToken")
    private String qqWebhookToken;

    /**
     * dingTalkWebhookToken
     */
    @Schema(description = "dingTalkWebhookToken")
    private String dingTalkWebhookToken;

    /**
     * feiShuWebhookToken
     */
    @Schema(description = "feiShuWebhookToken")
    private String feiShuWebhookToken;

    /**
     * 头像
     */
    @Schema(description = "avatar 头像")
    private String avatar;

    /**
     * 账号状态 0正常 1停用
     */
    @Schema(description = "status 账号状态 0正常 1停用")
    private String status;

    /**
     * 最后登录ip
     */
    @Schema(description = "lastLoginIp 最后登录ip")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "lastLoginTime 最后登录时间")
    private LocalDateTime lastLoginTime;
}
