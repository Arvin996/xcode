package cn.xk.xcode.config.embedding;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.XcodeAiConfiguration;
import cn.xk.xcode.config.XcodeAiProperties;
import cn.xk.xcode.core.context.XcodeAiModelContext;
import cn.xk.xcode.core.enums.AiPlatformEnum;
import com.alibaba.cloud.ai.tongyi.embedding.TongYiEmbeddingOptions;
import com.alibaba.cloud.ai.tongyi.embedding.TongYiTextEmbeddingModel;
import com.alibaba.dashscope.embeddings.TextEmbedding;
import com.alibaba.dashscope.utils.Constants;
import com.azure.ai.openai.OpenAIClient;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingModel;
import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingOptions;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.qianfan.QianFanEmbeddingModel;
import org.springframework.ai.qianfan.QianFanEmbeddingOptions;
import org.springframework.ai.qianfan.api.QianFanApi;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import javax.annotation.Resource;

import static cn.xk.xcode.config.chat.XcodeAiChatConfiguration.DEFAULT_OLLAMA_BASE_URL;

/**
 * @Author xuk
 * @Date 2025/2/13 11:08
 * @Version 1.0.0
 * @Description XcodeEmbeddingConfiguration
 **/
@Configuration
@ConditionalOnBean(XcodeAiConfiguration.class)
public class XcodeEmbeddingConfiguration {

    @Resource
    private XcodeAiProperties xcodeAiProperties;

    @Resource
    private XcodeAiModelContext xcodeAiModelContext;

    private final static String DEFAULT_AZURE_EMBEDDING_MODEL = "text-embedding-ada-002";
    @Bean
    @ConditionalOnMissingBean
    public TextEmbedding textEmbedding() {
        return new TextEmbedding();
    }

    @Bean
    public TongYiTextEmbeddingModel tongYiTextEmbeddingModel(TextEmbedding textEmbedding) {
        XcodeAiProperties.TongYi tongYi = xcodeAiProperties.getTongYi();
        XcodeAiProperties.TongYiEmbedding tongYiEmbedding = tongYi.getEmbedding();
        Constants.apiKey = tongYi.getApiKey();
        TongYiEmbeddingOptions tongYiEmbeddingOptions = TongYiEmbeddingOptions
                .builder()
                .withtextType(tongYiEmbedding.getTextType().getTextType())
                .build();
        TongYiTextEmbeddingModel tongYiTextEmbeddingModel = new TongYiTextEmbeddingModel(textEmbedding, tongYiEmbedding.getMetadataMode().getMetadataMode(), tongYiEmbeddingOptions);
        xcodeAiModelContext.putEmbeddingModel(AiPlatformEnum.TONG_YI, tongYiTextEmbeddingModel);
        return tongYiTextEmbeddingModel;
    }

    @Bean
    public QianFanEmbeddingModel qianFanEmbeddingModel() {
        XcodeAiProperties.QianFan qianFan = xcodeAiProperties.getQianFan();
        String apiKey = qianFan.getApiKey();
        String secretKey = qianFan.getSecretKey();
        QianFanApi qianFanApi = new QianFanApi(apiKey, secretKey);
        XcodeAiProperties.QianFanEmbedding embedding = qianFan.getEmbedding();
        QianFanEmbeddingOptions qianFanEmbeddingOptions = QianFanEmbeddingOptions
                .builder()
                .withModel(StrUtil.isEmpty(embedding.getModel()) ? QianFanApi.DEFAULT_EMBEDDING_MODEL : embedding.getModel())
                .withUser(StrUtil.isEmpty(embedding.getUser()) ? "" : embedding.getUser())
                .build();
        QianFanEmbeddingModel qianFanEmbeddingModel = new QianFanEmbeddingModel(qianFanApi, embedding.getMetadataMode().getMetadataMode(), qianFanEmbeddingOptions);
        xcodeAiModelContext.putEmbeddingModel(AiPlatformEnum.QIAN_FAN, qianFanEmbeddingModel);
        return qianFanEmbeddingModel;
    }

    @Bean
    public ZhiPuAiEmbeddingModel zhiPuAiEmbeddingModel() {
        XcodeAiProperties.ZhiPu zhiPu = xcodeAiProperties.getZhiPu();
        String apiKey = zhiPu.getApiKey();
        String url = StrUtil.blankToDefault(zhiPu.getBaseUrl(), ZhiPuAiConnectionProperties.DEFAULT_BASE_URL);
        ZhiPuAiApi zhiPuAiApi = new ZhiPuAiApi(url, apiKey, RestClient.builder());
        XcodeAiProperties.ZhiPuEmbedding embedding = zhiPu.getEmbedding();
        ZhiPuAiEmbeddingOptions zhiPuAiEmbeddingOptions = ZhiPuAiEmbeddingOptions
                .builder()
                .withModel(StrUtil.isEmpty(embedding.getModel()) ? ZhiPuAiApi.DEFAULT_EMBEDDING_MODEL : embedding.getModel())
                .build();
        ZhiPuAiEmbeddingModel zhiPuAiEmbeddingModel = new ZhiPuAiEmbeddingModel(zhiPuAiApi, embedding.getMetadataMode().getMetadataMode(), zhiPuAiEmbeddingOptions);
        xcodeAiModelContext.putEmbeddingModel(AiPlatformEnum.ZHI_PU, zhiPuAiEmbeddingModel);
        return zhiPuAiEmbeddingModel;
    }

    @Bean
    public OpenAiEmbeddingModel openAiEmbeddingModel(){
        XcodeAiProperties.OpenAi openAi = xcodeAiProperties.getOpenAi();
        String apiKey = openAi.getApiKey();
        String url = StrUtil.blankToDefault(openAi.getBaseUrl(), ZhiPuAiConnectionProperties.DEFAULT_BASE_URL);
        OpenAiApi openAiApi = new OpenAiApi(url, apiKey);
        XcodeAiProperties.OpenAiEmbedding embedding = openAi.getEmbedding();
        OpenAiEmbeddingOptions openAiEmbeddingOptions = OpenAiEmbeddingOptions
                .builder()
                .withModel(StrUtil.isEmpty(embedding.getModel()) ? OpenAiApi.DEFAULT_EMBEDDING_MODEL : embedding.getModel())
                .withUser(StrUtil.isEmpty(embedding.getUser()) ? "" : embedding.getUser())
                .build();
        OpenAiEmbeddingModel openAiEmbeddingModel = new OpenAiEmbeddingModel(openAiApi, embedding.getMetadataMode().getMetadataMode(), openAiEmbeddingOptions);
        xcodeAiModelContext.putEmbeddingModel(AiPlatformEnum.OPENAI, openAiEmbeddingModel);
        return openAiEmbeddingModel;
    }

    @Bean
    public AzureOpenAiEmbeddingModel azureOpenAiEmbeddingModel(OpenAIClient openAIClient){
        XcodeAiProperties.AzureOpenAiEmbedding azureOpenAiEmbedding = xcodeAiProperties.getAzureOpenAi().getEmbedding();
        AzureOpenAiEmbeddingOptions azureOpenAiEmbeddingOptions = AzureOpenAiEmbeddingOptions
                .builder()
                .withDeploymentName(StrUtil.isEmpty(azureOpenAiEmbedding.getModel()) ? DEFAULT_AZURE_EMBEDDING_MODEL : azureOpenAiEmbedding.getModel())
                .withUser(StrUtil.isEmpty(azureOpenAiEmbedding.getUser()) ? "" : azureOpenAiEmbedding.getUser())
                .build();
        AzureOpenAiEmbeddingModel azureOpenAiEmbeddingModel = new AzureOpenAiEmbeddingModel(openAIClient, azureOpenAiEmbedding.getMetadataMode().getMetadataMode(), azureOpenAiEmbeddingOptions);
        xcodeAiModelContext.putEmbeddingModel(AiPlatformEnum.AZURE_OPENAI, azureOpenAiEmbeddingModel);
        return azureOpenAiEmbeddingModel;
    }

    @Bean
    public OllamaEmbeddingModel ollamaEmbeddingModel(){
        XcodeAiProperties.Ollama ollama = xcodeAiProperties.getOllama();
        XcodeAiProperties.OllamaImage ollamaImage = ollama.getImage();
        OllamaApi ollamaApi = new OllamaApi(StrUtil.isEmpty(ollama.getBaseUrl()) ? DEFAULT_OLLAMA_BASE_URL : ollama.getBaseUrl());
        OllamaOptions ollamaOptions = new OllamaOptions();
        ollamaOptions.setModel(StrUtil.isEmpty(ollamaImage.getModel()) ? OllamaOptions.DEFAULT_MODEL : ollamaImage.getModel());
        OllamaEmbeddingModel ollamaEmbeddingModel = new OllamaEmbeddingModel(ollamaApi, ollamaOptions);
        xcodeAiModelContext.putEmbeddingModel(AiPlatformEnum.OLLAMA, ollamaEmbeddingModel);
        return ollamaEmbeddingModel;
    }
}
