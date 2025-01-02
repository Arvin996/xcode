package cn.xk.xcode.entity.trans;

import cn.xk.xcode.core.annotation.FlexEnumTrans;
import cn.xk.xcode.core.annotation.IgnoreRecFlexTrans;
import cn.xk.xcode.core.entity.TransVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/12/30 10:03
 * @Version 1.0.0
 * @Description FlexTransTestEntity4
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlexTransTestEntity4 implements TransVo {

    private FlexTransTestEntity3 flexTransTestEntity3;
    @IgnoreRecFlexTrans
    private List<FlexTransTestEntity2> flexTransTestEntity2List;
    @FlexEnumTrans(targetField = "name1", enumType = "enum")
    private String id1;

    private String name1;
}
