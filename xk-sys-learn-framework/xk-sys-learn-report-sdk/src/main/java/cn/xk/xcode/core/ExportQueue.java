package cn.xk.xcode.core;

import cn.xk.xcode.config.ExportQueueProperties;
import cn.xk.xcode.core.entity.ExportingUser;
import cn.xk.xcode.exception.core.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.LinkedList;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.UNKNOWN;

/**
 * @Author xuk
 * @Date 2024/8/8 10:47
 * @Version 1.0
 * @Description ExportQueue
 */
@Slf4j
@RequiredArgsConstructor
public class ExportQueue {

    private final ExportQueueProperties exportQueueProperties;

    private final LinkedList<ExportingUser> queue = new LinkedList<>();

    public synchronized void addToQueue(ExportingUser exportingUser) {
        while (queue.size() >= exportQueueProperties.getCoreSize()) {
            try {
                log.info("当前报表导出队列排队人数已满，请稍后再试");
                wait();
            } catch (InterruptedException e) {
                throw new ServiceException(UNKNOWN);
            }
        }
        queue.add(exportingUser);
        log.info("当前报表导出人数:{}", queue.size());
        notifyAll();
    }

    public synchronized void getNextExportingUser() {
        while (queue.isEmpty()) {
            try {
                log.warn("当前报表导出队列为空，无人需要导出报表");
                wait();
            } catch (InterruptedException e) {
                throw new ServiceException(UNKNOWN);
            }
        }
        ExportingUser exportingUser = queue.remove();
        log.info("用户{}.{}处理完毕报表导出完毕，当前报表导出人数:{}", exportingUser.getThreadId(), exportingUser.getUserId(), queue.size());
    }
}
