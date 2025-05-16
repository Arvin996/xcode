package cn.xk.xcode.entity.discard.account.other;

import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/3/10 15:43
 * @Version 1.0.0
 * @Description EmailAccount
 **/
@Data
public class EmailAccount {

    // SMTP服务器地址
    private String host;
    // SMTP服务器端口号
    private int port;
    // 登录SMTP服务器所需的用户名
    private String username;
    // 登录SMTP服务器所需的密码
    private String password;
}
