package cn.xk.xcode.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/5/27 15:58
 * @Version 1.0
 * @Description PageResult
 */
@AllArgsConstructor
@Data
@Schema(description = "分页返回结果")
public class PageResult<T> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "总数", requiredMode = Schema.RequiredMode.REQUIRED)
    private long total;

    @Schema(description = "数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<T> data;

    private PageParam pageParam;

    public PageResult(long pageNo, long pageSize) {
        this.pageParam = new PageParam(pageNo, pageSize);
    }

    public PageResult(PageParam pageParam, long total) {
        this.pageParam = pageParam;
        this.total = total;
        this.data = new ArrayList<>();
    }

    public PageResult(PageParam pageParam, long total, List<T> data) {
        this.pageParam = pageParam;
        this.total = total;
        this.data = data;
    }

    public PageResult(long pageNo, long pageSize, long total) {
        this.pageParam = new PageParam(pageNo, pageSize);
        this.total = total;
        this.data = new ArrayList<>();
    }

    public PageResult(long pageNo, long pageSize, long total, List<T> data) {
        this.pageParam = new PageParam(pageNo, pageSize);
        this.total = total;
        this.data = data;
    }

    public PageResult(long total, PageParam pageParam) {
        this.pageParam = pageParam;
        this.total = total;
        this.data = new ArrayList<>();
    }

    public static <T> PageResult<T> empty(PageParam pageParam) {
        return new PageResult<>(0L, pageParam);
    }

}
