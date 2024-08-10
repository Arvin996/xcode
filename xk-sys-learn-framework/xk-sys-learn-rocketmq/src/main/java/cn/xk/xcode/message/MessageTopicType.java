package cn.xk.xcode.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/7/4 14:51
 * @Version 1.0
 * @Description MessageTopicType
 */
@AllArgsConstructor
@Getter
public enum MessageTopicType
{
    TOPIC_BIZ_LOG("bizLog"),
    TOPIC_GW_LOG("gwLog"),
    TOPIC_TRACE_LOG("traceLog"),
    TOPIC_LOGIN_LOG("loginLog"),
    TOPIC_OTHER("other");

    private final String type;
}
