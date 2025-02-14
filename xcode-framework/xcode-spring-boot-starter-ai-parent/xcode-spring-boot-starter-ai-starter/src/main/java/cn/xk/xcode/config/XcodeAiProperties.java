package cn.xk.xcode.config;

import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.ai.document.MetadataMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/2/13 10:31
 * @Version 1.0.0
 * @Description XcodeAiProperties
 **/
@Data
@ConfigurationProperties("xcode.ai")
public class XcodeAiProperties {

    private TongYi tongYi;
    private QianFan QianFan;
    private ZhiPu zhiPu;
    private OpenAi openAi;
    private AzureOpenAi azureOpenAi;
    private Ollama ollama;
    private Stability stability;
    private DeepSeek deepSeek;
    private XingHuo xingHuo;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TongYi extends Base {
        private TongYiChat chat;
        private TongYiImage image;
        private TongYiEmbedding embedding;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TongYiEmbedding extends BaseEmbedding {
        private TongYiTextTypeEnum textType = TongYiTextTypeEnum.DOCUMENT;
        private MetadataModeEnum metadataMode = MetadataModeEnum.EMBED;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TongYiChat extends BaseChat {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TongYiImage extends BaseImage {
        private Integer width;
        private Integer height;
        private Integer n;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class QianFan extends Base {
        private String secretKey;
        private QianFanChat chat;
        private QianFanImage image;
        private QianFanEmbedding embedding;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class QianFanChat extends BaseChat {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class QianFanImage extends BaseImage {
        private Integer width;
        private Integer height;
        private Integer n;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class QianFanEmbedding extends BaseEmbedding {
        private MetadataModeEnum metadataMode = MetadataModeEnum.EMBED;
        private String model;
        private String user;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ZhiPu extends Base {
        private ZhiPuChat chat;
        private ZhiPuImage image;
        private ZhiPuEmbedding embedding;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ZhiPuChat extends BaseChat {
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ZhiPuImage extends BaseImage {
        private String user;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ZhiPuEmbedding extends BaseEmbedding {
        private MetadataModeEnum metadataMode = MetadataModeEnum.EMBED;
        private String model;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OpenAi extends Base {
        private OpenAiChat chat;
        private OpenAiImage image;
        private OpenAiEmbedding embedding;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OpenAiChat extends BaseChat {
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OpenAiImage extends BaseImage {
        private String user;
        private Integer width;
        private Integer height;
        private Integer n;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OpenAiEmbedding extends BaseEmbedding {
        private MetadataModeEnum metadataMode = MetadataModeEnum.EMBED;
        private String model;
        private String user;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class AzureOpenAi extends Base {
        private AzureOpenAiChat chat;
        private AzureOpenAiImage image;
        private AzureOpenAiEmbedding embedding;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class AzureOpenAiChat extends BaseChat {
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class AzureOpenAiImage extends BaseImage {
        private String user;
        private Integer width;
        private Integer height;
        private Integer n;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class AzureOpenAiEmbedding extends BaseEmbedding {
        private MetadataModeEnum metadataMode = MetadataModeEnum.EMBED;
        private String model;
        private String user;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Ollama extends Base {
        private OllamaChat chat;
        private OllamaImage image;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OllamaChat extends BaseChat {
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class OllamaImage extends BaseImage {

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class Stability extends Base {
        private StabilityImage image;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class StabilityImage extends BaseImage {
        private Integer width;
        private Integer height;
        private Integer n;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DeepSeek extends Base{
        private OpenAiChat chat;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class XingHuo extends Base{
        private OpenAiChat chat;
    }

    @Data
    public static class Base {
        private String apiKey;
        private String baseUrl;
    }

    @Data
    public static class BaseChat {
        private String model;
        private Float temperature;
        private Integer maxTokens;
        private Integer topK;
        private Float topP;
    }

    @Data
    public static class BaseImage {
        private String model;
    }

    @Data
    public static class BaseEmbedding {

    }

    @AllArgsConstructor
    @Getter
    public enum MetadataModeEnum {
        ALL(MetadataMode.ALL, "all"),
        EMBED(MetadataMode.EMBED, "embed"),
        INFERENCE(MetadataMode.INFERENCE, "inference"),
        NONE(MetadataMode.NONE, "none");
        private final MetadataMode metadataMode;
        private final String mode;
    }

    @AllArgsConstructor
    @Getter
    public enum TongYiTextTypeEnum {
        QUERY(TextEmbeddingParam.TextType.QUERY, "query"),
        DOCUMENT(TextEmbeddingParam.TextType.DOCUMENT, "document");
        private final TextEmbeddingParam.TextType textType;
        private final String type;
    }

}
