package cn.xk.xcode.entity.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/9 10:15
 * @Version 1.0.0
 * @Description QueryDictDto
 **/
@Data
@Schema(name = "QueryDictDto 字典查询实体类")
public class QueryDictDto {

    @Schema(description = "dictTypeList 字典类型列表", example = "['sex']")
    private List<String> dictTypeList;

    @Schema(description = "status 字典状态", example = "0 正常 1禁用")
    private String status;

    @Schema(description = "label 字典标签", example = "男")
    private String label;
}
