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
 * @author Arvin
 * @since 2024-06-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_resource")
public class ResourcePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源自增主键
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 资源路径 唯一
     */
    private String resourceCode;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源是否启用，0启用 1未启用
     */
    private String status;

    @Column(onUpdateValue = "now()")
    private LocalDateTime updateTime;

    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

}
