package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/13 8:21
 * @Version 1.0.0
 * @Description UpdateUserDto
 **/
@Data
@Schema(name = "UpdateUserDto", description = "User修改实体类")
public class UpdateUserDto {

    /**
     * 登录用户名
     */
    @Schema(description = "username 登录用户名")
    @NotBlank(message = "username 不能为空")
    private String username;


    /**
     * 用户昵称
     */
    @Schema(description = "nickname 用户昵称")
    @NotBlank(message = "nickname 不能为空")
    private String nickname;

    /**
     * 0 男  1女 2未知
     */
    @Schema(description = "sex 0 男  1女 2未知")
    @NotBlank(message = "sex 不能为空")
    private String sex;

    /**
     * 年龄
     */
    @Schema(description = "age 年龄")
    @NotBlank(message = "age 不能为空")
    private Integer age;

    /**
     * 手机号
     */
    @Schema(description = "mobile 手机号")
    @NotBlank(message = "mobile 不能为空")
    private String mobile;

    /**
     * 邮件
     */
    @Schema(description = "email 邮件")
    private String email;

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
     * dingTalkWebhookSign
     */
    @Schema(description = "dingTalkWebhookSign")
    private String dingTalkWebhookSign;

    /**
     * feiShuWebhookSign
     */
    @Schema(description = "feiShuWebhookSign")
    private String feiShuWebhookSign;
}
