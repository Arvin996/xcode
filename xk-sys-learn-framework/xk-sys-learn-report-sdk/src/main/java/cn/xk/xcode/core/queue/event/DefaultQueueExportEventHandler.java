package cn.xk.xcode.core.queue.event;

import java.util.Collections;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/28 10:04
 * @Version V1.0.0
 * @Description DefaultQueueExportEventHandler
 */
public class DefaultQueueExportEventHandler extends AbsQueueExportEventHandler<Object, Object>{

    @Override
    public int totalExportCount(Object o) {
        return 0;
    }

    @Override
    public List<Object> getExportData(Object object, long StartPageNo) {
        return Collections.emptyList();
    }

    @Override
    public long getNextStartId(Object object, long currentNo, int pageSize) {
        return 0;
    }

}
