package cn.xk.xcode.utils;

import cn.xk.xcode.config.MonitorClientProperties;
import cn.xk.xcode.core.monitor.MonitorClientInstance;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.exception.core.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;

import java.net.*;
import java.util.Enumeration;

/**
 * @Author xuk
 * @Date 2024/11/14 10:06
 * @Version 1.0.0
 * @Description IpUtil
 **/
@Slf4j
public class MonitorClientUtil {

    private MonitorClientUtil() {
    }

    private static final InetUtils inetUtils = new InetUtils(new InetUtilsProperties());
    public static final InetIPv6Utils iNetIPv6Utils = new InetIPv6Utils(new InetUtilsProperties());
    private static final ErrorCode IP_TYPE_NOT_VALID = new IntErrorCode(1300_0_500, "ip类型不合法");
    private static final ErrorCode NET_INTERFACE_ERROR = new IntErrorCode(1300_0_501, "cannot find available ip from network interface {}, 错误信息:{}");
    private static final ErrorCode NET_INTERFACE_NOT_FOUND = new IntErrorCode(1300_0_502, "no such interface {}");

    public static MonitorClientInstance bulidMonitorClientInstance(MonitorClientProperties monitorClientProperties) {
        MonitorClientInstance monitorClientInstance = new MonitorClientInstance();
        if (StringUtils.isEmpty(monitorClientProperties.getIp())) {
            if (StringUtils.isEmpty(monitorClientProperties.getNetworkInterface())) {
                if (monitorClientProperties.getIpType() == null) {
                    monitorClientInstance.setIp(inetUtils.findFirstNonLoopbackHostInfo().getIpAddress());
                    String ipv6Addr = iNetIPv6Utils.findIPv6Address();
                    if (ipv6Addr != null) {
                        monitorClientInstance.setIpv6(ipv6Addr);
                    }
                } else if (monitorClientProperties.getIpType().equals(MonitorClientProperties.IpType.IP_V4)) {
                    monitorClientInstance.setIp(inetUtils.findFirstNonLoopbackHostInfo().getIpAddress());
                } else {
                    if (!monitorClientProperties.getIpType().equals(MonitorClientProperties.IpType.IP_V6)) {
                        ExceptionUtil.castServerException(IP_TYPE_NOT_VALID);
                    }
                    String ip = iNetIPv6Utils.findIPv6Address();
                    if (StringUtils.isEmpty(ip)) {
                        log.warn("There is no available IPv6 found. This Application will automatically find IPv4.");
                        monitorClientInstance.setIp(inetUtils.findFirstNonLoopbackHostInfo().getIpAddress());
                        monitorClientInstance.setIpv4(inetUtils.findFirstNonLoopbackHostInfo().getIpAddress());
                    } else {
                        monitorClientInstance.setIpv6(ip);
                        monitorClientInstance.setIp(ip);
                    }
                }
            } else {
                NetworkInterface netInterface = null;
                try {
                    netInterface = NetworkInterface.getByName(monitorClientProperties.getNetworkInterface());
                } catch (SocketException e) {
                    ExceptionUtil.castServerException(NET_INTERFACE_ERROR, monitorClientProperties.getNetworkInterface(), e.getMessage());
                }
                if (null == netInterface) {
                    ExceptionUtil.castServerException(NET_INTERFACE_NOT_FOUND, monitorClientProperties.getNetworkInterface());
                }

                Enumeration<InetAddress> inetAddress = netInterface.getInetAddresses();

                while (inetAddress.hasMoreElements()) {
                    InetAddress currentAddress = inetAddress.nextElement();
                    if (currentAddress instanceof Inet4Address || currentAddress instanceof Inet6Address && !currentAddress.isLoopbackAddress()) {
                        monitorClientInstance.setIp(currentAddress.getHostAddress());
                        break;
                    }
                }

                if (StringUtils.isEmpty(monitorClientInstance.getIp())) {
                    ExceptionUtil.castServerException(NET_INTERFACE_ERROR, monitorClientProperties.getNetworkInterface(), null);
                }
            }
        } else {
            monitorClientInstance.setIp(monitorClientProperties.getIp());
        }
        return monitorClientInstance;
    }
}
