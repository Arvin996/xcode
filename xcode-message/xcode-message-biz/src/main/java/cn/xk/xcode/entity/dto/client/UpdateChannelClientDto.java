package cn.xk.xcode.entity.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/15 15:25
 * @Version 1.0.0
 * @Description UpdateChannelClientDto
 **/
@Data
@Schema(name = "QueryChannelClientDto", description = "修改渠道接入商dto")
public class UpdateChannelClientDto {

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull(message = "id 不能为空")
    private Integer id;

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
    private String email;

    /**
     * 接入商手机号
     */
    @Schema(description = "接入商手机号")
    private String mobile;
}
