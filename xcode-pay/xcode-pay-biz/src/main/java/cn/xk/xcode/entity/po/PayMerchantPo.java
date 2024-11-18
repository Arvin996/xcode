package cn.xk.xcode.entity.po;

import cn.xk.xcode.listener.BaseStringEntityChangeListener;
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
@Table(value = "pay_merchant", onInsert = BaseStringEntityChangeListener.class)
public class PayMerchantPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户id 自增
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户简称
     */
    private String merchantShortName;

    /**
     * 商户状态 0 开启 1关闭
     */
    private String status;

    /**
     * 商户备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUser;

}
