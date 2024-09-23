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
 * @author Administrator
 * @since 2024-09-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pay_app")
public class PayAppPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id 自增
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用状态 0开启 1关闭
     */
    private String status;

    /**
     * 应用备注
     */
    private String remark;

    /**
     * 支付结果的回调地址
     */
    private String orderNotifyUrl;

    /**
     * 退款结果的回调地址
     */
    private String refundNotifyUrl;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
