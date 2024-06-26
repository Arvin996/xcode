package cn.xk.xcode.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/5/27 16:03
 * @Version 1.0
 * @Description SortedPageParam
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Schema(description = "可排序的分页参数")
public class SortedPageParam extends PageParam
{
    @Schema(description = "排序字段")
    private List<SortedField>  sortedFields;
}
