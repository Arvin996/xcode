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

    // 执行顺序 越小越先执行
    int getOrder();

    // 责任链标识
    String getCode();

    default String getHandlerName() {
        return getCode() + "---" + this.getClass().getSimpleName();
    }
}
