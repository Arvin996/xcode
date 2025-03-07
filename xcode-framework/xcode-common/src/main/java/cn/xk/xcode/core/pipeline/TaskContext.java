package cn.xk.xcode.core.pipeline;

import cn.xk.xcode.pojo.CommonResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2025/3/6 16:42
 * @Version 1.0.0
 * @Description TaskContext 责任连上下文
 **/
@Data
@Slf4j
public class TaskContext<T extends TaskModel> implements Serializable {

    // 业务数据
    private T taskModel;

    // 责任链标识
    private String code;

    // 是否直接退出
    private Boolean isBreak;

    // 优先级 执行顺序
    private Integer order;

    // 返回数据
    private CommonResult<?> commonResult;

}
