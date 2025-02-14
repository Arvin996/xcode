package cn.xk.xcode.core.extra.options;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/2/13 16:48
 * @Version 1.0.0
 * @Description XcodeXingHuoChatOptions
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class XcodeXingHuoChatOptions extends AbstractExtraChatOptions {
    public static final String MODEL_DEFAULT = "generalv3.5";

    public static XcodeXingHuoChatOptions.Builder builder() {
        return new XcodeXingHuoChatOptions.Builder();
    }

    @Override
    protected AbstractExtraChatOptions fromOptions(AbstractExtraChatOptions options) {
        return XcodeXingHuoChatOptions
                .builder()
                .withModel(options.getModel())
                .withTemperature(options.getTemperature())
                .withMaxTokens(options.getMaxTokens())
                .withTopP(options.getTopP())
                .build();
    }

    public static class Builder {

        private final XcodeXingHuoChatOptions options;

        private Builder() {
            options = new XcodeXingHuoChatOptions();
        }

        public XcodeXingHuoChatOptions.Builder withModel(String model) {
            options.setModel(model);
            return this;
        }

        public XcodeXingHuoChatOptions.Builder withTemperature(Float temperature) {
            options.setTemperature(temperature);
            return this;
        }

        public XcodeXingHuoChatOptions.Builder withMaxTokens(Integer maxTokens) {
            options.setMaxTokens(maxTokens);
            return this;
        }

        public XcodeXingHuoChatOptions.Builder withTopP(Float topP) {
            options.setTopP(topP);
            return this;
        }

        public XcodeXingHuoChatOptions build() {
            return options;
        }
    }
}
