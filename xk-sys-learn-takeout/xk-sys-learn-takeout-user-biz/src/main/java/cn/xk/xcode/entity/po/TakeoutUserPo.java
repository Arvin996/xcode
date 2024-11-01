package cn.xk.xcode.entity.po;

import cn.xk.xcode.typehandler.SexTypeHandler;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;


import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2024-10-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("takeout_user")
public class TakeoutUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 0 男 1 女
     */
    @Column(typeHandler = SexTypeHandler.class)
    private String sex;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态 0:正常，1:禁用
     */
    private Integer status;

}
