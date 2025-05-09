package cn.xk.xcode.entity.po;

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
@Table("message_template_params")
public class MessageTemplateParamsPo implements Serializable {

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
