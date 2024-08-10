package cn.xk.xcode.core;

import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.core.entity.ExportingUser;
import cn.xk.xcode.pojo.CommonResult;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author xuk
 * @Date 2024/8/8 11:11
 * @Version 1.0
 * @Description ExportService
 */
public abstract class ExportService<T, K> {

    public abstract void export();

    private ExcelWriter getExcelWriter(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);
        return EasyExcel.write(response.getOutputStream()).build();
    }

}
