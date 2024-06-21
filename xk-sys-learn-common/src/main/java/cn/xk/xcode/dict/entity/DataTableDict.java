package cn.xk.xcode.dict.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2024/5/30 15:13
 * @Version 1.0
 * @Description DataTableDict 字典对象
 */
@Data
@Builder
@AllArgsConstructor
public class DataTableDict
{
    private String code;

    private String name;

    private String pad1;

    private String pad;

    private String parId;

    private String note;

    private String pad2;

    private String pad3;
}
