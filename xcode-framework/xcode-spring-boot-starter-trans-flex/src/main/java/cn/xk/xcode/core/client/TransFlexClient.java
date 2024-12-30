package cn.xk.xcode.core.client;

import cn.xk.xcode.core.entity.EnumTransDto;
import cn.xk.xcode.core.entity.FlexTransDto;
import cn.xk.xcode.core.entity.TransPo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 14:42
 * @description
 */
public interface TransFlexClient {

    @PostMapping("/trans/flex")
    TransPo flexTrans(@RequestBody FlexTransDto flexTransDto);

    @PostMapping("/trans/flex/list")
    List<? extends TransPo> flexTransList(@RequestBody FlexTransDto flexTransDto);

    @PostMapping("/trans/enum")
    Object enumTrans(@RequestBody EnumTransDto enumTransDto);
}
