package cn.xk.xcode;

import cn.xk.xcode.annotation.EnableServerSecurity;
import cn.xk.xcode.pojo.LoginStpType;
import cn.xk.xcode.pojo.PermissionStpType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author xuk
 * @Date 2025/5/9 11:16
 * @Version 1.0.0
 * @Description MainApplication
 **/
@SpringBootApplication
@EnableServerSecurity(permissionType = PermissionStpType.SYSTEM, loginType = LoginStpType.SYSTEM)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
