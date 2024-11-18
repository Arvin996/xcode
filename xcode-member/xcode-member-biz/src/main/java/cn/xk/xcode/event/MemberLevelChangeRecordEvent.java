package cn.xk.xcode.event;

import cn.xk.xcode.entity.po.MemberLevelChangeRecordPo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author Administrator
 * @Date 2024/8/26 15:00
 * @Version V1.0.0
 * @Description MemberLevelChangeRecordEvent
 */
@Getter
public class MemberLevelChangeRecordEvent extends ApplicationEvent {

    private final MemberLevelChangeRecordPo memberLevelChangeRecordPo;

    public MemberLevelChangeRecordEvent(MemberLevelChangeRecordPo memberLevelChangeRecordPo) {
        super(new Object());
        this.memberLevelChangeRecordPo = memberLevelChangeRecordPo;
    }
}
