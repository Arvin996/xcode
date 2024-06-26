package cn.xk.xcode.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2024/6/26 12:56
 * @Version 1.0
 * @Description DataSourceEntity
 */
@Data
@Builder
public class DataSourceEntity
{
    private String username;

    private String password;

    private String dbIp;

    private String dbPort;

    private String dbName;

}
