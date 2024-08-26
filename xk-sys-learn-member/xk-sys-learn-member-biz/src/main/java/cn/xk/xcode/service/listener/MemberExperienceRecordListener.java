package cn.xk.xcode.service.listener;

import cn.xk.xcode.event.MemberExperienceRecordEvent;
import cn.xk.xcode.service.MemberExperienceRecordService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/26 15:03
 * @Version V1.0.0
 * @Description MemberExperienceRecordListener
 */
@Component
public class MemberExperienceRecordListener {

    @Resource
    private MemberExperienceRecordService memberExperienceRecordService;

    @Async
    @EventListener
    public void createMemberExperienceRecord(MemberExperienceRecordEvent memberExperienceRecordEvent){
        memberExperienceRecordService.save(memberExperienceRecordEvent.getMemberExperienceRecordPo());
    }
}
