package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-03-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_template")
public class MessageTemplatePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板所属用户id
     */
    private Integer clientId;

    /**
     * 模板内容信息 使用{}占位符
     */
    private String content;

    /**
     * 0 启用 1禁用
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "now()")
    private LocalDateTime updateTime;

}
