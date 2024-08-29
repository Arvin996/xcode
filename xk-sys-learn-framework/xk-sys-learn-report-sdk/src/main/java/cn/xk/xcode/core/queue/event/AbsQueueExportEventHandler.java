package cn.xk.xcode.core.queue.event;

import cn.xk.xcode.core.queue.model.ExportModel;
import com.lmax.disruptor.EventHandler;
import lombok.RequiredArgsConstructor;

/**
 * @Author Administrator
 * @Date 2024/8/20 20:16
 * @Version V1.0.0
 * @Description QueueExportEventHandler
 */
@RequiredArgsConstructor
public abstract class AbsQueueExportEventHandler implements EventHandler<ExportModel> {

    // ... todo 考虑怎么优雅封装 通过easyexcel导出
    @Override
    public void onEvent(ExportModel exportModel, long l, boolean b) throws Exception {

    }
}
