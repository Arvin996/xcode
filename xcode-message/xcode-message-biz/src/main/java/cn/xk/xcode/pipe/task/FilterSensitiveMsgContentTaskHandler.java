package cn.xk.xcode.pipe.task;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.pipeline.TaskContext;
import cn.xk.xcode.core.pipeline.TaskHandler;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageTemplateParamsPo;
import cn.xk.xcode.entity.po.MessageTemplatePo;
import cn.xk.xcode.enums.MessageContentType;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.pipe.model.SendMessageTaskModel;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageTemplateParamsService;
import cn.xk.xcode.service.MessageTemplateService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS;
import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_TEMPLATE_CONTENT_NOT_DEFINED;
import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_TEMPLATE_NOT_EXISTS;
import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_TEMPLATE_PARAMS_NOT_MATCH;
import static cn.xk.xcode.entity.def.MessageTemplateParamsTableDef.MESSAGE_TEMPLATE_PARAMS;
import static cn.xk.xcode.entity.def.MessageTemplateTableDef.MESSAGE_TEMPLATE;

/**
 * @Author xuk
 * @Date 2025/5/26 9:09
 * @Version 1.0.0
 * @Description FilterSensitiveMsgContentTaskHandler
 **/
@Component
@Slf4j
public class FilterSensitiveMsgContentTaskHandler implements TaskHandler<SendMessageTaskModel> {

    @Resource
    private SensitiveWordBs sensitiveWordBs;

    @Resource
    private MessageTemplateService messageTemplateService;

    @Resource
    private MessageTemplateParamsService messageTemplateParamsService;

    @Override
    public void handle(TaskContext<SendMessageTaskModel> taskContext) {
        MessageTask messageTask = taskContext.getTaskModel().getMessageTask();
        try {
            filterSensitiveMsgContent(messageTask);
        }catch (Exception e){
            log.error("FilterSensitiveMsgContentTaskHandler#handle error:{}", e.getMessage());
            taskContext.setIsBreak(true);
            if (e instanceof ServiceException){
                taskContext.setResult(CommonResult.error((ServiceException) e));
            } else if (e instanceof ServerException) {
                taskContext.setResult(CommonResult.error((ServerException) e));
            }else {
                taskContext.setResult(CommonResult.error(EXEC_MESSAGE_TASK_FAILED, null, getHandlerName()));
            }
        }
    }

    @Override
    public int getOrder() {
        return SEND_MESSAGE_TASK_STEP_ONE;
    }

    @Override
    public String getCode() {
        return SEND_MESSAGE_TASK_CODE;
    }

    private void filterSensitiveMsgContent(@NotNull MessageTask messageTask) {
        String msgContentType = messageTask.getMsgContentType();
        if (MessageContentType.PLAIN.getCode().equals(msgContentType)) {
            if (StrUtil.isBlank(messageTask.getMessageContent())) {
                ExceptionUtil.castServiceException(PLAIN_TEXT_MESSAGE_MUST_NOT_EMPTY);
            }
            String messageContent = messageTask.getMessageContent();
            if (sensitiveWordBs.contains(messageContent)) {
                ExceptionUtil.castServerException(MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS);
            }
        } else {
            MessageTemplatePo messageTemplatePo = messageTemplateService.getOne(MESSAGE_TEMPLATE.TEMPLATE_ID.eq(messageTask.getTemplateCode()));
            if (Objects.isNull(messageTemplatePo)) {
                ExceptionUtil.castServerException(MESSAGE_TEMPLATE_NOT_EXISTS, messageTask.getTemplateCode());
            }
            String templateType = messageTemplatePo.getType();
            messageTask.setTemplateId(messageTemplatePo.getId());
            // 自定义模板
            if ("0".equals(templateType)) {
                String content = messageTemplatePo.getContent();
                if (!StringUtils.hasLength(content)) {
                    ExceptionUtil.castServerException(MESSAGE_TEMPLATE_CONTENT_NOT_DEFINED, messageTask.getTemplateId());
                }
                List<MessageTemplateParamsPo> templateParamsPoList = messageTemplateParamsService.list(MESSAGE_TEMPLATE_PARAMS.TEMPLATE_ID.eq(messageTask.getTemplateId()));
                if (CollectionUtil.isEmpty(templateParamsPoList)) {
                    messageTask.setMessageContent(content);
                    return;
                }
                String contentValueParams = messageTask.getContentValueParams();
                JSONObject jsonObject = JSON.parseObject(contentValueParams);
                if (jsonObject.size() != templateParamsPoList.size()) {
                    ExceptionUtil.castServerException(MESSAGE_TEMPLATE_PARAMS_NOT_MATCH);
                }
                String realContent = formatContent(content, templateParamsPoList, jsonObject);
                if (sensitiveWordBs.contains(realContent)) {
                    log.warn("敏感词信息：{}", sensitiveWordBs.findAll(realContent));
                    ExceptionUtil.castServerException(MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS);
                }
                messageTask.setMessageContent(realContent);
            } else {
                // 三方模板
                String contentValueParams = messageTask.getContentValueParams();
                if (StrUtil.isNotBlank(contentValueParams)) {
                    if (sensitiveWordBs.contains(contentValueParams)) {
                        ExceptionUtil.castServerException(MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS);
                    }
                }
            }
        }
    }

    private String formatContent(@NotNull String content, List<MessageTemplateParamsPo> templateParamsPoList, JSONObject jsonObject) {
        for (MessageTemplateParamsPo messageTemplateParamsPo : templateParamsPoList) {
            String paramName = messageTemplateParamsPo.getName();
            if (jsonObject.containsKey(paramName)) {
                String value = jsonObject.getString(paramName);
                content = content.replace("{" + paramName + "}", value);
            }
        }
        return content;
    }
}
