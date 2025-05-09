package cn.xk.xcode.entity.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/3/10 10:10
 * @Version 1.0.0
 * @Description UpdateMessageChannelAccountDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "update message channel account dto")
public class UpdateMessageChannelAccountDto extends AddMessageChannelAccountDto{

    @Schema(description = "id")
    @NotNull(message = "id cannot be null")
    private Integer id;
}
