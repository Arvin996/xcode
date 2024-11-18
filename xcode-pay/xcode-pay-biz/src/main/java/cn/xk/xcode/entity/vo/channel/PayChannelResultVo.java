package cn.xk.xcode.entity.vo.channel;

import cn.xk.xcode.entity.po.PayChannelPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/9/26 15:26
 * @Version 1.0.0
 * @Description PayChannelResultVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "支付渠道list vo")
public class PayChannelResultVo {

    private List<PayChannelPo> list;
}
