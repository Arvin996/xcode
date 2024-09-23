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
@Table("pay_app_channel")
public class PayAppChannelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 应用id
     */
    private Integer appId;

    /**
     * 渠道id
     */
    private Integer channelId;

}
