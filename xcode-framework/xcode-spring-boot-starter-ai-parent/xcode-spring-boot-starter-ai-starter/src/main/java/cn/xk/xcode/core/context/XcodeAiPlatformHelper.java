package cn.xk.xcode.core.context;

import cn.xk.xcode.core.enums.AiFeatureEnum;
import cn.xk.xcode.core.enums.AiPlatformEnum;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.LinkedList;

/**
 * @Author xuk
 * @Date 2025/2/14 13:40
 * @Version 1.0.0
 * @Description XcodeAiTypeHolder
 **/
public class XcodeAiPlatformHelper {

    private XcodeAiPlatformHelper() {
    }

    private static final ThreadLocal<LinkedList<AiPlatformEnum>> AI_PLATFORM_CHAT = TransmittableThreadLocal.withInitial(LinkedList::new);
    private static final ThreadLocal<LinkedList<AiPlatformEnum>> AI_PLATFORM_IMAGE = TransmittableThreadLocal.withInitial(LinkedList::new);
    private static final ThreadLocal<LinkedList<AiPlatformEnum>> AI_PLATFORM_EMBEDDING = TransmittableThreadLocal.withInitial(LinkedList::new);

    public static void add(AiPlatformEnum platform, AiFeatureEnum feature) {
        switch (feature) {
            case CHAT:
                AI_PLATFORM_CHAT.get().addLast(platform);
                break;
            case IMAGE:
                AI_PLATFORM_IMAGE.get().addLast(platform);
                break;
            case EMBEDDING:
                AI_PLATFORM_EMBEDDING.get().addLast(platform);
        }
    }

    public static AiPlatformEnum get(AiFeatureEnum feature) {
        return switch (feature) {
            case CHAT -> AI_PLATFORM_CHAT.get().peekLast();
            case IMAGE -> AI_PLATFORM_IMAGE.get().peekLast();
            case EMBEDDING -> AI_PLATFORM_EMBEDDING.get().peekLast();
        };
    }

    public static void remove() {
        LinkedList<AiPlatformEnum> linkedList = AI_PLATFORM_CHAT.get();
        if (!linkedList.isEmpty()) {
            linkedList.removeLast();
            if (linkedList.isEmpty()) {
                AI_PLATFORM_CHAT.remove();
            }
        }else {
            AI_PLATFORM_CHAT.remove();
        }
        linkedList = AI_PLATFORM_IMAGE.get();
        if (!linkedList.isEmpty()) {
            linkedList.removeLast();
            if (linkedList.isEmpty()) {
                AI_PLATFORM_IMAGE.remove();
            }
        }else {
            AI_PLATFORM_IMAGE.remove();
        }
        linkedList = AI_PLATFORM_EMBEDDING.get();
        if (!linkedList.isEmpty()) {
            linkedList.removeLast();
            if (linkedList.isEmpty()) {
                AI_PLATFORM_EMBEDDING.remove();
            }
        }else {
            AI_PLATFORM_EMBEDDING.remove();
        }
    }


    public static void useAiChatPlatform(AiPlatformEnum platform) {
        LinkedList<AiPlatformEnum> linkedList = AI_PLATFORM_CHAT.get();
        if (linkedList.isEmpty()) {
            add(platform, AiFeatureEnum.CHAT);
        } else {
            linkedList.removeLast();
            linkedList.addLast(platform);
        }
    }

    public static void useAiImagePlatform(AiPlatformEnum platform) {
        LinkedList<AiPlatformEnum> linkedList = AI_PLATFORM_IMAGE.get();
        if (linkedList.isEmpty()) {
            add(platform, AiFeatureEnum.IMAGE);
        } else {
            linkedList.removeLast();
            linkedList.addLast(platform);
        }
    }

    public static void useAiEmbeddingPlatform(AiPlatformEnum platform) {
        LinkedList<AiPlatformEnum> linkedList = AI_PLATFORM_EMBEDDING.get();
        if (linkedList.isEmpty()) {
            add(platform, AiFeatureEnum.EMBEDDING);
        } else {
            linkedList.removeLast();
            linkedList.addLast(platform);
        }
    }

    public static void useAllAiPlatform(AiPlatformEnum platform) {
        useAiChatPlatform(platform);
        useAiImagePlatform(platform);
        useAiEmbeddingPlatform(platform);
    }
}
