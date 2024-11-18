package cn.xk.xcode.entity.dto.flavor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/11/4 9:52
 * @Version 1.0.0
 * @Description QueryFlavorDto
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "查询口味 dto")
public class QueryFlavorDto {

    /**
     * 口味名称
     */
    @Schema(description = "口味名称")
    private String name;
}
