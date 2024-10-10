package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/10/10 18:01
 * @Version 1.0.0
 * @Description DataObjectBaseEntity
 **/
@Data
@Schema(description = "数据表通用字段实体类")
public class DataObjectBaseEntity {

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private String createUser;

    @Schema(description = "更新人")
    private String updateUser;
}
