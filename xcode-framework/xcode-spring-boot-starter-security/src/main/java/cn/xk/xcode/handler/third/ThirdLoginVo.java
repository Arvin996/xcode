package cn.xk.xcode.handler.third;

import cn.xk.xcode.pojo.LoginVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/6/10 10:06
 * @Version 1.0.0
 * @Description ThirdLoginVo
 **/
@Data
@Schema(name = "ThirdLoginVo", description = "第三方登录返回值")
public class ThirdLoginVo {

    @Schema(description = "是否首次登录")
    private Boolean firstLogin;

    @Schema(description = "第三方登录类型")
    private String thirdLoginType;

    @Schema(description = "第三方登录唯一标识")
    private String unionId;

    @Schema(description = "登录返回值")
    private LoginVO loginVO;
}
