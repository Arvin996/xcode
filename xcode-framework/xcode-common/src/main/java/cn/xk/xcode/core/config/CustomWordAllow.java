package cn.xk.xcode.core.config;

import com.github.houbb.sensitive.word.api.IWordAllow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 16:05
 * @Version 1.0.0
 * @Description CustomWordAllow
 **/
@Slf4j
public class CustomWordAllow implements IWordAllow {
    @Override
    public List<String> allow() {
        List<String> list = new ArrayList<>();;
        try {
            Resource mySensitiveWords = new ClassPathResource("myAllowWords.txt");
            Path mySensitiveWordsPath = Paths.get(mySensitiveWords.getFile().getPath());
            list =  Files.readAllLines(mySensitiveWordsPath, StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            log.error("读取敏感词文件错误！{}", ioException.getMessage());
        }
        return list;
    }
}
