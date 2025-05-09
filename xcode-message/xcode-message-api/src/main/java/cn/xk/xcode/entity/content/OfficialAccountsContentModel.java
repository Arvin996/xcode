package cn.xk.xcode.entity.content;

import lombok.*;

import java.util.Map;

/**
 * 微信服务号
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfficialAccountsContentModel extends ContentModel {

    /**
     * 模板消息发送的数据
     */
    private Map<String, String> officialAccountParam;

    /**
     * 模板消息跳转的url
     */
    private String url;

    /**
     * 模板Id
     */
    private String templateId;

    /**
     * 模板消息跳转小程序的appid
     */
    private String miniProgramId;

    /**
     * 模板消息跳转小程序的页面路径
     */
    private String path;

}
