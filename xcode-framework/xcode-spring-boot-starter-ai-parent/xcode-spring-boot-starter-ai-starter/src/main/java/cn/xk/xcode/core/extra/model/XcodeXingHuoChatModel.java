package cn.xk.xcode.core.extra.model;

import cn.xk.xcode.core.extra.options.XcodeXingHuoChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.retry.support.RetryTemplate;

/**
 * @Author xuk
 * @Date 2025/2/13 16:47
 * @Version 1.0.0
 * @Description XcodeXingHuoChatModel
 **/
public class XcodeXingHuoChatModel extends AbstractExtraChatModel {

    public static final String BASE_URL = "https://spark-api-open.xf-yun.com";


    public XcodeXingHuoChatModel(OpenAiApi openAiApi, XcodeXingHuoChatOptions options) {
        this(openAiApi, options, RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }

    public XcodeXingHuoChatModel(OpenAiApi openAiApi, XcodeXingHuoChatOptions options, RetryTemplate retryTemplate) {
        super(retryTemplate, openAiApi, options);
    }
}
