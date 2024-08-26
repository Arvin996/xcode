package cn.xk.xcode.event;

import cn.xk.xcode.entity.po.MemberExperienceRecordPo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author Administrator
 * @Date 2024/8/26 14:58
 * @Version V1.0.0
 * @Description MemberExperienceRecordEvent
 */
@Getter
public class MemberExperienceRecordEvent extends ApplicationEvent {

    private final MemberExperienceRecordPo memberExperienceRecordPo;

    public MemberExperienceRecordEvent(MemberExperienceRecordPo memberExperienceRecordPo) {
        super(new Object());
       this.memberExperienceRecordPo = memberExperienceRecordPo;
    }
}
