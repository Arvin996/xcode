package cn.xk.xcode.entity.dict;

import cn.xk.xcode.annotation.DictFieldTrans;
import cn.xk.xcode.entity.TransPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/12/26 16:37
 * @Version 1.0.0
 * @Description DictTestEntity
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictTestEntity1 implements TransPojo {

    @DictFieldTrans(dictType = "enum", targetField = "name")
    private String code;

    private String name;

    private List<DictTestEntity2> list;
}
