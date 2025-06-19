package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/11/25 16:12
 * @Version 1.0.0
 * @Description BaseGenDto
 **/
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Schema(description = "在线代码生成base dto")
public class BaseGenDto {

    @Schema(description = "作者")
    private String author;

    @Schema(description = "生成包路径")
    public String packageName;

    @Getter
    @Schema(description = "自动去除表前缀，默认是false")
    public boolean autoRemovePre;

    @Schema(description = "表前缀(类名不会包含表前缀)")
    public String tablePrefix;
}
