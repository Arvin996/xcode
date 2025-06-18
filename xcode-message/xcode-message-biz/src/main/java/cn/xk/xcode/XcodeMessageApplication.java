package cn.xk.xcode;

import cn.xk.xcode.annotation.EnableServerSecurity;
import cn.xk.xcode.pojo.LoginStpType;
import cn.xk.xcode.pojo.PermissionStpType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author xuk
 * @Date 2025/4/18 15:27
 * @Version 1.0.0
 * @Description XcodeMessageApplication
 **/
@SpringBootApplication
@EnableServerSecurity(permissionType = PermissionStpType.SYSTEM, loginType = LoginStpType.NONE)
public class XcodeMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcodeMessageApplication.class, args);
    }
}
