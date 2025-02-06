package cn.xk.xcode.task;

import cn.xk.xcode.annotation.AutoRegisterXxlJob;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/2/6 8:50
 * @Version 1.0.0
 * @Description xxlTest
 **/
@Component
public class xxlTest {

    @XxlJob("xxlTest")
    @AutoRegisterXxlJob(cron = "0/5 * * * * ?", jobDesc = "xxlTest", author = "xuk")
    public void xxlJobTest(){
        System.out.println("xxlJobTest");
    }
}
