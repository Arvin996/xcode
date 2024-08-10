package cn.xk.xcode.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/5/27 15:51
 * @Version 1.0
 * @Description PageParam
 */
@Data
@Schema(description = "分页参数")
public class PageParam implements java.io.Serializable
{
    public static final int PAGE_NO_DEFAULT = 1;

    public static final int PAGE_SIZE_DEFAULT = 10;

    @Schema(description = "起始页", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "起始页不能为空")
    @Min(value = 1, message = "起始页不能小于1")
    private Integer pageNo = PAGE_NO_DEFAULT;

    @Schema(description = "每页大小", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小不能小于1")
    @Max(value = 100, message = "每页大小不能大于100")
    private Integer pageSize = PAGE_SIZE_DEFAULT;

}
