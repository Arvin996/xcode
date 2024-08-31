package cn.xk.xcode.core.queue.model;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author Administrator
 * @Date 2024/8/20 20:08
 * @Version V1.0.0
 * @Description ExportModel
 */
@Data
@Builder
public class ExportModel {
    private String username;

    private Long threadId;

    private HttpServletResponse response;
}
