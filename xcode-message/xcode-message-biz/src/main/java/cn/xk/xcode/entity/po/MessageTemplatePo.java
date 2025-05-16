package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.*;

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_template")
public class MessageTemplatePo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 0自定义模板 1 三方平台的模板
     */
    private String type;

    /**
     * 模板id 指的是在三方平台中定义模板后的id值 或者自定义的
     */
    private String templateId;

    /**
     * 模板内容信息 使用{}占位符
     */
    private String content;

    /**
     * 0 启用 1禁用
     */
    private String status;

    /**
     * 模板描述
     */
    private String desc;

    /**
     * 0 已删除 1 未删除
     */
    @com.mybatisflex.annotation.Column(isLogicDelete = true)
    private String isDeleted;

}
