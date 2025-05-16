package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/5/13 10:38
 * @Version 1.0.0
 * @Description QueryUserDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryUserDto", description = "User查询实体类")
public class QueryUserDto extends PageParam {

    @Schema(description = "username 登录用户名")
    private String username;

    @Schema(description = "nickname 用户昵称")
    private String nickname;
}
