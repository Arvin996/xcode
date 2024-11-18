package cn.xk.xcode.entity.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/30 10:16
 * @Version 1.0.0
 * @Description AddressBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "用户收货地址base dto")
public class AddressBaseDto {

    @Schema(description = "收货地址id")
    @NotNull(message = "收货地址id不能为空")
    private Long id;
}
