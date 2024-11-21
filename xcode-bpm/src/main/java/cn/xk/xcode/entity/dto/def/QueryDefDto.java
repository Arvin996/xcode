package cn.xk.xcode.entity.dto.def;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/11/20 10:06
 * @Version 1.0.0
 * @Description QueryDefDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "流程分页查询dto")
public class QueryDefDto extends PageParam {
    @Schema(description = "流程编码")
    private String flowCode;
    @Schema(description = "流程名称")
    private String flowName;
    @Schema(description = "流程分类")
    private String category;
    @Schema(description = "流程版本")
    private String version;
}
