package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/10/29 14:51
 * @Version 1.0.0
 * @Description DataLongObjectBaseEntity
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "数据表通用字段实体类, 只不过用户标识为long")
public class DataLongObjectBaseEntity {

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private Long createUser;

    @Schema(description = "更新人")
    private Long updateUser;
}
