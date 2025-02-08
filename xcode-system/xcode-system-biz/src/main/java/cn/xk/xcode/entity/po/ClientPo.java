package cn.xk.xcode.entity.po;

import cn.xk.xcode.typehandler.ListStringTypeHandler;
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
 *  实体类。
 *
 * @author lenovo
 * @since 2024-06-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_client")
public class ClientPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端id
     */
    @Id
    private String clientId;

    /**
     * 申请人标识
     */
    private String subjectId;

    /**
     * 客户端key
     */
    private String clientKey;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    @Column(typeHandler = ListStringTypeHandler.class)
    private List<String> scopes;

    /**
     * 授权类型
     */
    private String grantType;

    /**
     * 允许重定向的地址
     */
    @Column(typeHandler = ListStringTypeHandler.class)
    private List<String> redirectUris;

    /**
     * 允许的scope
     */
    @Column(typeHandler = ListStringTypeHandler.class)
    private List<String> scope;

    /**
     * 访问令牌过期时间
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌过期时间
     */
    private Integer refreshTokenValidity;

    /**
     * 状态 0 可用 1不可用
     */
    private String status;

    /**
     * 创建人
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
