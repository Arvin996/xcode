package cn.xk.xcode.core.pipeline;

/**
 * @Author xuk
 * @Date 2025/3/6 16:45
 * @Version 1.0.0
 * @Description TaskHandler 业务执行器
 **/
public interface TaskHandler<T extends TaskModel> {

    // 真正执行业务逻辑
    void handle(TaskContext<T> taskContext);

    int getOrder();

    String getCode();
}
