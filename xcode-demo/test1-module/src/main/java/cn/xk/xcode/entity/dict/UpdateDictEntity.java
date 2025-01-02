package cn.xk.xcode.entity.dict;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2024/12/26 17:05
 * @Version 1.0.0
 * @Description UpdateDictEntity
 **/
@Data
@AllArgsConstructor
public class UpdateDictEntity {
    // 字典code
    private String code;

    // 字典code名称
    private String name;

    // 字典类型
    private String dictType;

    // 字典code功能描述
    private String desc;
}
