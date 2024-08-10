package cn.xk.xcode.config;

import lombok.Data;

@Data
public class CheckCodeEmailProperties {
    // SMTP服务器地址
    private String host;

    // SMTP服务器端口号
    private int port;

    // 登录SMTP服务器所需的用户名
    private String username;

    // 登录SMTP服务器所需的密码
    private String password;
}