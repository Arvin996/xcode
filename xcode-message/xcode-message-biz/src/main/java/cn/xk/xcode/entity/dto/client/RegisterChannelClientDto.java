package cn.xk.xcode.entity.dto.client;

import cn.xk.xcode.validation.Email;
import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/15 11:24
 * @Version 1.0.0
 * @Description RegisterChannelClientDto
 **/
@Data
@Schema(name = "RegisterChannelClientDto", description = "申请注册渠道接入商dto")
public class RegisterChannelClientDto {

    /**
     * 接入商名称
     */
    @Schema(description = "接入商名称")
    @NotBlank(message = "name 不能为空")
    private String name;

    /**
     * 接入商邮箱
     */
    @Schema(description = "接入商邮箱")
    @Email
    private String email;

    /**
     * 接入商手机号
     */
    @Schema(description = "接入商手机号")
    @Mobile
    private String mobile;

    /**
     * 接入商消息配额 默认100
     */
    @Schema(description = "接入商消息配额 默认100")
    @Min(value = 1, message = "accessCount 不能小于1")
    @Max(value = 500, message = "accessCount 不能大于500")
    private Integer accessCount = 100;


}
