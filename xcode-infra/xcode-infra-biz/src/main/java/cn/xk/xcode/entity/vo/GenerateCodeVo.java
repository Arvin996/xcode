package cn.xk.xcode.entity.vo;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/11/25 11:30
 * @Version 1.0.0
 * @Description GenerateCodeVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "代码生成返回")
public class GenerateCodeVo {

    @JsonRawValue
    @Schema(description = "代码")
    private String code;
}
