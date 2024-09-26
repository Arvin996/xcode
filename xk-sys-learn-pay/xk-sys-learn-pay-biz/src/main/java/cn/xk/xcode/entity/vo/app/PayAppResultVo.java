package cn.xk.xcode.entity.vo.app;

import cn.xk.xcode.entity.po.PayAppPo;
import cn.xk.xcode.entity.po.PayChannelPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/9/26 14:41
 * @Version 1.0.0
 * @Description PayAppResultVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "支付应用list vo")
public class PayAppResultVo {

    private List<PayAppChannelResult> list;


    @Data
    @Builder
    public static class PayAppChannelResult {
        private PayAppPo payAppPo;
        private List<PayChannelPo> list;
    }
}
