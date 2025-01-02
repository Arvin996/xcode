package cn.xk.xcode.entity.trans;

import cn.xk.xcode.core.annotation.FlexFieldTrans;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.entity.po.BizMessagePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/12/27 16:31
 * @Version 1.0.0
 * @Description FlexTransTestEntity2
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexTransTestEntity2 implements TransVo {

    @FlexFieldTrans(ref = BizMessagePo.class, targetField = "name", refConditionField = "id", refSourceFiled = "bizType")
    private Long id;

    private String name;
}
