package cn.xk.xcode;

import cn.xk.xcode.core.annotation.EnableCrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author xuk
 * @Date 2024/12/24 14:33
 * @Version 1.0.0
 * @Description Test1Application
 **/
@SpringBootApplication
//@EnableCrypt(isSign = false)
public class Test1Application {

    public static void main(String[] args) {
        SpringApplication.run(Test1Application.class, args);
    }
}
