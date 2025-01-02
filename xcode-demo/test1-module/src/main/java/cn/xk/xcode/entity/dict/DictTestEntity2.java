package cn.xk.xcode.entity.dict;

import cn.xk.xcode.annotation.DictFieldTrans;
import cn.xk.xcode.entity.TransPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/12/26 16:54
 * @Version 1.0.0
 * @Description DictTestEntity2
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictTestEntity2 implements TransPojo {

    @DictFieldTrans(dictType = "enum", targetField = "name")
    private String code;

    private String name;
}
