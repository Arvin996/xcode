package cn.xk.xcode.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/2/5 13:52
 * @Version 1.0.0
 * @Description XxlJobGroup
 **/
public class XxlJobGroup {

    @Setter
    @Getter
    private int id;

    @Setter
    @Getter
    private String appname;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private int addressType;        // 执行器地址类型：0=自动注册、1=手动录入

    @Setter
    @Getter
    private String addressList;     // 执行器地址列表，多地址逗号分隔(手动录入)

    @Setter
    @Getter
    private Date updateTime;

    // registry list
    private List<String> registryList;  // 执行器地址列表(系统注册)

    public List<String> getRegistryList() {
        if (addressList != null && !addressList.trim().isEmpty()) {
            registryList = StrUtil.split(addressList, StrUtil.COMMA);
        }
        return registryList;
    }
}
