package cn.xk.xcode;

import cn.xk.xcode.annotation.EnableServerSecurity;
import cn.xk.xcode.pojo.StpType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableServerSecurity(type = StpType.MIXED)
public class XcodeInfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcodeInfraApplication.class, args);
    }

}
