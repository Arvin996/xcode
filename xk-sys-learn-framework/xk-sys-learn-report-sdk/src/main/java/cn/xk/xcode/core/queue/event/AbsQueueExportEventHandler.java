package cn.xk.xcode.core.queue.event;

import cn.xk.xcode.core.queue.model.ExportModel;
import cn.xk.xcode.exception.core.ExceptionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lmax.disruptor.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.EXCEL_EXPORT_ERROR;

/**
 * @Author Administrator
 * @Date 2024/8/20 20:16
 * @Version V1.0.0
 * @Description QueueExportEventHandler
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbsQueueExportEventHandler<T, K> implements EventHandler<ExportModel<T, K>> {

    @Override
    public void onEvent(ExportModel exportModel, long l, boolean b) {
        log.info("线程号{}, 用户编号{}开始导出报表", exportModel.getThreadId(), exportModel.getUsername());

    }

    /**
     * 导出报表
     * @param httpServletResponse http返回
     * @param pageSize            每页大小
     * @param t                   导出条件
     * @param kClass              导出类型
     * @param fileName            导出文件名
     */
    public void export(HttpServletResponse httpServletResponse, int pageSize, T t, Class<K> kClass, String fileName) {
        ExcelWriter excelWriter = null;
        try {
            excelWriter = getExcelWriter(httpServletResponse, fileName);
            int totalExportCount = totalExportCount(t);
            int pageCount = totalExportCount / pageSize;
            if (totalExportCount % pageSize != 0) {
                pageCount++;
            }
            List<K> exportData = exportData(t);
            for (int i = 1; i <= pageCount; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, fileName).head(kClass).build();
                excelWriter.write(getWriteDate(exportData, pageSize, i), writeSheet);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            ExceptionUtil.castServerException(EXCEL_EXPORT_ERROR);
        } finally {
            if (!Objects.isNull(excelWriter)) {
                excelWriter.finish();
            }
        }
    }

    private List<K> getWriteDate(List<K> data, int pageSize, int pageNum) {
        return data.subList((pageNum - 1) * pageSize, Math.min(pageNum * pageSize, data.size()));
    }

    private ExcelWriter getExcelWriter(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");//这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileNameUtf = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("content-disposition", "attachment;filename*=utf-8''" + fileNameUtf + ".xlsx");
        return EasyExcel.write(response.getOutputStream()).build();
    }

    /**
     * 导出总条数
     *
     * @param t 导出条件
     * @return 导出条数
     */
    public abstract int totalExportCount(T t);


    /**
     * 导出数据
     *
     * @param t 导出条件
     */
    public abstract List<K> exportData(T t);
}
