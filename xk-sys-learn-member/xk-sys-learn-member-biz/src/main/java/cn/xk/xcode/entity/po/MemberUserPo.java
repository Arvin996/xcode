package cn.xk.xcode.entity.po;

import cn.xk.xcode.typehandler.ListIntTypeHandler;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类。
 *
 * @author lenovo
 * @since 2024-08-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("member_user")
public class MemberUserPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id
    private String id;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户邮箱 用于绑定
     */
    private String email;

    /**
     * 0 正常 1禁用
     */
    private String status;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户等级
     */
    private Integer levelId;

    /**
     * 用户经验
     */
    private Integer experience;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户积分
     */
    private Integer point;

    /**
     * 用户标签
     */
    @Column(typeHandler = ListIntTypeHandler.class)
    private List<Integer> tagIds;

    /**
     * 用户分组id
     */
    private Integer groupId;

    /**
     * 用户性别 0男 1女
     */
    private String sex;

    /**
     * 用户生日
     */
    private String birthday;

    /**
     * 最近一次登录ip
     */
    private String loginIp;

    /**
     * 最近一次登录地区
     */
    private Integer loginAreaId;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

}
