package cn.xk.xcode.entity.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/15 9:34
 * @Version 1.0.0
 * @Description AliYunSmsAccount
 **/
@Data
@Schema(name = "AliYunSmsAccount", description = "阿里云短信平台账户")
public class AliYunSmsAccount {

    @Schema(description = "regionId")
    private String regionId;

    @Schema(description = "accessKeyId")
    private String accessKeyId;

    @Schema(description = "secret")
    private String secret;

    @Schema(description = "signName")
    private String signName;
}
