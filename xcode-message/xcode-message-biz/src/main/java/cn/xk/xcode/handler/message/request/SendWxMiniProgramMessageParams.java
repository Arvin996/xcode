package cn.xk.xcode.handler.message.request;

import lombok.Data;

import java.util.Map;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 17:55
 * @description SendWxMiniProgramMessageParams
 */
@Data
public class SendWxMiniProgramMessageParams {
    private String template_id;
    private String page;
    private String touser;
    private Map<String, Map<String, Object>> data;
}
