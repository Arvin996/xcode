package cn.xk.xcode.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/6/10 9:30
 * @Version 1.0.0
 * @Description ThirdLoginType
 **/
@AllArgsConstructor
@Getter
public enum ThirdLoginType {

    FEISHU("feishu"),
    DINGDING("dingding"),
    GITEE("gitee"),
    GITHUB("github"),
    ALIPAY("alipay"),
    QQ("qq"),
    GOOGLE("google");

    private final String type;
}
