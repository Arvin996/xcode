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
 * @author lenovo
 * @since 2024-07-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_third_user")
public class ThirdUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信登录用户id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 绑定用户表
     */
    private Integer userId;

    /**
     * 微信unionid
     */
    private String unionId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户头像url
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
