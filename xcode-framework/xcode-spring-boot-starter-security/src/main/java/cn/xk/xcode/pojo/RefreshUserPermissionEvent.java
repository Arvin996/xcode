package cn.xk.xcode.pojo;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/5/22 17:04
 * @Version 1.0.0
 * @Description RefreshUserPermissionEvent
 **/
@Getter
public class RefreshUserPermissionEvent extends ApplicationEvent {

    private final Object loginId;
    private final Set<String> permissions;

    public RefreshUserPermissionEvent(Object loginId, Set<String> permissions){
        super(loginId);
        this.loginId = loginId;
        this.permissions = permissions;

    }

}
