package cn.xk.xcode.handler.message;

import lombok.Builder;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/4/18 10:38
 * @Version 1.0.0
 * @Description WithdrawResult
 **/
@Data
@Builder
public class WithdrawResult {

    private Integer taskDetailId;

    private boolean success;
}
