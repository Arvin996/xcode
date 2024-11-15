package cn.xk.xcode.core.monitor.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/11/15 9:18
 * @Version 1.0.0
 * @Description ServiceDetailReqDto
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ServiceDetailReqDto {

    private String applicationName;
}
