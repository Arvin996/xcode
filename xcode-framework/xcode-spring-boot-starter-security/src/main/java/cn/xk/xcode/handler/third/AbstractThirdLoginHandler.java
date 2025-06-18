package cn.xk.xcode.handler.third;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.utils.object.ObjectUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2025/6/10 9:32
 * @Version 1.0.0
 * @Description AbstractThirdLoginHandler
 **/
@Data
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractThirdLoginHandler {

    protected final XcodeThirdLoginProperties xcodeThirdLoginProperties;

    public void renderAuth(HttpServletResponse response) {
        validateProperties();
        AuthRequest authRequest = getAuthRequest();
        try {
            response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
        } catch (Exception e) {
            log.error("第三方登录渲染失败, 异常信息:{}", e.getMessage());
            ExceptionUtil.castServerException(THIRD_LOGIN_RENDER_FAILED);
        }
    }

    protected abstract ThirdLoginType getThirdLoginType();

    public void validateProperties() {
        Object fieldValue = ReflectUtil.getFieldValue(xcodeThirdLoginProperties, getThirdLoginType().getType());
        if (ObjectUtil.isNull(fieldValue)) {
            ExceptionUtil.castServerException(THIRD_LOGIN_PROPERTY_ERROR, getThirdLoginType().getType(), "", "配置不存在");
        }
        Field[] fields = ReflectUtil.getFields(fieldValue.getClass());
        for (Field field : fields) {
            Object value = ReflectUtil.getFieldValue(fieldValue, field);
            if (StrUtil.isBlankIfStr(value)) {
                ExceptionUtil.castServerException(THIRD_LOGIN_PROPERTY_ERROR, getThirdLoginType().getType(), field.getName(), field.getName() + "不能为空");
            }
        }
    }

    protected abstract AuthRequest getAuthRequest();

    public ThirdLoginVo login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        AuthResponse<AuthUser> response = authRequest.login(callback);
        log.info("第三方登录成功, 返回值:{}", response.getData().getEmail());
        if (!response.ok()) {
            log.error("第三方登录失败, 异常信息:{}", response.getMsg());
            ExceptionUtil.castServerException(THIRD_LOGIN_FAILED);
        }
        return doLogin(response);
    }


    public abstract ThirdLoginVo doLogin(AuthResponse<AuthUser> response);

}
