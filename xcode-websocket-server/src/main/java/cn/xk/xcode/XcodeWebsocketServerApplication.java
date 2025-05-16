package cn.xk.xcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class XcodeWebsocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcodeWebsocketServerApplication.class, args);
    }

}
