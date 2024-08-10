package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
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
 * @author lenovo
 * @since 2024-06-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("infra_table_info")
public class TableInfoPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表所属数据库
     */
    @Id
    private Integer databaseId;

    /**
     * 表名
     */
    @Id
    private String tableName;

    /**
     * 表名前缀
     */
    private String tablePre;

    /**
     * 实体类后缀
     */
    private String entitySuff;

    /**
     * 创建用户
     */
    private String createUser;

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
