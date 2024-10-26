package cn.xk.xcode.core.queue.event;

import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.core.queue.model.ExportModel;
import com.lmax.disruptor.EventFactory;

/**
 * @Author Administrator
 * @Date 2024/8/20 20:14
 * @Version V1.0.0
 * @Description QueueExportEventFactory
 */
public class QueueExportEventFactory<T, K> implements EventFactory<ExportModel<T, K>> {
    @Override
    public ExportModel newInstance() {
        return ExportModel.builder().username(StpUtil.getLoginIdAsString()).threadId(Thread.currentThread().getId()).build();
    }
}
