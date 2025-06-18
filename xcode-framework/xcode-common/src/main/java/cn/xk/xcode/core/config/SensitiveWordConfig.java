package cn.xk.xcode.core.config;

import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xuk
 * @Date 2025/6/4 9:48
 * @Version 1.0.0
 * @Description SensitiveWordConfig
 **/
@Configuration
public class SensitiveWordConfig {

    // 配置默认敏感词 + 自定义敏感词
    IWordDeny wordDeny = WordDenys.chains(WordDenys.defaults(), new CustomWordDeny());
    // 配置默认非敏感词 + 自定义非敏感词
    IWordAllow wordAllow = WordAllows.chains(WordAllows.defaults(), new CustomWordAllow());

    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        return SensitiveWordBs.newInstance()
                // 忽略大小写
                .ignoreCase(true)
                // 忽略半角圆角
                .ignoreWidth(true)
                // 忽略数字的写法
                .ignoreNumStyle(true)
                // 忽略中文的书写格式：简繁体
                .ignoreChineseStyle(true)
                // 忽略英文的书写格式
                .ignoreEnglishStyle(true)
                // 忽略重复词
                .ignoreRepeat(false)
                // 是否启用数字检测
                .enableNumCheck(false)
                // 是否启用邮箱检测
                .enableEmailCheck(false)
                // 是否启用url检测
                .enableUrlCheck(false)
                // 是否启用ip检测
                .enableIpv4Check(false)
                // 是否启用链接检测
                .enableUrlCheck(false)
                // 数字检测，自定义指定长度
                // .numCheckLen(8)
                // 配置自定义敏感词
                .wordDeny(wordDeny)
                // 配置非自定义敏感词
                .wordAllow(wordAllow)
                .init();
    }
}
