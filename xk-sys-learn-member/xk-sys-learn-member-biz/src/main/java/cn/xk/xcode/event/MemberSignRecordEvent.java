package cn.xk.xcode.event;

import cn.xk.xcode.entity.po.MemberSignRecordPo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author Administrator
 * @Date 2024/8/26 10:12
 * @Version V1.0.0
 * @Description MemberSignRecordEvent 签到记录事件
 */
@Getter
public class MemberSignRecordEvent extends ApplicationEvent {

    private final MemberSignRecordPo memberSignRecordPo;

    public MemberSignRecordEvent(MemberSignRecordPo memberSignRecordPo) {
        super(new Object());
        this.memberSignRecordPo = memberSignRecordPo;
    }
}
