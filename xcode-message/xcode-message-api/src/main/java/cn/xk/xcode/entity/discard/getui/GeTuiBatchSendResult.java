package cn.xk.xcode.entity.discard.getui;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/4/18 13:41
 * @Version 1.0.0
 * @Description GeTuiBatchSendResult
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeTuiBatchSendResult {

    /**
     * msg
     */
    @JSONField(name = "msg")
    private String msg;
    /**
     * code
     */
    @JSONField(name = "code")
    private Integer code;
    /**
     * data
     */
    @JSONField(name = "data")
    private JSONObject data;


    @Data
    public static class SingleResult {
        @JSONField(name = "cid")
        private String cid;

        @JSONField(name = "status")
        private String status;
    }
}
