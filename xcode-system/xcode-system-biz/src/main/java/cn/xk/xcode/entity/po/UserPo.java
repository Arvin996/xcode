package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

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
@Table("system_user")
public class UserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    private Long id;

    /**
     * 用户名 不能重复
     */
    private String username;

    /**
     * 用户登录密码
     */
    @Length(min = 8, max = 16)
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String userpic;

    /**
     * qq
     */
    private String qq;

    /**
     * 微信
     */
    private String wx;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户状态 0在线 1离线 2封禁
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
