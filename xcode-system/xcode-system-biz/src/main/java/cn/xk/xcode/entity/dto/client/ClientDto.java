package cn.xk.xcode.entity.dto.client;

import com.mybatisflex.annotation.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/25 19:22
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "OAuth2.0 Client")
public class ClientDto {

    /**
     * 客户端id
     */
    @NotNull(message = "clientId cannot be null")
    @Schema(description = "客户端编号", example = "xk-sys-learn")
    private String clientId;

    /**
     * 客户端key
     */
    @Schema(description = "客户端key", example = "xk-sys-learn")
    private String clientKey;

    /**
     * 客户端秘钥
     */
    @Schema(description = "客户端秘钥", example = "xk-sys-learn")
    private String clientSecret;

    /**
     * 授权类型
     */
    @Schema(description = "授权类型", example = "password")
    private List<String> grantType;

    /**
     * 状态 0 可用 1不可用
     */
    @Schema(description = "状态 0 可用 1不可用", example = "0")
    private Integer status = 0;

    /**
     * 访问令牌过期时间
     */
    @Schema(description = "访问令牌过期时间", example = "3600")
    private Integer accessTokenValidity = 3600;

    /**
     * 刷新令牌过期时间
     */
    @Schema(description = "刷新令牌过期时间", example = "3600")
    private Integer refreshTokenValidity = 3600;

    /**
     * 允许的scope
     */
    @Schema(description = "允许的scopes", example = "all")
    @NotBlank(message = "scopes cannot be null")
    private List<String> scopes;

    /**
     * 允许重定向的地址
     */
    @Schema(description = "允许重定向的地址", example = "http://localhost:8080/oauth2/callback")
    @NotBlank(message = "redirectUris cannot be null")
    private String redirectUris;

}
