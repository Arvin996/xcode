package cn.xk.xcode.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/8/8 14:30
 * @Version 1.0
 * @Description MemberUsersQueryDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "多用户信息查询dto")
public class MemberUserListQueryDto
{
    @Schema(description = "用户id")
    private Collection<String> ids;

    @Schema(description = "用户昵称")
    private String nickname;

    @JsonIgnore
    private boolean isValid(){
        return StringUtils.hasLength(nickname) || (Objects.nonNull(ids) && !ids.isEmpty());
    }
}
