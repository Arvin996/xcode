package cn.xk.xcode;

import cn.xk.xcode.annotation.EnableServerSecurity;
import cn.xk.xcode.pojo.LoginStpType;
import cn.xk.xcode.pojo.PermissionStpType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableServerSecurity(permissionType = PermissionStpType.MIXED, loginType = LoginStpType.MEMBER)
public class XcodeInfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcodeInfraApplication.class, args);
    }

}
