package cn.xk.xcode.entity.discard.content;

import lombok.*;

/**
 * 飞书群 机器人
 * <p>
 * <a href="https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN#756b882f">...</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeiShuRobotContentModel extends ContentModel {

    /**
     * 发送类型
     */
    private String sendType;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送标题
     */
    private String title;

    /**
     * 媒体Id
     */
    private String mediaId;


    /**
     * 富文本内容：[[{"tag":"text","text":"项目有更新: "},{"tag":"a","text":"请查看","href":"http://www.example.com/"},{"tag":"at","user_id":"ou_18eac8********17ad4f02e8bbbb"}]]
     */
    private String postContent;

}
