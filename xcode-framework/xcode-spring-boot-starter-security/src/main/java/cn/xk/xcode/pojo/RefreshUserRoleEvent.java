package cn.xk.xcode.pojo;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/5/22 17:08
 * @Version 1.0.0
 * @Description RefreshUserRoleEvent
 **/
@Getter
public class RefreshUserRoleEvent extends ApplicationEvent {

    private final Object loginId;
    private final Set<String> roles;

    public RefreshUserRoleEvent(Object loginId, Set<String> roles){
        super(loginId);
        this.loginId = loginId;
        this.roles = roles;

    }
}
