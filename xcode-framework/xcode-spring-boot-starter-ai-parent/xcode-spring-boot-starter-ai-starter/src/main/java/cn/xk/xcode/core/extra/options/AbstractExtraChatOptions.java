package cn.xk.xcode.core.extra.options;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.chat.prompt.ChatOptions;

/**
 * @Author xuk
 * @Date 2025/2/14 9:46
 * @Version 1.0.0
 * @Description AbstractExtraChatOptions
 **/
@Setter
public abstract class AbstractExtraChatOptions implements ChatOptions {

    /**
     * 模型
     */
    @Getter
    private String model;
    /**
     * 温度
     */
    private Float temperature;
    /**
     * 最大 Token
     */
    @Getter
    private Integer maxTokens;
    /**
     * topP
     */
    private Float topP;

    @Override
    public Float getTemperature() {
        return temperature;
    }

    @Override
    public Float getTopP() {
        return topP;
    }

    @Override
    public Integer getTopK() {
        return null;
    }

    protected abstract AbstractExtraChatOptions fromOptions(AbstractExtraChatOptions options);
}
