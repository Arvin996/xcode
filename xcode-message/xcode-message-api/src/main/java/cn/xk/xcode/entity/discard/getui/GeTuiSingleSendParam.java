package cn.xk.xcode.entity.discard.getui;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * @Author xuk
 * @Date 2025/3/10 15:26
 * @Version 1.0.0
 * @Description SendPushParam
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GeTuiSingleSendParam {

    /**
     * requestId
     */
    @JSONField(name = "request_id")
    private String requestId;
    /**
     * settings
     */
    @JSONField(name = "settings")
    private SettingsVO settings;
    /**
     * audience
     */
    @JSONField(name = "audience")
    private AudienceVO audience;
    /**
     * pushMessage
     */
    @JSONField(name = "push_message")
    private PushMessageVO pushMessage;

    /**
     * SettingsVO
     */
    @NoArgsConstructor
    @Data
    public static class SettingsVO {
        /**
         * ttl
         */
        @JSONField(name = "ttl")
        private Integer ttl;
    }

    /**
     * AudienceVO
     */
    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    @Builder
    public static class AudienceVO {
        /**
         * cid
         */
        @JSONField(name = "cid")
        private Set<String> cid;
    }

    /**
     * PushMessageVO
     */
    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    @Builder
    public static class PushMessageVO {
        /**
         * notification
         */
        @JSONField(name = "notification")
        private NotificationVO notification;

        /**
         * NotificationVO
         */
        @NoArgsConstructor
        @Data
        @AllArgsConstructor
        @Builder
        public static class NotificationVO {
            /**
             * title
             */
            @JSONField(name = "title")
            private String title;
            /**
             * body
             */
            @JSONField(name = "body")
            private String body;
            /**
             * clickType
             */
            @JSONField(name = "click_type")
            private String clickType;
            /**
             * url
             */
            @JSONField(name = "url")
            private String url;
        }
    }
}
