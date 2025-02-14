package cn.xk.xcode.config.image;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.XcodeAiConfiguration;
import cn.xk.xcode.config.XcodeAiProperties;
import cn.xk.xcode.core.context.XcodeAiModelContext;
import cn.xk.xcode.core.enums.AiPlatformEnum;
import com.alibaba.cloud.ai.tongyi.image.TongYiImagesModel;
import com.alibaba.cloud.ai.tongyi.image.TongYiImagesOptions;
import com.alibaba.cloud.ai.tongyi.image.TongYiImagesProperties;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.utils.Constants;
import com.azure.ai.openai.OpenAIClient;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiConnectionProperties;
import org.springframework.ai.azure.openai.AzureOpenAiImageModel;
import org.springframework.ai.azure.openai.AzureOpenAiImageOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.ai.qianfan.QianFanImageModel;
import org.springframework.ai.qianfan.QianFanImageOptions;
import org.springframework.ai.qianfan.api.QianFanImageApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiApi;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.ai.zhipuai.ZhiPuAiImageModel;
import org.springframework.ai.zhipuai.ZhiPuAiImageOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiImageApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClient;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.xk.xcode.config.chat.XcodeAiChatConfiguration.DEFAULT_OPEN_API_BASE_URL;

/**
 * @Author xuk
 * @Date 2025/2/13 10:13
 * @Version 1.0.0
 * @Description XcodeAiImageConfiguration
 **/
@Configuration
@ConditionalOnBean(XcodeAiConfiguration.class)
public class XcodeAiImageConfiguration {
    private static final Integer WIDTH = 1024;
    private static final Integer HEIGHT = 1024;
    private static final Integer N = 1;
    private static final String DEFAULT_TONG_YI_IMAGE_MODEL = TongYiImagesProperties.DEFAULT_IMAGES_MODEL_NAME;

    @Resource
    private XcodeAiProperties xcodeAiProperties;

    @Resource
    private XcodeAiModelContext xcodeAiModelContext;

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public ImageSynthesis imageSynthesis() {
        return new ImageSynthesis();
    }

    @Bean
    public TongYiImagesModel tongYiImagesModel(ImageSynthesis imageSynthesis) {
        XcodeAiProperties.TongYi tongYi = xcodeAiProperties.getTongYi();
        Constants.apiKey = tongYi.getApiKey();
        XcodeAiProperties.TongYiImage tongYiImage = tongYi.getImage();
        TongYiImagesOptions tImagesOptions = TongYiImagesOptions
                .builder()
                .withModel(StrUtil.isEmpty(tongYiImage.getModel()) ? DEFAULT_TONG_YI_IMAGE_MODEL : tongYiImage.getModel())
                .withHeight(Objects.isNull(tongYiImage.getHeight()) ? HEIGHT : tongYiImage.getHeight())
                .withN(Objects.isNull(tongYiImage.getN()) ? N : tongYiImage.getN())
                .withWidth(Objects.isNull(tongYiImage.getWidth()) ? WIDTH : tongYiImage.getWidth())
                .build();
        TongYiImagesModel tongYiImagesModel = new TongYiImagesModel(imageSynthesis, tImagesOptions);
        xcodeAiModelContext.putImageModel(AiPlatformEnum.TONG_YI, tongYiImagesModel);
        return tongYiImagesModel;
    }

    @Bean
    public QianFanImageModel qianFanImageModel() {
        XcodeAiProperties.QianFan QianFan = xcodeAiProperties.getQianFan();
        String appKey = QianFan.getApiKey();
        String secretKey = QianFan.getSecretKey();
        QianFanImageApi qianFanApi = new QianFanImageApi(appKey, secretKey);
        XcodeAiProperties.QianFanImage QianFanImage = QianFan.getImage();
        QianFanImageOptions qianFanImageOptions = QianFanImageOptions
                .builder()
                .withModel(StrUtil.isEmpty(QianFanImage.getModel()) ? QianFanImageApi.DEFAULT_IMAGE_MODEL : QianFanImage.getModel())
                .withHeight(Objects.isNull(QianFanImage.getHeight()) ? HEIGHT : QianFanImage.getHeight())
                .withN(Objects.isNull(QianFanImage.getN()) ? N : QianFanImage.getN())
                .withWidth(Objects.isNull(QianFanImage.getWidth()) ? WIDTH : QianFanImage.getWidth())
                .build();
        QianFanImageModel qianFanImageModel = new QianFanImageModel(qianFanApi, qianFanImageOptions, RetryUtils.DEFAULT_RETRY_TEMPLATE);
        xcodeAiModelContext.putImageModel(AiPlatformEnum.QIAN_FAN, qianFanImageModel);
        return qianFanImageModel;
    }

    @Bean
    public ZhiPuAiImageModel zhiPuAiImageModel() {
        XcodeAiProperties.ZhiPu zhiPu = xcodeAiProperties.getZhiPu();
        String apiKey = zhiPu.getApiKey();
        String url = StrUtil.blankToDefault(zhiPu.getBaseUrl(), ZhiPuAiConnectionProperties.DEFAULT_BASE_URL);
        ZhiPuAiImageApi zhiPuAiApi = new ZhiPuAiImageApi(url, apiKey, RestClient.builder());
        XcodeAiProperties.ZhiPuImage zhiPuImage = zhiPu.getImage();
        ZhiPuAiImageOptions zhiPuAiImageOptions = ZhiPuAiImageOptions
                .builder()
                .withModel(StrUtil.isEmpty(zhiPuImage.getModel()) ? ZhiPuAiImageApi.DEFAULT_IMAGE_MODEL : zhiPuImage.getModel())
                .withUser(StrUtil.isEmpty(zhiPuImage.getUser()) ? "" : zhiPuImage.getUser())
                .build();
        ZhiPuAiImageModel zhiPuAiImageModel = new ZhiPuAiImageModel(zhiPuAiApi, zhiPuAiImageOptions, RetryUtils.DEFAULT_RETRY_TEMPLATE);
        xcodeAiModelContext.putImageModel(AiPlatformEnum.ZHI_PU, zhiPuAiImageModel);
        return zhiPuAiImageModel;
    }

    @Bean
    public OpenAiImageModel openAiImageModel() {
        XcodeAiProperties.OpenAi openAi = xcodeAiProperties.getOpenAi();
        String apiKey = openAi.getApiKey();
        String url = StrUtil.blankToDefault(openAi.getBaseUrl(), DEFAULT_OPEN_API_BASE_URL);
        OpenAiImageApi openAiImageApi = new OpenAiImageApi(url, apiKey, RestClient.builder());
        XcodeAiProperties.OpenAiImage openAiImage = openAi.getImage();
        OpenAiImageOptions openAiImageOptions = OpenAiImageOptions
                .builder()
                .withModel(StrUtil.isEmpty(openAiImage.getModel()) ? OpenAiImageApi.DEFAULT_IMAGE_MODEL : openAiImage.getModel())
                .withHeight(Objects.isNull(openAiImage.getHeight()) ? HEIGHT : openAiImage.getHeight())
                .withN(Objects.isNull(openAiImage.getN()) ? N : openAiImage.getN())
                .withWidth(Objects.isNull(openAiImage.getWidth()) ? WIDTH : openAiImage.getWidth())
                .withUser(StrUtil.isEmpty(openAiImage.getUser()) ? "" : openAiImage.getUser())
                .build();
        OpenAiImageModel openAiImageModel = new OpenAiImageModel(openAiImageApi, openAiImageOptions, RetryUtils.DEFAULT_RETRY_TEMPLATE);
        xcodeAiModelContext.putImageModel(AiPlatformEnum.OPENAI, openAiImageModel);
        return openAiImageModel;
    }

    @Bean
    public AzureOpenAiImageModel azureOpenAiImageModel(OpenAIClient openAIClient) {
        XcodeAiProperties.AzureOpenAiImage azureOpenAiImage = xcodeAiProperties.getAzureOpenAi().getImage();
        AzureOpenAiImageOptions azureOpenAiImageOptions = AzureOpenAiImageOptions
                .builder()
                .withModel(StrUtil.isEmpty(azureOpenAiImage.getModel()) ? AzureOpenAiImageOptions.DEFAULT_IMAGE_MODEL : azureOpenAiImage.getModel())
                .withHeight(Objects.isNull(azureOpenAiImage.getHeight()) ? HEIGHT : azureOpenAiImage.getHeight())
                .withN(Objects.isNull(azureOpenAiImage.getN()) ? N : azureOpenAiImage.getN())
                .withWidth(Objects.isNull(azureOpenAiImage.getWidth()) ? WIDTH : azureOpenAiImage.getWidth())
                .withUser(StrUtil.isEmpty(azureOpenAiImage.getUser()) ? "" : azureOpenAiImage.getUser())
                .build();
        AzureOpenAiImageModel azureOpenAiImageModel = new AzureOpenAiImageModel(openAIClient, azureOpenAiImageOptions);
        xcodeAiModelContext.putImageModel(AiPlatformEnum.AZURE_OPENAI, azureOpenAiImageModel);
        return azureOpenAiImageModel;
    }

    @Bean
    public StabilityAiImageModel stabilityAiImageModel() {
        XcodeAiProperties.Stability stability = xcodeAiProperties.getStability();
        XcodeAiProperties.StabilityImage stabilityImage = stability.getImage();
        String url = StrUtil.blankToDefault(stability.getBaseUrl(), StabilityAiApi.DEFAULT_BASE_URL);
        StabilityAiApi stabilityAiApi = new StabilityAiApi(stability.getApiKey(), StrUtil.isEmpty(stabilityImage.getModel()) ? StabilityAiApi.DEFAULT_IMAGE_MODEL : stabilityImage.getModel(), url);
        StabilityAiImageOptions stabilityAiImageOptions = StabilityAiImageOptions
                .builder()
                .withN(Objects.isNull(stabilityImage.getN()) ? N : stabilityImage.getN())
                .withWidth(Objects.isNull(stabilityImage.getWidth()) ? WIDTH : stabilityImage.getWidth())
                .withHeight(Objects.isNull(stabilityImage.getHeight()) ? HEIGHT : stabilityImage.getHeight())
                .build();
        StabilityAiImageModel stabilityAiImageModel = new StabilityAiImageModel(stabilityAiApi, stabilityAiImageOptions);
        xcodeAiModelContext.putImageModel(AiPlatformEnum.STABILITY, stabilityAiImageModel);
        return stabilityAiImageModel;
    }
}
