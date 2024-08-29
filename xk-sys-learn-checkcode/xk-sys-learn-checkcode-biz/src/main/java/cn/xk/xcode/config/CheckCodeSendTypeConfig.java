package cn.xk.xcode.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/8/1 11:35
 * @Version 1.0
 * @Description CheckCodeSendTypeConfig
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckCodeSendTypeConfig {
    private CheckCodeEmailProperties emailConfig;

    private CheckCodeMobileProperties mobileConfig;
}
