package cn.xk.xcode.entity.discard.account.other;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 创建个推账号时的元信息
 * <p>
 * （在调用个推的api时需要用到部分的参数）
 * <p>
 * <a href="https://docs.getui.com/getui/start/devcenter/">...</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeTuiAccount {

    private String appId;

    private String appKey;

    private String masterSecret;
}
