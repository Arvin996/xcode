package cn.xk.xcode.entity.message;

import cn.xk.xcode.message.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/11/8 11:16
 * @Version 1.0.0
 * @Description OrderCancelMessage
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelMessage extends BaseMessage {
}
