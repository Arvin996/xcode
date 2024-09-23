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
 * @author Administrator
 * @since 2024-09-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pay_channel")
public class PayChannelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道id 自增
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 支付渠道编码
     */
    private String channelCode;

    /**
     * 渠道状态 0 可用 1不可用
     */
    private String status;

    /**
     * 渠道费率，单位：百分比
     */
    private String feeRate;

    /**
     * 渠道备注
     */
    private String remark;

}
