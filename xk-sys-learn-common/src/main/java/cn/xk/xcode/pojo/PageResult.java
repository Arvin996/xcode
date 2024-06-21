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
public class PageResult<T> implements java.io.Serializable
{

    private static final long serialVersionUID = 1L;

    @Schema(description = "总数", requiredMode = Schema.RequiredMode.REQUIRED)
    private int total;

    @Schema(description = "数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<T> data;

    public PageResult(int total){
        this.total = total;
        this.data = new ArrayList<>();
    }

}
