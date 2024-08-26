package cn.xk.xcode.entity.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/8/4 20:23
 * @Version 1.0
 * @Description MemberTagAddDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增会员tag")
public class MemberTagAddDto {

    @NotBlank(message = "标签名不能为空")
    @Schema(description = "标签名")
    private String name;
}
