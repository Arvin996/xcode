package cn.xk.xcode.core.monitor.server.entity;

import cn.xk.xcode.core.monitor.client.MonitorClientInstance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/15 9:20
 * @Version 1.0.0
 * @Description ServiceInstanceDetailsVo
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ServiceInstanceDetailsVo {

    private String applicationName;

    private List<MonitorClientInstance> monitorClientInstances;
}
