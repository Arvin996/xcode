package cn.xk.xcode.entity.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/8/4 20:23
 * @Version 1.0
 * @Description MemberTagBaseDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员tag基础对象")
public class MemberTagBaseDto {

    @NotNull(message = "标签id不能为空")
    @Schema(description = "标签id")
    private Integer id;
}
