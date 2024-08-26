package cn.xk.xcode.service.listener;

import cn.xk.xcode.event.MemberPointRecordEvent;
import cn.xk.xcode.service.MemberPointRecordService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/26 11:44
 * @Version V1.0.0
 * @Description MemberPointRecordListener
 */
@Component
public class MemberPointRecordListener {

    @Resource
    private MemberPointRecordService memberPointRecordService;

    @Async
    @EventListener
    public void createMemberPointRecord(MemberPointRecordEvent memberPointRecordEvent){
        memberPointRecordService.save(memberPointRecordEvent.getMemberPointRecordPo());
    }
}
