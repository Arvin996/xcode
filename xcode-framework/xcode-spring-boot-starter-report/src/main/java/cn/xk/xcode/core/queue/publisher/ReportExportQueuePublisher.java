package cn.xk.xcode.core.queue.publisher;

import cn.xk.xcode.core.queue.model.ExportModel;
import cn.xk.xcode.exception.core.ExceptionUtil;
import com.lmax.disruptor.RingBuffer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.EXCEL_MESSAGE_ADD_ERROR;

/**
 * @Author Administrator
 * @Date 2024/8/28 8:43
 * @Version V1.0.0
 * @Description ReportExportQueueService
 */
@Slf4j
@RequiredArgsConstructor
public class ReportExportQueuePublisher<T, K> {

    private final RingBuffer<ExportModel<T, K>> ringBuffer;

    public void handleReportExport(HttpServletResponse httpServletResponse, String fileName, T t, Class<K> kClass, int PageSize){
        long sequence = ringBuffer.next();
        try {
            //给Event填充数据
            ExportModel<T, K> event = ringBuffer.get(sequence);
            event.setResponse(httpServletResponse);
            event.setFileName(fileName);
            event.setData(t);
            event.setKClass(kClass);
            event.setPageSize(PageSize);
            log.info("往消息队列中添加消息：{}", event);
        } catch (Exception e) {
            log.error("failed to add event to messageModelRingBuffer for : e = {},{}", e, e.getMessage());
            ExceptionUtil.castServerException(EXCEL_MESSAGE_ADD_ERROR);
        } finally {
            //发布Event，激活观察者去消费，将sequence传递给改消费者
            //注意最后的publish方法必须放在finally中以确保必须得到调用；
            // 如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
            ringBuffer.publish(sequence);
        }
    }
}
