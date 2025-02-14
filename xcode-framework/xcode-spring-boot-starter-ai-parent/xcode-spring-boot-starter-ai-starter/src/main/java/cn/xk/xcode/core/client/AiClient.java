package cn.xk.xcode.core.client;

import cn.xk.xcode.core.context.XcodeAiModelContext;
import cn.xk.xcode.core.context.XcodeAiPlatformHelper;
import cn.xk.xcode.core.enums.AiFeatureEnum;
import cn.xk.xcode.core.enums.AiPlatformEnum;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.image.ImageMessage;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2025/2/14 15:46
 * @Version 1.0.0
 * @Description AiClient
 **/
@RequiredArgsConstructor
public class AiClient {

    private final XcodeAiModelContext xcodeAiModelContext;

    public ChatResponse doChatCall(String platform, List<String> messages) {
        AiPlatformEnum platformEnum = XcodeAiPlatformHelper.get(AiFeatureEnum.CHAT);
        if (Objects.nonNull(platformEnum)) {
            platform = platformEnum.getName();
        }
        ChatModel chatModel = xcodeAiModelContext.getChatModel(platform);
        return chatModel.call(new Prompt(messages.stream().map(UserMessage::new).collect(Collectors.toList())));
    }

    public ChatResponse doChatCall(String platform, String message) {
        return doChatCall(platform, CollectionUtil.createSingleList(message));
    }

    public Flux<ChatResponse> doChatStream(String platform, List<String> messages) {
        AiPlatformEnum platformEnum = XcodeAiPlatformHelper.get(AiFeatureEnum.CHAT);
        if (Objects.nonNull(platformEnum)) {
            platform = platformEnum.getName();
        }
        ChatModel chatModel = xcodeAiModelContext.getChatModel(platform);
        return chatModel.stream(new Prompt(messages.stream().map(UserMessage::new).collect(Collectors.toList())));
    }

    public Flux<ChatResponse> doChatStream(String platform, String message) {
        return doChatStream(platform, CollectionUtil.createSingleList(message));
    }

    public ImageResponse doImageCall(String platform, String message) {
        return doImageCall(platform, CollectionUtil.createSingleList(message));
    }

    public ImageResponse doImageCall(String platform, List<String> messages) {
        AiPlatformEnum platformEnum = XcodeAiPlatformHelper.get(AiFeatureEnum.IMAGE);
        if (Objects.nonNull(platformEnum)) {
            platform = platformEnum.getName();
        }
        ImageModel imageModel = xcodeAiModelContext.getImageModel(platform);
        return imageModel.call(new ImagePrompt(messages.stream().map(ImageMessage::new).collect(Collectors.toList())));
    }

    public EmbeddingResponse doEmbeddingCall(String platform, String message) {
        return doEmbeddingCall(platform, CollectionUtil.createSingleList(message));
    }

    public EmbeddingResponse doEmbeddingCall(String platform, List<String> messages) {
        AiPlatformEnum platformEnum = XcodeAiPlatformHelper.get(AiFeatureEnum.EMBEDDING);
        if (Objects.nonNull(platformEnum)) {
            platform = platformEnum.getName();
        }
        EmbeddingModel embeddingModel = xcodeAiModelContext.getEmbeddingModel(platform);
        return embeddingModel.call(new EmbeddingRequest(messages, EmbeddingOptions.EMPTY));
    }

}
