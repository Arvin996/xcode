package cn.xk.xcode.service.sms;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/3/13 14:25
 * @Version 1.0.0
 * @Description SmsReqDto
 **/
@Data
@Builder
public class SmsReqDto {

    private Integer taskId;

    private Integer accountId;

    private Set<String> phones;

    private String content;
}
