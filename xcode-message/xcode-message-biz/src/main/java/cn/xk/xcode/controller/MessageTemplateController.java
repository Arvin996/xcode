package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.template.AddMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.AddMessageTemplateParamsDto;
import cn.xk.xcode.entity.dto.template.QueryMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.UpdateMessageTemplateDto;
import cn.xk.xcode.entity.vo.template.MessageTemplateVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    @Operation(summary = "新增消息模板")
    public CommonResult<Boolean> addMessageTemplate(@Validated @RequestBody AddMessageTemplateDto addMessageTemplateDto) {
        return CommonResult.success(messageTemplateService.addMessageTemplate(addMessageTemplateDto));
    }

    @DeleteMapping("/del/{id}")
    @Operation(summary = "删除消息模板")
    public CommonResult<Boolean> delMessageTemplate(@PathVariable("id") Integer id) {
        return CommonResult.success(messageTemplateService.delMessageTemplate(id));
    }

    @PostMapping("/update")
    @Operation(summary = "修改消息模板")
    public CommonResult<Boolean> updateMessageTemplate(@Validated @RequestBody UpdateMessageTemplateDto updateMessageTemplateDto) {
        return CommonResult.success(messageTemplateService.updateMessageTemplate(updateMessageTemplateDto));
    }

    @PostMapping("/list")
    @Operation(summary = "查询消息模板")
    public CommonResult<PageResult<MessageTemplateVo>> getMessageTemplateList(@RequestBody QueryMessageTemplateDto queryMessageTemplateDto) {
        return CommonResult.success(messageTemplateService.getMessageTemplateList(queryMessageTemplateDto));
    }

    @PostMapping("/addMessageTemplateParam")
    @Operation(summary = "新增消息模板参数")
    public CommonResult<Boolean> addMessageTemplateParam(@RequestBody AddMessageTemplateParamsDto addMessageTemplateParamsDto) {
        return CommonResult.success(messageTemplateService.addMessageTemplateParam(addMessageTemplateParamsDto));
    }

    @DeleteMapping("/delMessageTemplateParam/{id}")
    @Operation(summary = "删除消息模板参数")
    public CommonResult<Boolean> delMessageTemplateParam(@PathVariable("id") Integer id) {
        return CommonResult.success(messageTemplateService.delMessageTemplateParam(id));
    }

}
