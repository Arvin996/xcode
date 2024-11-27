package cn.xk.xcode.entity.vo;

import cn.xk.xcode.entity.po.GenTableColumnPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author xuk
 * @Date 2024/11/26 13:42
 * @Version 1.0.0
 * @Description GenTableColumnVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "表属性返回")
public class GenTableColumnVo extends GenTableColumnPo {


}
