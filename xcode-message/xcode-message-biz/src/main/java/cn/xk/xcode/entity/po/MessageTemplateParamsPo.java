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
@Table("message_template_params")
public class MessageTemplateParamsPo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 模板id
     */
    private Integer templateId;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数描述
     */
    private String desc;

}
