package cn.xk.xcode.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/11/15 8:45
 * @Version 1.0.0
 * @Description ServiceInstanceIndexVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ServiceInstanceIndexVo {

    // 服务名
    private String applicationName;

    // 实例个数
    private Integer instanceCount;

    // 健康实例个数
    private Integer healthyInstanceCount;

    // 非健康实例个数
    private Integer unhealthyInstanceCount;
}
