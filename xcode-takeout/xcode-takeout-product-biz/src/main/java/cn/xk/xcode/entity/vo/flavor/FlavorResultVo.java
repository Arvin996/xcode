package cn.xk.xcode.entity.vo.flavor;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/4 9:58
 * @Version 1.0.0
 * @Description FlavorResultVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "口味查询返回 vo")
public class FlavorResultVo extends DataLongObjectBaseEntity {

    /**
     * 主键
     */
    @Schema(description = "口味id")
    private Long id;

    /**
     * 口味名称
     */
    @Schema(description = "口味名称")
    private String name;

    /**
     * 口味数据list
     */
    @Schema(description = "具体口味数据")
    private List<String> value;
}
