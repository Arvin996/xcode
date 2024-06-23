package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;


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
@Table("system_user_role")
public class UserRolePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键 无意义
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

}
