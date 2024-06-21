package cn.xk.xcode.entity.vo;

import cn.xk.xcode.entity.po.DictPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/6/21 11:21
 * @Version 1.0
 * @Description DictVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictVo
{
    private String code;

    private String name;

    private String parId;

    String note;

    String pad;
}
