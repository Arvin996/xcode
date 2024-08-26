package cn.xk.xcode.entity.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/8/4 20:23
 * @Version 1.0
 * @Description MemberTagUpdateDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员tag更新")
public class MemberTagUpdateDto extends MemberTagBaseDto{

    @NotBlank(message = "更新tag名称不能为空")
    @Schema(description = "更新tag名称")
    private String name;
}
