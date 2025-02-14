package cn.xk.xcode.core.extra.options;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/2/13 16:00
 * @Version 1.0.0
 * @Description XcodeDeepSeekChatOptions
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class XcodeDeepSeekChatOptions extends AbstractExtraChatOptions {

    public static final String MODEL_DEFAULT = "deepseek-chat";


    @Override
    protected AbstractExtraChatOptions fromOptions(AbstractExtraChatOptions options) {
        return XcodeDeepSeekChatOptions
                .builder()
                .withModel(options.getModel())
                .withTemperature(options.getTemperature())
                .withMaxTokens(options.getMaxTokens())
                .withTopP(options.getTopP())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final XcodeDeepSeekChatOptions options;

        private Builder() {
            options = new XcodeDeepSeekChatOptions();
        }

        public Builder withModel(String model) {
            options.setModel(model);
            return this;
        }

        public Builder withTemperature(Float temperature) {
            options.setTemperature(temperature);
            return this;
        }

        public Builder withMaxTokens(Integer maxTokens) {
            options.setMaxTokens(maxTokens);
            return this;
        }

        public Builder withTopP(Float topP) {
            options.setTopP(topP);
            return this;
        }

        public XcodeDeepSeekChatOptions build() {
            return options;
        }
    }
}
