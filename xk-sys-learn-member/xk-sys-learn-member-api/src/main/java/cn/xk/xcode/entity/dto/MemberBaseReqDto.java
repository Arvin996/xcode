package cn.xk.xcode.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/8/6 15:57
 * @Version 1.0
 * @Description MemberLevelReqDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户基础dto")
public class MemberBaseReqDto {
    @NotBlank(message = "用户id不能为空")
    @Schema(description = "用户id")
    private String userId;
}
