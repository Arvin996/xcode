package cn.xk.xcode.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.xk.xcode.entity.discard.account.other.DingDingWorkNoticeAccount;
import cn.xk.xcode.entity.discard.account.other.GeTuiAccount;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.utils.object.ObjectUtil;
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
    public static final String DING_DING_ACCESS_TOKEN_PREFIX = "ding_ding_access_token_";
    public static final long DING_DING_ACCESS_TOKEN_EXPIRE = 3600 * 2;
    public static final String WX_MINI_PROGRAM_TOKEN_PREFIX = "wx_miniProgram_access_token_";
    public static final String WX_OFFICE_TOKEN_PREFIX = "wx_office_access_token_";
    public static final long WX_MINI_PROGRAM_TOKEN_EXPIRE = 2 * 60 * 600;

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
            token = redisTemplate.opsForValue().get(DING_DING_ACCESS_TOKEN_PREFIX + accountPo.getId());
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
            if (StringUtils.hasLength(token)) {
                redisTemplate.opsForValue().set(DING_DING_ACCESS_TOKEN_PREFIX + accountPo.getId(), token, DING_DING_ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS);
            }
            return token;
        } catch (Exception e) {
            log.error("获取DING DING token失败， 报错信息：{}", e.getMessage());
        }
        return token;
    }

    public static String getWxMiniProgramAccessToken(MessageChannelAccountPo messageChannelAccountPo, Map<String, Object> channelParamsValueMap, boolean isRefresh) {
        String token = "";
        try {
            token = redisTemplate.opsForValue().get(WX_MINI_PROGRAM_TOKEN_PREFIX + messageChannelAccountPo.getId());
            if (StrUtil.isNotBlank(token) && !isRefresh) {
                return token;
            }
            Object v1 = channelParamsValueMap.getOrDefault("appId", null);
            Object v2 = channelParamsValueMap.getOrDefault("appSecret", null);
            if (ObjectUtil.isNull(v1)) {
                log.error("微信小程序账号{}没有配置appId", messageChannelAccountPo.getAccountName());
                return token;
            }
            if (ObjectUtil.isNull(v2)) {
                log.error("微信小程序账号{}appSecret", messageChannelAccountPo.getAccountName());
                return token;
            }
            String url = String.format(WX_ACCESS_TOKEN_URL, v1, v2);
            HttpResponse httpResponse = HttpRequest.get(url).execute();
            if (!httpResponse.isOk()) {
                log.error("获取小程序token失败，参数：appId:{},  appSecret:{}, 错误信息：{}", v1, v2, httpResponse.body());
                return token;
            }
            JSONObject jsonObject = JSON.parseObject(httpResponse.body());
            token = jsonObject.getString("access_token");
            if (StrUtil.isEmpty(token)) {
                log.error("获取小程序token失败，参数：appId:{},  appSecret:{}, 错误信息：{}", v1, v2, jsonObject.getString("errmsg"));
                return token;
            }
            redisTemplate.opsForValue().set(WX_MINI_PROGRAM_TOKEN_PREFIX + messageChannelAccountPo.getId(), token, WX_MINI_PROGRAM_TOKEN_EXPIRE, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("获取小程序token失败， 报错信息：{}", e.getMessage());
        }
        return token;
    }

    public static String getWxOfficeAccessToken(MessageChannelAccountPo messageChannelAccountPo, Map<String, Object> channelParamsValueMap, boolean isRefresh) {
        String token = "";
        try {
            token = redisTemplate.opsForValue().get(WX_OFFICE_TOKEN_PREFIX + messageChannelAccountPo.getId());
            if (StrUtil.isNotBlank(token) && !isRefresh) {
                return token;
            }
            Object v1 = channelParamsValueMap.getOrDefault("appId", null);
            Object v2 = channelParamsValueMap.getOrDefault("appSecret", null);
            if (ObjectUtil.isNull(v1)) {
                log.error("微信公众号账号{}没有配置appId", messageChannelAccountPo.getAccountName());
                return token;
            }
            if (ObjectUtil.isNull(v2)) {
                log.error("微信公众号账号{}appSecret", messageChannelAccountPo.getAccountName());
                return token;
            }
            String url = String.format(WX_ACCESS_TOKEN_URL, v1, v2);
            HttpResponse httpResponse = HttpRequest.get(url).execute();
            if (!httpResponse.isOk()) {
                log.error("获取公众号token失败，参数：appId:{},  appSecret:{}, 错误信息：{}", v1, v2, httpResponse.body());
                return token;
            }
            JSONObject jsonObject = JSON.parseObject(httpResponse.body());
            token = jsonObject.getString("access_token");
            if (StrUtil.isEmpty(token)) {
                log.error("获取公众号token失败，参数：appId:{},  appSecret:{}, 错误信息：{}", v1, v2, jsonObject.getString("errmsg"));
                return token;
            }
            redisTemplate.opsForValue().set(WX_OFFICE_TOKEN_PREFIX + messageChannelAccountPo.getId(), token, WX_MINI_PROGRAM_TOKEN_EXPIRE, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("获取公众号token失败， 报错信息：{}", e.getMessage());
        }
        return token;
    }
}
