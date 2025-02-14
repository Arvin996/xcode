package cn.xk.xcode.core.extra.model;

import cn.hutool.core.lang.Assert;
import cn.xk.xcode.core.extra.options.AbstractExtraChatOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.metadata.OpenAiChatResponseMetadata;
import org.springframework.ai.openai.metadata.support.OpenAiResponseHeaderExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2025/2/14 9:24
 * @Version 1.0.0
 * @Description AbstractExtraChatModel
 **/
@Slf4j
public abstract class AbstractExtraChatModel implements ChatModel {

    protected final RetryTemplate retryTemplate;
    protected final OpenAiApi openAiApi;
    protected final AbstractExtraChatOptions defaultOptions;

    protected AbstractExtraChatModel(RetryTemplate retryTemplate, OpenAiApi openAiApi, AbstractExtraChatOptions options) {
        Assert.notNull(openAiApi, "apiKey 不能为空");
        Assert.notNull(options, "options 不能为空");
        Assert.notNull(retryTemplate, "retryTemplate 不能为空");
        this.retryTemplate = retryTemplate;
        this.openAiApi = openAiApi;
        this.defaultOptions = options;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        OpenAiApi.ChatCompletionRequest request = this.createRequest(prompt, false);
        return this.retryTemplate.execute((ctx) -> {
            ResponseEntity<OpenAiApi.ChatCompletion> completionEntity = openAiApi.chatCompletionEntity(request);
            OpenAiApi.ChatCompletion chatCompletion = completionEntity.getBody();
            if (chatCompletion == null) {
                log.warn("No chat completion returned for prompt: {}", prompt);
                return new ChatResponse(List.of());
            } else {
                RateLimit rateLimits = OpenAiResponseHeaderExtractor.extractAiResponseHeaders(completionEntity);
                List<OpenAiApi.ChatCompletion.Choice> choices = chatCompletion.choices();
                if (choices == null) {
                    log.warn("No choices returned for prompt: {}", prompt);
                    return new ChatResponse(List.of());
                } else {
                    List<Generation> generations = choices.stream().map((choice) -> (new Generation(choice.message().content(), this.toMap(chatCompletion.id(), choice))).withGenerationMetadata(ChatGenerationMetadata.from(choice.finishReason().name(), null))).toList();
                    return new ChatResponse(generations, OpenAiChatResponseMetadata.from(completionEntity.getBody()).withRateLimit(rateLimits));
                }
            }
        });
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return defaultOptions;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        OpenAiApi.ChatCompletionRequest request = this.createRequest(prompt, true);
        return this.retryTemplate.execute((ctx) -> {
            Flux<OpenAiApi.ChatCompletionChunk> completionChunks = this.openAiApi.chatCompletionStream(request);
            ConcurrentHashMap<String, String> roleMap = new ConcurrentHashMap<>();
            return completionChunks.map(this::chunkToChatCompletion)
                    .map((chatCompletion) -> {
                        try {
                            String id = chatCompletion.id();
                            List<Generation> generations = chatCompletion.choices().stream().map((choice) -> {
                                if (choice.message().role() != null) {
                                    roleMap.putIfAbsent(id, choice.message().role().name());
                                }

                                String finish = choice.finishReason() != null ? choice.finishReason().name() : "";
                                Generation generation = new Generation(choice.message().content(), Map.of("id", id, "role", roleMap.get(id), "finishReason", finish));
                                if (choice.finishReason() != null) {
                                    generation = generation.withGenerationMetadata(ChatGenerationMetadata.from(choice.finishReason().name(), null));
                                }

                                return generation;
                            }).toList();
                            return new ChatResponse(generations);
                        } catch (Exception e) {
                            log.error("Error processing chat completion", e);
                            return new ChatResponse(List.of());
                        }
                    });
        });
    }

    protected OpenAiApi.ChatCompletionRequest createRequest(Prompt prompt, boolean stream) {
        // 1. 构建 ChatCompletionMessage 对象
        List<OpenAiApi.ChatCompletionMessage> chatCompletionMessages = prompt.getInstructions().stream().map(m ->
                new OpenAiApi.ChatCompletionMessage(m.getContent(), OpenAiApi.ChatCompletionMessage.Role.valueOf(m.getMessageType().name()))).toList();
        OpenAiApi.ChatCompletionRequest request = new OpenAiApi.ChatCompletionRequest(chatCompletionMessages, stream);

        // 2.1 补充 prompt 内置的 options
        if (prompt.getOptions() != null) {
            if (prompt.getOptions() instanceof ChatOptions runtimeOptions) {
                OpenAiChatOptions updatedRuntimeOptions = ModelOptionsUtils.copyToTarget(runtimeOptions,
                        ChatOptions.class, OpenAiChatOptions.class);
                request = ModelOptionsUtils.merge(updatedRuntimeOptions, request, OpenAiApi.ChatCompletionRequest.class);
            } else {
                throw new IllegalArgumentException("Prompt options are not of type ChatOptions: "
                        + prompt.getOptions().getClass().getSimpleName());
            }
        }
        // 2.2 补充默认 options
        if (this.defaultOptions != null) {
            request = ModelOptionsUtils.merge(request, this.defaultOptions, OpenAiApi.ChatCompletionRequest.class);
        }
        return request;
    }

    private Map<String, Object> toMap(String id, OpenAiApi.ChatCompletion.Choice choice) {
        Map<String, Object> map = new HashMap<>();
        OpenAiApi.ChatCompletionMessage message = choice.message();
        if (message.role() != null) {
            map.put("role", message.role().name());
        }

        if (choice.finishReason() != null) {
            map.put("finishReason", choice.finishReason().name());
        }

        map.put("id", id);
        return map;
    }

    private OpenAiApi.ChatCompletion chunkToChatCompletion(OpenAiApi.ChatCompletionChunk chunk) {
        List<OpenAiApi.ChatCompletion.Choice> choices = chunk.choices().stream().map((cc) -> new OpenAiApi.ChatCompletion.Choice(cc.finishReason(), cc.index(), cc.delta(), cc.logprobs())).toList();
        return new OpenAiApi.ChatCompletion(chunk.id(), choices, chunk.created(), chunk.model(), chunk.systemFingerprint(), "chat.completion", null);
    }
}
