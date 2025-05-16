package cn.xk.xcode.entity.discard.content;

import lombok.*;

/**
 * 邮件消息体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentModel extends ContentModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容(可写入HTML)
     */
    private String content;

    /**
     * 邮件附件链接
     */
    private String url;
}
