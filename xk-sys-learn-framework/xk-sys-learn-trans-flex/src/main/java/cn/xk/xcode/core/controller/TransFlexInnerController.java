package cn.xk.xcode.core.controller;

import cn.xk.xcode.core.entity.EnumTransDto;
import cn.xk.xcode.core.entity.FlexTransDto;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.core.service.FlexTransService;
import cn.xk.xcode.support.enums.GlobalEnumsContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 14:16
 * @description
 */
@RestController
@RequiredArgsConstructor
public class TransFlexInnerController {

    private final FlexTransService flexTransService;
    private final GlobalEnumsContext globalEnumsContext;

    @PostMapping("/trans/flex")
    public TransVo flexTrans(@RequestBody FlexTransDto flexTransDto){
        return flexTransService.findById(flexTransDto.getId(), flexTransDto.getTargetClazz(), flexTransDto.getConditionField());
    }

    @PostMapping("/trans/flex/list")
    public List<? extends TransVo> flexTransList(@RequestBody FlexTransDto flexTransDto){
        return flexTransService.findList(flexTransDto.getId(), flexTransDto.getTargetClazz(), flexTransDto.getConditionField());
    }

    @PostMapping("/trans/enum")
    public Object enumTrans(@RequestBody EnumTransDto enumTransDto){
        return globalEnumsContext.getValue(enumTransDto.getEnumType(), enumTransDto.getKey());
    }
}
