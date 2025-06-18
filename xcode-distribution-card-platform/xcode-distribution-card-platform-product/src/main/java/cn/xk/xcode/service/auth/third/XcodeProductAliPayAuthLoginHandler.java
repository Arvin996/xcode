package cn.xk.xcode.service.auth.third;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import cn.xk.xcode.utils.object.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthAlipayRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.THIRD_LOGIN_FAILED;

/**
 * @Author xuk
 * @Date 2025/6/10 14:20
 * @Version 1.0.0
 * @Description XcodeProductAliPayAuthLoginHandler
 **/
@Component
@Slf4j
public class XcodeProductAliPayAuthLoginHandler extends AbstractXcodeProductAuthLoginHandler {

    public XcodeProductAliPayAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties, ProductMerchantThirdService productMerchantThirdService, ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties, productMerchantThirdService, productMerchantService);
    }

    @Override
    protected ThirdLoginType getThirdLoginType() {
        return ThirdLoginType.ALIPAY;
    }

    @Override
    protected AuthRequest getAuthRequest() {
        XcodeThirdLoginProperties.AlipayProperties alipay = xcodeThirdLoginProperties.getAlipay();
        ClassPathResource publicKeyResource = new ClassPathResource("/alikey/alipay_public_key.txt");
        ClassPathResource privateKeyResource = new ClassPathResource("/alikey/alipay_private_key.txt");
        String alipayPublicKey = "";
        String alipayPrivateKey = "";
        try (InputStream inputStream = publicKeyResource.getInputStream(); InputStream inputStream1 = privateKeyResource.getInputStream()) {
            try {
                // 使用InputStreamReader和BufferedReader来读取数据
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n"); // 添加换行符，根据需要调整
                }
                alipayPublicKey = stringBuilder.toString();
                inputStreamReader = new InputStreamReader(inputStream1, StandardCharsets.UTF_8);
                reader = new BufferedReader(inputStreamReader);
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n"); // 添加换行符，根据需要调整
                }
                alipayPrivateKey = stringBuilder.toString();
            } catch (Exception e) {
                log.error("读取支付宝公钥或私钥失败, 失败信息：{}", e.getMessage());
                ExceptionUtil.castServerException(THIRD_LOGIN_FAILED);
            }
        } catch (Exception e) {
            log.error("关闭流失败, 失败信息：{}", e.getMessage());
            ExceptionUtil.castServerException(THIRD_LOGIN_FAILED);
        }
        // 确保关闭流
        return new AuthAlipayRequest(AuthConfig.builder()
                .clientId(alipay.getAppId())
                .clientSecret(alipayPrivateKey)
                .redirectUri(alipay.getCallbackUrl())
                .build(), alipayPublicKey);
    }
}
