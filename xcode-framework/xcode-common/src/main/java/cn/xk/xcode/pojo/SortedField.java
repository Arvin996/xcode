package cn.xk.xcode.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2024/5/27 16:04
 * @Version 1.0
 * @Description SortedField
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortedField implements Serializable {

    /**
     * 顺序 - 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 顺序 - 降序
     */
    public static final String ORDER_DESC = "desc";

    private String fieldName;

    private String sortType;
}

