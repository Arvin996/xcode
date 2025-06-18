package cn.xk.xcode.core;

import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.third.AbstractThirdLoginHandler;
import cn.xk.xcode.handler.third.ThirdLoginHandlerHolder;
import cn.xk.xcode.handler.third.ThirdLoginVo;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.utils.object.ObjectUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.THIRD_LOGIN_TYPE_HANDLER_NOT_EXISTS;

/**
 * @Author xuk
 * @Date 2025/6/10 9:31
 * @Version 1.0.0
 * @Description ThirdLoginController
 **/
@RestController
@RequestMapping("/auth/third")
@Tag(name = "第三方登录")
@Slf4j
public class ThirdLoginController {

    @Resource
    private ThirdLoginHandlerHolder thirdLoginHandlerHolder;

    @GetMapping("/render/{thirdLoginType}")
    @Operation(summary = "渲染第三方登录页面")
    public void renderAuth(@PathVariable("thirdLoginType") String thirdLoginType, HttpServletResponse response) {
        AbstractThirdLoginHandler thirdLoginHandler = thirdLoginHandlerHolder.routeThirdLoginHandler(thirdLoginType);
        if (ObjectUtil.isNull(thirdLoginHandler)) {
            ExceptionUtil.castServerException(THIRD_LOGIN_TYPE_HANDLER_NOT_EXISTS, thirdLoginType);
        }
        thirdLoginHandler.renderAuth(response);
    }

    @GetMapping("/callback/{thirdLoginType}")
    @Operation(summary = "第三方登录回调")
    public void login(@PathVariable("thirdLoginType") String thirdLoginType, AuthCallback authCallback, HttpServletResponse response) throws IOException {
        AbstractThirdLoginHandler thirdLoginHandler = thirdLoginHandlerHolder.routeThirdLoginHandler(thirdLoginType);
        if (ObjectUtil.isNull(thirdLoginHandler)) {
            ExceptionUtil.castServerException(THIRD_LOGIN_TYPE_HANDLER_NOT_EXISTS, thirdLoginType);
        }
        ThirdLoginVo thirdLoginVo = thirdLoginHandler.login(authCallback);
        response.sendRedirect("http://localhost:8000/user/login" + genUrlParams(thirdLoginVo));
    }

    private String genUrlParams(ThirdLoginVo thirdLoginVo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?firstLogin=").append(thirdLoginVo.getFirstLogin());
        stringBuilder.append("&thirdLoginType=").append(thirdLoginVo.getThirdLoginType());
        stringBuilder.append("&unionId=").append(thirdLoginVo.getUnionId());
        stringBuilder.append("&requireCaptcha=").append(false);
        if (ObjectUtil.isNotNull(thirdLoginVo.getLoginVO())) {
            stringBuilder.append("&userInfo=").append(thirdLoginVo.getLoginVO().getUserInfo());
            stringBuilder.append("&accessToken=").append(thirdLoginVo.getLoginVO().getAccessToken());
            stringBuilder.append("&expireIn=").append(thirdLoginVo.getLoginVO().getExpireIn());
        }
        return stringBuilder.toString();
    }
}
