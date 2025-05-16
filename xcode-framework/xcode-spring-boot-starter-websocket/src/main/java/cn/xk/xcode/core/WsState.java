package cn.xk.xcode.core;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/7/2 14:47
 * @Version 1.0
 * @Description WsState
 */
@Data
@Component
public class WsState {
    private boolean state = false;
}
