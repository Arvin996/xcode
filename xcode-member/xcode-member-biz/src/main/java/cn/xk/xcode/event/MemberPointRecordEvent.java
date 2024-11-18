package cn.xk.xcode.event;

import cn.xk.xcode.entity.po.MemberPointRecordPo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author Administrator
 * @Date 2024/8/26 11:42
 * @Version V1.0.0
 * @Description MemberPointRecordEvent
 */
@Getter
public class MemberPointRecordEvent extends ApplicationEvent {

    private final MemberPointRecordPo memberPointRecordPo;

    public MemberPointRecordEvent(MemberPointRecordPo memberPointRecordPo) {
        super(new Object());
        this.memberPointRecordPo = memberPointRecordPo;
    }
}
