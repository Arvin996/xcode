package cn.xk.xcode.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2025/2/13 10:02
 * @Version 1.0.0
 * @Description AiPlatformEnum
 **/
@Getter
@AllArgsConstructor
public enum AiPlatformEnum {

    TONG_YI("TongYi", "通义千问"),
    QIAN_FAN("QianFan", "文心一言"),
    OPENAI("OpenAI", "OpenAI"),
    AZURE_OPENAI("AzureOpenAI", "Azure OpenAI"),
    OLLAMA("Ollama", "Ollama"),
    STABILITY("Stability", "Stability"),
    DEEP_SEEK("DeepSeek", "DeepSeek"),
    XING_HUO("XingHuo", "XingHuo"),
    ZHI_PU("ZhiPu", "智谱"),
    NULL("Null", "aop注解内的默认值");
    private final String name;
    private final String desc;

    public static AiPlatformEnum getByName(String name){
        return Arrays.stream(AiPlatformEnum.values()).filter(aiPlatformEnum -> aiPlatformEnum.getName().equals(name)).findFirst().orElse(null);
    }
}
