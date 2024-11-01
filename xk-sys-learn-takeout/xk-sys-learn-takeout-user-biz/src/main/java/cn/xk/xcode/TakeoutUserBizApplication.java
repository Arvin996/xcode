package cn.xk.xcode;

import cn.xk.xcode.client.CheckCodeClientApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author xuk
 * @Date 2024/10/30 14:59
 * @Version 1.0.0
 * @Description TakeoutUserBizApplication
 **/
@EnableFeignClients(clients = {CheckCodeClientApi.class})
@SpringBootApplication
public class TakeoutUserBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeoutUserBizApplication.class, args);
    }
}
