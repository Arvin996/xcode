package cn.xk.xcode.entity.trans;

import cn.xk.xcode.core.annotation.FlexEnumTrans;
import cn.xk.xcode.core.annotation.FlexFieldTrans;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.entity.po.BizMessagePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/12/27 16:32
 * @Version 1.0.0
 * @Description FlexTransTestEntity3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexTransTestEntity3 implements TransVo {

    @FlexFieldTrans(ref = BizMessagePo.class, targetField = "name", refConditionField = "id", refSourceFiled = "bizType")
    private List<Long> id;

    private String name;

    @FlexEnumTrans(targetField = "name1", enumType = "enum")
    private String id1;

    private String name1;
}
