package cn.xk.xcode.entity;

import cn.xk.xcode.message.BaseMessage;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/8/8 15:27
 * @Version 1.0
 * @Description UserLoginLog
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginLog extends BaseMessage
{
    private String userId;

    private String username;

    private LocalDateTime loginTIme;

    private String loginArea;

    private String loginIp;
}
