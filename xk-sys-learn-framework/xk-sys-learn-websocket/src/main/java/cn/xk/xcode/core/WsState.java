package cn.xk.xcode.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/7/2 14:47
 * @Version 1.0
 * @Description WsState
 */
@Getter
@Component
@Setter
public class WsState
{
    private boolean state = false;
}
