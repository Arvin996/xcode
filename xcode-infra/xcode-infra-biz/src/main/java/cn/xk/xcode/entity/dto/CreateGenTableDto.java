package cn.xk.xcode.entity.dto;

import cn.xk.xcode.validation.Xss;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/11/25 17:06
 * @Version 1.0.0
 * @Description CreateGenTableDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "使用sql创建表生成dto")
public class CreateGenTableDto extends BaseGenDto{

    @NotBlank(message = "sql不能为空")
    @Xss
    private String sql;
}
