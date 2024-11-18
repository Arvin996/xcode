package cn.xk.xcode.entity.dto.flavor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/4 9:35
 * @Version 1.0.0
 * @Description FlavorBaseDto
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "口味base dto")
public class FlavorBaseDto {

    @Schema(description = "口味id")
    @NotNull(message = "口味id不能为空")
    private Long id;
}
