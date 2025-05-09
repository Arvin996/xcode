package cn.xk.xcode.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.xk.xcode.entity.account.other.DingDingWorkNoticeAccount;
import cn.xk.xcode.entity.account.other.GeTuiAccount;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.constants.MessageChannelUrlConstant.*;

/**
 * @Author xuk
 * @Date 2025/3/11 8:25
 * @Version 1.0.0
 * @Description AccessTokenUtil
 **/
@Slf4j
public class AccessTokenUtil {

    public static final String GE_TUI_ACCESS_TOKEN_PREFIX = "ge_tui_access_token_";
    public static final long GE_TUI_ACCESS_TOKEN_EXPIRE = 24 * 60 * 60;
    public static final String DING_DING_ACCESS_TOEN_PREFIX = "ding_ding_access_token_";
    public static final long DING_DING_ACCESS_TOKEN_EXPIRE = 3600 * 2;

    private final static StringRedisTemplate redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);

    public static String getGeTuiAccessToken(MessageChannelAccountPo accountPo, GeTuiAccount geTuiAccount, Boolean refresh) {
        String token = "";
        try {
            token = redisTemplate.opsForValue().get(GE_TUI_ACCESS_TOKEN_PREFIX + accountPo.getId());
            if (token != null && !refresh) {
                return token;
            }
            String url = GE_TUI_BASE_URL + geTuiAccount.getAppId() + GE_TUI_AUTH;
            String time = String.valueOf(System.currentTimeMillis());
            String digest = SecureUtil.sha256().digestHex(geTuiAccount.getAppKey() + time + geTuiAccount.getMasterSecret());
            Map<String, String> param = new HashMap<>();
            param.put("timestamp", time);
            param.put("appKey", geTuiAccount.getAppKey());
            param.put("sign", digest);
            String body = HttpRequest.post(url).header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                    .body(JSON.toJSONString(param))
                    .timeout(2000)
                    .execute().body();
            JSONObject jsonObject = JSON.parseObject(body);
            if (jsonObject.getIntValue("code") == 0) {
                token = jsonObject.getJSONObject("data").getString("token");
            }
            if (StringUtils.hasLength(token)) {
                redisTemplate.opsForValue().set(GE_TUI_ACCESS_TOKEN_PREFIX + accountPo.getId(), token, GE_TUI_ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS);
            }
            return token;
        } catch (Exception e) {
            log.error("获取ge tui token失败， 报错信息：{}", e.getMessage());
        }
        return token;
    }

    public static String getDingDingAccessToken(MessageChannelAccountPo accountPo, DingDingWorkNoticeAccount dingWorkNoticeAccount, Boolean refresh) {
        String token = "";
        try {
            token = redisTemplate.opsForValue().get(DING_DING_ACCESS_TOEN_PREFIX + accountPo.getId());
            if (token != null && !refresh) {
                return token;
            }
            DingTalkClient client = new DefaultDingTalkClient(DING_DING_TOKEN_URL);
            String time = String.valueOf(System.currentTimeMillis());
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(dingWorkNoticeAccount.getAppKey());
            req.setAppsecret(dingWorkNoticeAccount.getAppSecret());
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            token = rsp.getAccessToken();
            if (StringUtils.hasLength(token)){
                redisTemplate.opsForValue().set(DING_DING_ACCESS_TOEN_PREFIX + accountPo.getId(), token, DING_DING_ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS);
            }
            return token;
        } catch (Exception e) {
            log.error("获取DING DING token失败， 报错信息：{}", e.getMessage());
        }
        return token;
    }
}
