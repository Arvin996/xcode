package cn.xk.xcode.controller.front;

import cn.xk.xcode.entity.dto.template.AddMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.DelMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.QueryMessageTemplateDto;
import cn.xk.xcode.entity.vo.MessageTemplateVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageTemplateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/10 10:15
 * @Version 1.0.0
 * @Description MessageTemplateController
 **/
@RestController
@RequestMapping("/message/template")
@Tag(name = "消息模板接口")
public class MessageTemplateController {

    @Resource
    private MessageTemplateService messageTemplateService;

    @PostMapping("/add")
    public CommonResult<Boolean> addMessageTemplate(@Validated @RequestBody AddMessageTemplateDto addMessageTemplateDto) {
        return CommonResult.success(messageTemplateService.addMessageTemplate(addMessageTemplateDto));
    }

    @PostMapping("/del")
    public CommonResult<Boolean> delMessageTemplate(@Validated @RequestBody DelMessageTemplateDto delMessageTemplateDto) {
        return CommonResult.success(messageTemplateService.removeById(delMessageTemplateDto.getId()));
    }

    @PostMapping("/list")
    public CommonResult<List<MessageTemplateVo>> getMessageTemplate(@RequestBody QueryMessageTemplateDto queryMessageTemplateDto) {
        return CommonResult.success(messageTemplateService.getMessageTemplate(queryMessageTemplateDto));
    }

}
