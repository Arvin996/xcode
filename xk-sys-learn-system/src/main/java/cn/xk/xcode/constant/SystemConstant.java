package cn.xk.xcode.constant;

/**
 * @Author xuk
 * @Date 2024/7/8 09:45
 * @Version 1.0
 * @Description SystemConstant
 */
public class SystemConstant
{
    public static final String WX_USER_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    public static final String WX_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    public static final String QQ_USER_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&fmt=json";

    public static final String QQ_USER_OPEN_ID = "https://graph.qq.com/oauth2.0/me?access_token=%s&fmt=json";

    public static final String QQ_USER_INFO_URL = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";

}
