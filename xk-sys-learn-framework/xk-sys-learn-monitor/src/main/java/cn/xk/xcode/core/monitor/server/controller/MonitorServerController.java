package cn.xk.xcode.core.monitor.server.controller;

import cn.xk.xcode.core.monitor.client.MonitorClientInstance;
import cn.xk.xcode.core.monitor.server.context.MonitorClientInstanceContext;
import cn.xk.xcode.core.monitor.server.context.MonitorClientInstanceCounter;
import cn.xk.xcode.core.monitor.server.entity.ServiceDetailReqDto;
import cn.xk.xcode.core.monitor.server.entity.ServiceInstanceDetailsVo;
import cn.xk.xcode.core.monitor.server.entity.ServiceInstanceIndexVo;
import cn.xk.xcode.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    @PostMapping("/index")
    public CommonResult<List<ServiceInstanceIndexVo>> getIndexData(){
        List<ServiceInstanceIndexVo> list = new ArrayList<>();
        ConcurrentHashMap<String, Integer> counter = MonitorClientInstanceCounter.getInstanceCounter();
        ConcurrentHashMap<String, Map<String, MonitorClientInstance>> instanceMap = MonitorClientInstanceContext.getInstanceMap();
        counter.forEach((k, v) -> {
            Map<String, MonitorClientInstance> map = instanceMap.get(k);
            ServiceInstanceIndexVo serviceInstanceIndexVo = new ServiceInstanceIndexVo();
            serviceInstanceIndexVo.setApplicationName(k);
            serviceInstanceIndexVo.setInstanceCount(v);
            int healthCount = (int) map.values().stream().filter(mv -> "UP".equals(mv.getStatus())).count();
            serviceInstanceIndexVo.setHealthyInstanceCount(healthCount);
            serviceInstanceIndexVo.setUnhealthyInstanceCount(v - healthCount);

        });
        return CommonResult.success(list);
    }

    @PostMapping("/getDetails")
    public CommonResult<ServiceInstanceDetailsVo> getDetails(@RequestBody ServiceDetailReqDto serviceDetailReqDto){
        String applicationName = serviceDetailReqDto.getApplicationName();
        ServiceInstanceDetailsVo serviceInstanceDetailsVo = new ServiceInstanceDetailsVo();
        Map<String, MonitorClientInstance> map = MonitorClientInstanceContext.getInstanceMap().get(applicationName);
        serviceInstanceDetailsVo.setApplicationName(applicationName);
        serviceInstanceDetailsVo.setMonitorClientInstances(new ArrayList<>(map.values()));
        return CommonResult.success(serviceInstanceDetailsVo);
    }

}
