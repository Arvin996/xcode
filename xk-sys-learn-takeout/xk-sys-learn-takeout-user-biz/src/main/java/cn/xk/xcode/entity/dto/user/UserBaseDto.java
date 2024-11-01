package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/30 11:32
 * @Version 1.0.0
 * @Description UserBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "用户base dto")
public class UserBaseDto {

    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long id;
}
