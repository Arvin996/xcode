package cn.xk.xcode.entity.discard.content;

import lombok.*;

import java.util.Map;

/**
 * @author jwq
 * 支付宝小程序订阅消息内容
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlipayMiniProgramContentModel extends ContentModel {
    /**
     * 模板消息发送的数据
     */
    private Map<String, String> miniProgramParam;

    /**
     * 模板Id
     */
    private String templateId;

    /**
     * 跳转链接
     */
    private String page;

}
