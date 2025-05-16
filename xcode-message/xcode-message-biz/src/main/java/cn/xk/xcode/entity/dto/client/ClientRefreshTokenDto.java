package cn.xk.xcode.entity.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/15 16:26
 * @Version 1.0.0
 * @Description ClientRefreshTokenDto
 **/
@Data
@Schema(name = "ClientRefreshTokenDto", description = "渠道商token刷新dto")
public class ClientRefreshTokenDto {

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull(message = "id 不能为空")
    private Integer id;

    @Schema(description = "accessToken")
    @NotBlank(message = "accessToken 不能为空")
    private String accessToken;
}
