package cn.xk.xcode.service.listener;

import cn.xk.xcode.event.MemberSignRecordEvent;
import cn.xk.xcode.service.MemberSignRecordService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/26 10:17
 * @Version V1.0.0
 * @Description MemberSignRecordListener
 */
@Component
public class MemberSignRecordListener {

    @Resource
    private MemberSignRecordService memberSignRecordService;

    @Async
    @EventListener
    public void createMemberSignRecord(MemberSignRecordEvent event) {
        memberSignRecordService.save(event.getMemberSignRecordPo());
    }
}
