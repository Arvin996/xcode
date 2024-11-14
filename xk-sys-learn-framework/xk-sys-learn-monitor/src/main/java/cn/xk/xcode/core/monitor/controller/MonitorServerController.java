package cn.xk.xcode.core.monitor.controller;

import cn.xk.xcode.core.monitor.MonitorClientInstance;
import cn.xk.xcode.core.monitor.MonitorClientInstanceContext;
import cn.xk.xcode.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/11/14 11:32
 * @Version 1.0.0
 * @Description MonitorServerController
 **/
@ResponseBody
@RequestMapping("/monitor")
@Slf4j
public class MonitorServerController {

    @PostMapping("/register")
    public CommonResult<Boolean> registerService(@RequestBody MonitorClientInstance monitorClientInstance) {
        MonitorClientInstanceContext.put(monitorClientInstance);
        log.info("register service:{}", monitorClientInstance);
        return CommonResult.success(true);
    }

    @PostMapping("/update")
    public CommonResult<Boolean> updateService(@RequestBody MonitorClientInstance monitorClientInstance) {
        String ip = monitorClientInstance.getIp();
        String applicationName = monitorClientInstance.getApplicationName();
        MonitorClientInstance instance = MonitorClientInstanceContext.get(applicationName, ip);
        if (Objects.isNull(instance)) {
            return registerService(monitorClientInstance);
        }
        // 更新
        MonitorClientInstanceContext.update(monitorClientInstance);
        return CommonResult.success(true);
    }
}
