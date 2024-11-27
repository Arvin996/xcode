package cn.xk.xcode.entity.vo;

import cn.xk.xcode.entity.po.GenTableColumnPo;
import cn.xk.xcode.entity.po.GenTablePo;
import cn.xk.xcode.pojo.PageResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/11/25 16:57
 * @Version 1.0.0
 * @Description GenTableDetailVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "代码生成详细返回")
public class GenTableDetailVo {

    @Schema(description = "表信息")
    private GenTablePo  genTablePo;

    @Schema(description = "属性信息")
    private PageResult<GenTableColumnPo> columnList;
}
