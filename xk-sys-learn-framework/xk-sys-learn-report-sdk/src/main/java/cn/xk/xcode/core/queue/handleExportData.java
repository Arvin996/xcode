package cn.xk.xcode.core.queue;

import cn.xk.xcode.core.queue.model.ExportModel;
import com.lmax.disruptor.RingBuffer;
import lombok.RequiredArgsConstructor;

/**
 * @Author Administrator
 * @Date 2024/8/20 20:50
 * @Version V1.0.0
 * @Description handleExportData
 */
@RequiredArgsConstructor
public abstract class handleExportData {
    private final RingBuffer<ExportModel>  exportModelRingBuffer;

    public void handle(ExportModel exportModel){
        execute();
    }

    public abstract void execute();
}
