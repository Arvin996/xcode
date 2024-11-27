package cn.xk.xcode.entity.dto;

import cn.xk.xcode.entity.vo.GenTableResultVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/11/26 14:52
 * @Version 1.0.0
 * @Description UpdateGenTableDto
 **/
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "更新表生成信息dto")
public class UpdateGenTableDto extends GenTableResultVo {

    /** 请求参数 */
    @Schema(description = "请求参数")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
