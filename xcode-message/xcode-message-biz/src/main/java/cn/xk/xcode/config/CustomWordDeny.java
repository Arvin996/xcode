package cn.xk.xcode.config;

import com.github.houbb.sensitive.word.api.IWordDeny;
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
 * @Date 2025/3/11 16:02
 * @Version 1.0.0
 * @Description CustomWordDeny
 **/
@Slf4j
public class CustomWordDeny implements IWordDeny {
    @Override
    public List<String> deny() {
        List<String> list = new ArrayList<>();
        ;
        try {
            Resource mySensitiveWords = new ClassPathResource("mySensitiveWords.txt");
            Path mySensitiveWordsPath = Paths.get(mySensitiveWords.getFile().getPath());
            list = Files.readAllLines(mySensitiveWordsPath, StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            log.error("读取敏感词文件错误！错误信息；{}", ioException.getMessage());
        }
        return list;
    }
}
