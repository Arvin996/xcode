package cn.xk.xcode.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @Author xuk
 * @Date 2024/8/8 14:26
 * @Version 1.0
 * @Description MemberQueryDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户信息查询dto")
public class MemberQueryDto {
    @Schema(description = "用户id")
    private String UserId;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户手机号")
    private String phone;

    @JsonIgnore
    private boolean isValid(){
        return StringUtils.hasLength(this.getUserId()) || StringUtils.hasLength(this.getEmail()) || StringUtils.hasLength(this.getEmail());
    }
}
