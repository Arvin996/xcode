package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/26 15:14
 * @Version 1.0.0
 * @Description DelGenTableDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "删除表定义 dto")
public class DelGenTableDto {

    @Schema(description = "表id")
    @NotEmpty(message = "表id列表不能为空")
    private List<Long> tableIds;
}
