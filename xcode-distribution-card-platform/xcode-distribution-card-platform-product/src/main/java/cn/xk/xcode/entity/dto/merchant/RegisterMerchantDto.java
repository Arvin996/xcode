package cn.xk.xcode.entity.dto.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/30 13:28
 * @Version 1.0.0
 * @Description RegisterMerchantDto
 **/
@Schema(name = "RegisterMerchantDto", description = "商户注册实体类")
@Data
public class RegisterMerchantDto {

    @Schema(description = "username 登录用户名")
    @NotBlank(message = "登录用户名不能为空")
    private String username;

    /**
     * 登录密码
     */
    @Schema(description = "password 登录密码")
    @NotBlank(message = "登录密码不能为空")
    private String password;

    /**
     * 确认密码
     */
    @Schema(description = "confirmPassword 确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 商户邮箱
     */
    @Schema(description = "email 商户邮箱")
    @NotBlank(message = "商户邮箱不能为空")
    private String email;

    /**
     * 验证码
     */
    @Schema(description = "code 验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @Schema(description = "第三方登录类型 仅仅供三方登录注册时需要")
    private String thirdLoginType;

    @Schema(description = "第三方登录唯一标识 仅仅供三方登录注册时需要")
    private String unionId;
}
