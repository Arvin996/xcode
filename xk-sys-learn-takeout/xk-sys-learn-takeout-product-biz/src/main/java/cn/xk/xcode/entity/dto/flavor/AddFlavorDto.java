package cn.xk.xcode.entity.dto.flavor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/4 9:11
 * @Version 1.0.0
 * @Description AddFlavorDto
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增口味 dto")
public class AddFlavorDto {

    /**
     * 口味名称
     */
    @Schema(description = "口味名称")
    @NotBlank(message = "口味名称不能为空")
    private String name;

    /**
     * 口味数据list
     */
    @Schema(description = "口味数据list")
    @NotEmpty(message = "口味数据list不能为空")
    private List<String> value;
}
