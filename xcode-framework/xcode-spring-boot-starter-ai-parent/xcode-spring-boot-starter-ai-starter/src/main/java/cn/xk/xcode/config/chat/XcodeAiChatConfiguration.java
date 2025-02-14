package cn.xk.xcode.config.chat;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.XcodeAiConfiguration;
import cn.xk.xcode.config.XcodeAiProperties;
import cn.xk.xcode.core.context.XcodeAiModelContext;
import cn.xk.xcode.core.enums.AiPlatformEnum;
import cn.xk.xcode.core.extra.model.XcodeDeepSeekChatModel;
import cn.xk.xcode.core.extra.model.XcodeXingHuoChatModel;
import cn.xk.xcode.core.extra.options.XcodeDeepSeekChatOptions;
import cn.xk.xcode.core.extra.options.XcodeXingHuoChatOptions;
import com.alibaba.cloud.ai.tongyi.chat.TongYiChatModel;
import com.alibaba.cloud.ai.tongyi.chat.TongYiChatOptions;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.utils.Constants;
import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.ClientOptions;
import org.springframework.ai.autoconfigure.azure.openai.AzureOpenAiConnectionProperties;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.qianfan.QianFanChatModel;
import org.springframework.ai.qianfan.QianFanChatOptions;
import org.springframework.ai.qianfan.api.QianFanApi;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;

import java.util.Objects;

import static com.alibaba.cloud.ai.tongyi.chat.TongYiChatProperties.DEFAULT_DEPLOYMENT_NAME;

/**
 * @Author xuk
 * @Date 2025/2/12 17:07
 * @Version 1.0.0
 * @Description XcodeAiConfiguration
 **/
@Configuration
@ConditionalOnBean(XcodeAiConfiguration.class)
public class XcodeAiChatConfiguration {

    @Resource
    private XcodeAiProperties xcodeAiProperties;

    @Resource
    private XcodeAiModelContext xcodeAiModelContext;

    private static final Float DEFAULT_TEMPERATURE = 0.8f;
    private static final Integer DEFAULT_MAX_TOKENS = 1024;
    private static final Integer DEFAULT_TOP_K = 10;
    private static final Float DEFAULT_TOP_P = 0.8f;
    private static final String DEFAULT_QIAN_FAN_BASE_URL = "https://aip.baidubce.com/rpc/2.0/ai_custom";
    public static final String DEFAULT_OPEN_API_BASE_URL = "https://api.openai.com";
    public static final String DEFAULT_OLLAMA_BASE_URL = "http://localhost:11434";
    public static final String DEFAULT_AZURE_OPEN_AI_MODEL = "gpt-35-turbo";

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Generation generation() {
        return new Generation();
    }

    @Bean
    public TongYiChatModel tongYiChatModel(Generation generation) {
        XcodeAiProperties.TongYi tongYi = xcodeAiProperties.getTongYi();
        Constants.apiKey = tongYi.getApiKey();
        XcodeAiProperties.TongYiChat tongYiChat = tongYi.getChat();
        TongYiChatOptions tongYiChatOptions = TongYiChatOptions.builder()
                .withModel(StrUtil.isEmpty(tongYiChat.getModel()) ? DEFAULT_DEPLOYMENT_NAME : tongYiChat.getModel())
                .withTemperature(Double.valueOf(Objects.isNull(tongYiChat.getTemperature()) ? DEFAULT_TEMPERATURE : tongYiChat.getTemperature()))
                .withEnableSearch(true)
                .withResultFormat(GenerationParam.ResultFormat.MESSAGE)
                .withTopK(Objects.isNull(tongYiChat.getTopK()) ? DEFAULT_TOP_K : tongYiChat.getTopK())
                .withTopP(Double.valueOf(Objects.isNull(tongYiChat.getTopP()) ? DEFAULT_TOP_P : tongYiChat.getTopP()))
                .withMaxTokens(Objects.isNull(tongYiChat.getMaxTokens()) ? DEFAULT_MAX_TOKENS : tongYiChat.getMaxTokens())
                .build();
        TongYiChatModel tongYiChatModel = new TongYiChatModel(generation, tongYiChatOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.TONG_YI, tongYiChatModel);
        return tongYiChatModel;
    }

    @Bean
    public QianFanChatModel qianFanChatModel() {
        XcodeAiProperties.QianFan QianFan = xcodeAiProperties.getQianFan();
        String apiKey = QianFan.getApiKey();
        String secretKey = QianFan.getSecretKey();
        XcodeAiProperties.QianFanChat QianFanChat = QianFan.getChat();
        QianFanApi qianFanApi = new QianFanApi(StrUtil.isEmpty(QianFan.getBaseUrl()) ? DEFAULT_QIAN_FAN_BASE_URL : QianFan.getBaseUrl(), apiKey, secretKey);
        QianFanChatOptions qianFanChatOptions = QianFanChatOptions.builder()
                .withModel(StrUtil.isEmpty(QianFanChat.getModel()) ? QianFanApi.DEFAULT_CHAT_MODEL : QianFanChat.getModel())
                .withTemperature(Objects.isNull(QianFanChat.getTemperature()) ? DEFAULT_TEMPERATURE : QianFanChat.getTemperature())
                .withTopP(Objects.isNull(QianFanChat.getTopP()) ? DEFAULT_TOP_P : QianFanChat.getTopP())
                .withMaxTokens(Objects.isNull(QianFanChat.getMaxTokens()) ? DEFAULT_MAX_TOKENS : QianFanChat.getMaxTokens())
                .build();
        QianFanChatModel qianFanChatModel = new QianFanChatModel(qianFanApi, qianFanChatOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.QIAN_FAN, qianFanChatModel);
        return qianFanChatModel;
    }

    @Bean
    public ZhiPuAiChatModel zhiPuAiChatModel() {
        XcodeAiProperties.ZhiPu zhiPu = xcodeAiProperties.getZhiPu();
        String url = StrUtil.blankToDefault(zhiPu.getBaseUrl(), ZhiPuAiConnectionProperties.DEFAULT_BASE_URL);
        ZhiPuAiApi zhiPuAiApi = new ZhiPuAiApi(url, zhiPu.getApiKey());
        XcodeAiProperties.ZhiPuChat zhiPuChat = zhiPu.getChat();
        ZhiPuAiChatOptions zhiPuAiChatOptions = ZhiPuAiChatOptions
                .builder()
                .withModel(StrUtil.isEmpty(zhiPuChat.getModel()) ? QianFanApi.DEFAULT_CHAT_MODEL : zhiPuChat.getModel())
                .withTemperature(Objects.isNull(zhiPuChat.getTemperature()) ? DEFAULT_TEMPERATURE : zhiPuChat.getTemperature())
                .withTopP(Objects.isNull(zhiPuChat.getTopP()) ? DEFAULT_TOP_P : zhiPuChat.getTopP())
                .withMaxTokens(Objects.isNull(zhiPuChat.getMaxTokens()) ? DEFAULT_MAX_TOKENS : zhiPuChat.getMaxTokens())
                .build();
        ZhiPuAiChatModel zhiPuAiChatModel = new ZhiPuAiChatModel(zhiPuAiApi, zhiPuAiChatOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.ZHI_PU, zhiPuAiChatModel);
        return zhiPuAiChatModel;
    }

    @Bean
    public OpenAiChatModel openAiChatModel() {
        XcodeAiProperties.OpenAi openAi = xcodeAiProperties.getOpenAi();
        String apiKey = openAi.getApiKey();
        String url = StrUtil.blankToDefault(openAi.getBaseUrl(), DEFAULT_OPEN_API_BASE_URL);
        OpenAiApi openAiApi = new OpenAiApi(url, apiKey);
        XcodeAiProperties.OpenAiChat openAiChat = openAi.getChat();
        OpenAiChatOptions openAiChatOptions = OpenAiChatOptions
                .builder()
                .withModel(StrUtil.isEmpty(openAiChat.getModel()) ? OpenAiApi.DEFAULT_CHAT_MODEL : openAiChat.getModel())
                .withTemperature(Objects.isNull(openAiChat.getTemperature()) ? DEFAULT_TEMPERATURE : openAiChat.getTemperature())
                .withTopP(Objects.isNull(openAiChat.getTopP()) ? DEFAULT_TOP_P : openAiChat.getTopP())
                .withMaxTokens(Objects.isNull(openAiChat.getMaxTokens()) ? DEFAULT_MAX_TOKENS : openAiChat.getMaxTokens())
                .build();
        OpenAiChatModel openAiChatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.OPENAI, openAiChatModel);
        return openAiChatModel;
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAIClient openAIClient() {
        AzureOpenAiConnectionProperties azureOpenAiConnectionProperties = new AzureOpenAiConnectionProperties();
        XcodeAiProperties.AzureOpenAi azureOpenAi = xcodeAiProperties.getAzureOpenAi();
        azureOpenAiConnectionProperties.setEndpoint(azureOpenAi.getBaseUrl());
        azureOpenAiConnectionProperties.setApiKey(azureOpenAi.getApiKey());
        return (new OpenAIClientBuilder()).endpoint(azureOpenAiConnectionProperties.getEndpoint()).credential(new AzureKeyCredential(azureOpenAiConnectionProperties.getApiKey())).clientOptions((new ClientOptions()).setApplicationId("spring-ai")).buildClient();
    }

    @Bean
    public AzureOpenAiChatModel azureOpenAiChatModel(OpenAIClient openAIClient) {
        XcodeAiProperties.AzureOpenAiChat azureOpenAiChat = xcodeAiProperties.getAzureOpenAi().getChat();
        AzureOpenAiChatOptions azureOpenAiChatOptions = AzureOpenAiChatOptions
                .builder()
                .withDeploymentName(StrUtil.isEmpty(azureOpenAiChat.getModel()) ? DEFAULT_AZURE_OPEN_AI_MODEL : azureOpenAiChat.getModel())
                .withTemperature(Objects.isNull(azureOpenAiChat.getTemperature()) ? DEFAULT_TEMPERATURE : azureOpenAiChat.getTemperature())
                .withTopP(Objects.isNull(azureOpenAiChat.getTopP()) ? DEFAULT_TOP_P : azureOpenAiChat.getTopP())
                .withMaxTokens(Objects.isNull(azureOpenAiChat.getMaxTokens()) ? DEFAULT_MAX_TOKENS : azureOpenAiChat.getMaxTokens())
                .build();
        AzureOpenAiChatModel azureOpenAiChatModel = new AzureOpenAiChatModel(openAIClient, azureOpenAiChatOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.AZURE_OPENAI, azureOpenAiChatModel);
        return azureOpenAiChatModel;
    }

    @Bean
    public OllamaChatModel ollamaChatModel() {
        XcodeAiProperties.Ollama ollama = xcodeAiProperties.getOllama();
        XcodeAiProperties.OllamaChat ollamaChat = ollama.getChat();
        OllamaApi ollamaApi = new OllamaApi(StrUtil.isEmpty(ollama.getBaseUrl()) ? DEFAULT_OLLAMA_BASE_URL : ollama.getBaseUrl());
        OllamaOptions ollamaOptions = OllamaOptions.create();
        ollamaOptions.setModel(StrUtil.isEmpty(ollamaChat.getModel()) ? OllamaOptions.DEFAULT_MODEL : ollamaChat.getModel());
        ollamaOptions.setTemperature(Objects.isNull(ollamaChat.getTemperature()) ? DEFAULT_TEMPERATURE : ollamaChat.getTemperature());
        ollamaOptions.setTopK(Objects.isNull(ollamaChat.getTopK()) ? DEFAULT_TOP_K : ollamaChat.getTopK());
        ollamaOptions.setTopP(Objects.isNull(ollamaChat.getTopP()) ? DEFAULT_TOP_P : ollamaChat.getTopP());
        OllamaChatModel ollamaChatModel = new OllamaChatModel(ollamaApi, ollamaOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.OLLAMA, ollamaChatModel);
        return ollamaChatModel;
    }

    @Bean
    public XcodeDeepSeekChatModel xcodeDeepSeekChatModel(){
        XcodeAiProperties.DeepSeek deepSeek = xcodeAiProperties.getDeepSeek();
        String apiKey = deepSeek.getApiKey();
        String url = StrUtil.blankToDefault(deepSeek.getBaseUrl(), XcodeDeepSeekChatModel.BASE_URL);
        OpenAiApi openAiApi = new OpenAiApi(url, apiKey);
        XcodeAiProperties.OpenAiChat openAiChat = deepSeek.getChat();
        XcodeDeepSeekChatOptions openAiChatOptions = XcodeDeepSeekChatOptions
                .builder()
                .withModel(StrUtil.isEmpty(openAiChat.getModel()) ? XcodeDeepSeekChatOptions.MODEL_DEFAULT : openAiChat.getModel())
                .withTemperature(Objects.isNull(openAiChat.getTemperature()) ? DEFAULT_TEMPERATURE : openAiChat.getTemperature())
                .withTopP(Objects.isNull(openAiChat.getTopP()) ? DEFAULT_TOP_P : openAiChat.getTopP())
                .withMaxTokens(Objects.isNull(openAiChat.getMaxTokens()) ? DEFAULT_MAX_TOKENS : openAiChat.getMaxTokens())
                .build();
        XcodeDeepSeekChatModel xcodeDeepSeekChatModel = new XcodeDeepSeekChatModel(openAiApi, openAiChatOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.DEEP_SEEK, xcodeDeepSeekChatModel);
        return xcodeDeepSeekChatModel;
    }

    @Bean
    public XcodeXingHuoChatModel xcodeXingHuoChatModel(){
        XcodeAiProperties.XingHuo xingHuo = xcodeAiProperties.getXingHuo();
        String apiKey = xingHuo.getApiKey();
        String url = StrUtil.blankToDefault(xingHuo.getBaseUrl(), XcodeXingHuoChatModel.BASE_URL);
        OpenAiApi openAiApi = new OpenAiApi(url, apiKey);
        XcodeAiProperties.OpenAiChat openAiChat = xingHuo.getChat();
        XcodeXingHuoChatOptions openAiChatOptions = XcodeXingHuoChatOptions
                .builder()
                .withModel(StrUtil.isEmpty(openAiChat.getModel()) ? XcodeXingHuoChatOptions.MODEL_DEFAULT : openAiChat.getModel())
                .withTemperature(Objects.isNull(openAiChat.getTemperature()) ? DEFAULT_TEMPERATURE : openAiChat.getTemperature())
                .withTopP(Objects.isNull(openAiChat.getTopP()) ? DEFAULT_TOP_P : openAiChat.getTopP())
                .withMaxTokens(Objects.isNull(openAiChat.getMaxTokens()) ? DEFAULT_MAX_TOKENS : openAiChat.getMaxTokens())
                .build();
        XcodeXingHuoChatModel xcodeXingHuoChatModel = new XcodeXingHuoChatModel(openAiApi, openAiChatOptions);
        xcodeAiModelContext.putChatModel(AiPlatformEnum.XING_HUO, xcodeXingHuoChatModel);
        return xcodeXingHuoChatModel;
    }
}
