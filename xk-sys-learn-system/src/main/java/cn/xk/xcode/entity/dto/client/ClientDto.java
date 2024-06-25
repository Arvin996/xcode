package cn.xk.xcode.entity.dto.client;

import com.mybatisflex.annotation.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
    @NotNull
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
    private String grantType;
}
