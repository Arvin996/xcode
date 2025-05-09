package cn.xk.xcode.entity.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/3/10 10:06
 * @Version 1.0.0
 * @Description DelMeesageChannelAccountDto
 **/
@Data
@Schema(description = "del message channel account dto")
public class DelMessageChannelAccountDto {

    @Schema(description = "id")
    @NotNull(message = "id cannot be null")
    private Integer id;
}
