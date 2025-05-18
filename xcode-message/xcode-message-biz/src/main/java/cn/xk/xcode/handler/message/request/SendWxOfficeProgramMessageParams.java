package cn.xk.xcode.handler.message.request;

import lombok.Data;

import java.util.Map;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 18:14
 * @description SendWxOfficeProgramMessageParams
 */
@Data
public class SendWxOfficeProgramMessageParams {
    private String template_id;
    private Map<String, Object> miniprogram;
    private String touser;
    private Map<String, Map<String, Object>> data;
}
