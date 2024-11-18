package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author lenovo
 * @since 2024-06-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_dict")
public class DictPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典id
     */
    @Id
    private String code;

    /**
     * 字典值
     */
    private String name;

    /**
     * 父字典id,一级为##
     */
    @Id
    private String parId;

    /**
     * 字典表备注，如排序
     */
    private String note;

    /**
     * 填充字段
     */
    private String pad;

}
