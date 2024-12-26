package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2024/5/30 15:13
 * @Version 1.0
 * @Description DataTableDict 字典对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictDataEntity implements Serializable {

    // 字典code
    private String code;

    // 字典code名称
    private String name;

    // 字典类型
    private String dictType;

    // 字典code功能描述
    private String desc;

}
