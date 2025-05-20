package cn.xk.xcode.handler.message.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/20 9:14
 * @Version 1.0.0
 * @Description SendMessageResponse
 **/
@Data
@Schema(name = "SendMessageResponse", description = "SendMessageResponse 发送消息返回")
public class SendMessageResponse {

    @Schema(description = "successCount 成功数")
    private Integer successCount;

    @Schema(description = "failCount 失败数")
    private Integer failCount;

    @Schema(description = "failMessageDetailList 失败详情")
    private List<FailMessageDetail> failMessageDetailList;

    @Schema(description = "failMessageDetail 失败详情")
    @Data
    public static class FailMessageDetail {
        @Schema(description = "receiver 接收人")
        private String receiver;

        @Schema(description = "failMsg 失败原因")
        private String failMsg;
    }
}
