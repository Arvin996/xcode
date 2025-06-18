package cn.xk.xcode.entity.dto.audit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/6/6 13:52
 * @Version 1.0.0
 * @Description AuditResultDto
 **/
@Data
@Schema(name = "AuditResultDto", description = "AuditResultDto 审核结果dto")
public class AuditResultDto {

    @Schema(description = "审核id")
    @NotNull(message = "审核id不能为空")
    private Long id;


    @Schema(description = "审核结果10 审核通过 20 审核不通过")
    @NotBlank(message = "审核结果不能为空")
    private String result;

    /**
     * 审核失败原因
     */
    @Schema(description = "审核失败原因")
    private String failMsg;
}
