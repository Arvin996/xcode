package cn.xk.xcode.entity.vo;

import cn.xk.xcode.entity.po.GenTableColumnPo;
import cn.xk.xcode.entity.po.GenTablePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/25 16:26
 * @Version 1.0.0
 * @Description GenTableResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "在线代码返回")
public class GenTableResultVo extends GenTablePo {

    @Schema(description = "属性信息")
    private List<GenTableColumnPo> columns;

    @Schema(description = "子表信息")
    private GenTableResultVo subTable;

    @Schema(description = "主键信息")
    private GenTableColumnPo pkColumn;
}
