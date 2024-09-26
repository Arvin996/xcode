package cn.xk.xcode.entity.dto.app;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/9/26 14:23
 * @Version 1.0.0
 * @Description PayAppBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "支付应用base dto")
public class PayAppBaseDto {

    @NotNull(message = "支付应用id 不能为空")
    @Schema(description = "支付应用id")
    private Integer id;
}
