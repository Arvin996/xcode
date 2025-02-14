package cn.xk.xcode.core.extra.model;

import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.retry.support.RetryTemplate;
import cn.xk.xcode.core.extra.options.XcodeDeepSeekChatOptions;

/**
 * @Author xuk
 * @Date 2025/2/13 15:59
 * @Version 1.0.0
 * @Description XcodeDeepSeekChatModel
 **/
public class XcodeDeepSeekChatModel  extends AbstractExtraChatModel {

    public static final String BASE_URL = "https://api.deepseek.com";


    public XcodeDeepSeekChatModel(OpenAiApi openAiApi, XcodeDeepSeekChatOptions options) {
        this(openAiApi, options, RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }

    public XcodeDeepSeekChatModel(OpenAiApi openAiApi, XcodeDeepSeekChatOptions options, RetryTemplate retryTemplate) {
        super(retryTemplate, openAiApi, options);
    }

}
