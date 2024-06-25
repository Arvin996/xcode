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
 * @author lenovo
 * @since 2024-06-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("infra_database_conn_info")
public class DatabaseConnInfoPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 数据库名
     */
    private String databaseName;

    /**
     * 数据库连接地址 ip:port 只支持mysql
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String userName;

    /**
     * 数据库密码
     */
    private String password;

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
