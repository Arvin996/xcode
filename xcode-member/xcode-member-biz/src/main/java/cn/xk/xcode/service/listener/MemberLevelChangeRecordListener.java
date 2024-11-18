package cn.xk.xcode.service.listener;

import cn.xk.xcode.event.MemberLevelChangeRecordEvent;
import cn.xk.xcode.service.MemberLevelChangeRecordService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/26 15:08
 * @Version V1.0.0
 * @Description MemberLevelChangeRecordListener
 */
@Component
public class MemberLevelChangeRecordListener {

    @Resource
    private MemberLevelChangeRecordService memberLevelChangeRecordService;

    @Async
    @EventListener
    public void createMemberLevelChangeRecord(MemberLevelChangeRecordEvent memberLevelChangeRecordEvent) {
        memberLevelChangeRecordService.save(memberLevelChangeRecordEvent.getMemberLevelChangeRecordPo());
    }
}
