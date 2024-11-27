package cn.xk.xcode.entity.dto;

import cn.xk.xcode.pojo.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/11/25 16:24
 * @Version 1.0.0
 * @Description QueryGenTableDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "查询代码生成列表dto")
@Accessors(fluent = false)
public class QueryGenTableDto extends PageParam {

    @Schema(description = "表名")
    private String tableName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "开始时间")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "结束时间")
    private LocalDateTime endTime;
}
