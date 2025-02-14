package cn.xk.xcode.core.context;

import cn.xk.xcode.core.enums.AiPlatformEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;

import java.util.Map;
import java.util.Objects;

import static cn.xk.xcode.core.enums.XcodeAiConstants.*;

/**
 * @Author xuk
 * @Date 2025/2/13 17:29
 * @Version 1.0.0
 * @Description XcodeAiModelContext
 **/
@RequiredArgsConstructor
public class XcodeAiModelContext {

    private final Map<AiPlatformEnum, ChatModel> chatModelMap;
    private final Map<AiPlatformEnum, ImageModel> imageModelMap;
    private final Map<AiPlatformEnum, EmbeddingModel> embeddingModelMap;

    public ChatModel getChatModel(String platform){
        AiPlatformEnum platformEnum = AiPlatformEnum.getByName(platform);
        if (Objects.isNull(platformEnum)){
            ExceptionUtil.castServiceException(AI_PLATFORM_NOT_FOUND, platform);
        }
        ChatModel chatModel = getChatModel(platformEnum);
        if (Objects.isNull(chatModel)){
            ExceptionUtil.castServiceException(AI_PLATFORM_NOT_CONFIG_OR_NOT_SUPPORT, platform, "chat");
        }
        return chatModel;
    }

    public ImageModel getImageModel(String platform){
        AiPlatformEnum platformEnum = AiPlatformEnum.getByName(platform);
        if (Objects.isNull(platformEnum)){
            ExceptionUtil.castServiceException(AI_PLATFORM_NOT_FOUND, platform);
        }
        ImageModel imageModel = getImageModel(platformEnum);
        if (Objects.isNull(imageModel)){
            ExceptionUtil.castServiceException(AI_PLATFORM_NOT_CONFIG_OR_NOT_SUPPORT, platform, "image");
        }
        return imageModel;
    }

    public EmbeddingModel getEmbeddingModel(String platform){
        AiPlatformEnum platformEnum = AiPlatformEnum.getByName(platform);
        if (Objects.isNull(platformEnum)){
            ExceptionUtil.castServiceException(AI_PLATFORM_NOT_FOUND, platform);
        }
        EmbeddingModel embeddingModel = getEmbeddingModel(platformEnum);
        if (Objects.isNull(embeddingModel)){
            ExceptionUtil.castServiceException(AI_PLATFORM_NOT_CONFIG_OR_NOT_SUPPORT, platform, "embedding");
        }
        return embeddingModel;
    }

    private ChatModel getChatModel(AiPlatformEnum platform){
        return chatModelMap.get(platform);
    }

    private ImageModel getImageModel(AiPlatformEnum platform){
        return imageModelMap.get(platform);
    }

    private EmbeddingModel getEmbeddingModel(AiPlatformEnum platform){
        return embeddingModelMap.get(platform);
    }

    public void putChatModel(AiPlatformEnum platform, ChatModel chatModel){
        chatModelMap.put(platform, chatModel);
    }

    public void putImageModel(AiPlatformEnum platform, ImageModel imageModel){
        imageModelMap.put(platform, imageModel);
    }

    public void putEmbeddingModel(AiPlatformEnum platform, EmbeddingModel embeddingModel){
        embeddingModelMap.put(platform, embeddingModel);
    }
}
