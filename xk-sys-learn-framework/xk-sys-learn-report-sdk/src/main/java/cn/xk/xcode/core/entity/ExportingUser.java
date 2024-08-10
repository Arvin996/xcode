package cn.xk.xcode.core.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2024/8/8 10:53
 * @Version 1.0
 * @Description ExportingUser
 */
@Data
@Builder
public class ExportingUser
{
    /**
     * 线程id
     */
    private long threadId;

    /**
     * 用户id
     */
    private String UserId;
}
