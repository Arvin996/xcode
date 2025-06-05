package cn.xk.xcode.utils.ip.online;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author xuk
 * @Date 2024/11/22 16:30
 * @Version 1.0.0
 * @Description AddressUtil
 **/
@Slf4j
public class AddressUtil {

    private AddressUtil() {
    }

    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtil.internalIp(ip)) {
            return "内网IP";
        }
        try {
            String rspStr = HttpRequest.get(IP_URL + "?" + "ip=" + ip + "&json=true").contentType("application/json;charset=utf-8").execute().body();
            if (StringUtils.isEmpty(rspStr)) {
                return UNKNOWN;
            }
            JSONObject obj = JSONUtil.parseObj(rspStr);
            String region = obj.getStr("pro");
            String city = obj.getStr("city");
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return UNKNOWN;
    }
}
