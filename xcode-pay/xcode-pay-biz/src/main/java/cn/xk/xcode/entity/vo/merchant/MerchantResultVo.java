package cn.xk.xcode.entity.vo.merchant;

import cn.xk.xcode.entity.po.PayMerchantPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/9/23 16:12
 * @Version 1.0.0
 * @Description MerchantResultVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "商户list vo")
public class MerchantResultVo {

    private List<PayMerchantPo> payMerchantPoList;
}
