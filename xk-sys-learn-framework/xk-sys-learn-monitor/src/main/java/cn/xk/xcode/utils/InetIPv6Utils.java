package cn.xk.xcode.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

public class InetIPv6Utils {
    private static final Log log = LogFactory.getLog(InetIPv6Utils.class);
    private final InetUtilsProperties properties;

    public InetIPv6Utils(final InetUtilsProperties properties) {
        this.properties = properties;
    }

    private InetUtils.HostInfo findFirstValidHostInfo() {
        InetAddress address = this.findFirstValidIPv6Address();
        return address != null ? this.getHostInfo(address) : null;
    }

    private InetAddress findFirstValidIPv6Address() {
        InetAddress address = null;

        try {
            Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();

            while(nics.hasMoreElements()) {
                NetworkInterface ifc = (NetworkInterface)nics.nextElement();
                if (ifc.isUp() || !ifc.isVirtual() || !ifc.isLoopback()) {
                    if (address != null) {
                        break;
                    }

                    if (!this.ignoreInterface(ifc.getDisplayName())) {
                        Enumeration<InetAddress> addrs = ifc.getInetAddresses();

                        while(addrs.hasMoreElements()) {
                            InetAddress inetAddress = (InetAddress)addrs.nextElement();
                            if (inetAddress instanceof Inet6Address && !inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && !inetAddress.isAnyLocalAddress() && !inetAddress.isSiteLocalAddress() && !this.isUniqueLocalAddress(inetAddress) && this.isPreferredAddress(inetAddress)) {
                                log.trace("Found non-loopback interface: " + ifc.getDisplayName());
                                address = inetAddress;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Cannot get first non-loopback address", e);
        }

        return address;
    }

    public String findIPv6Address() {
        InetUtils.HostInfo hostInfo = this.findFirstValidHostInfo();
        return hostInfo != null ? this.normalizeIPv6(hostInfo.getIpAddress()) : null;
    }

    public String normalizeIPv6(String ip) {
        int idx = ip.indexOf("%");
        return idx != -1 ? "[" + ip.substring(0, idx) + "]" : "[" + ip + "]";
    }

    private boolean isPreferredAddress(InetAddress address) {
        if (this.properties.isUseOnlySiteLocalInterfaces()) {
            boolean siteLocalAddress = address.isSiteLocalAddress();
            if (!siteLocalAddress) {
                log.trace("Ignoring address" + address.getHostAddress());
            }

            return siteLocalAddress;
        } else {
            List<String> preferredNetworks = this.properties.getPreferredNetworks();
            if (preferredNetworks.isEmpty()) {
                return true;
            } else {
                for(String regex : preferredNetworks) {
                    String hostAddress = address.getHostAddress();
                    if (hostAddress.matches(regex) || hostAddress.startsWith(regex)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    boolean ignoreInterface(String interfaceName) {
        for(String regex : this.properties.getIgnoredInterfaces()) {
            if (interfaceName.matches(regex)) {
                return true;
            }
        }

        return false;
    }

    private InetUtils.HostInfo getHostInfo(final InetAddress address) {
        InetUtils.HostInfo hostInfo = new InetUtils.HostInfo();
        String hostName = address.getHostName();
        if (hostName == null) {
            hostName = "localhost";
        }

        hostInfo.setHostname(hostName);
        if (StringUtils.isNotEmpty(address.getHostAddress())) {
            hostInfo.setIpAddress(address.getHostAddress());
        } else {
            hostInfo.setIpAddress("");
        }

        return hostInfo;
    }

    private boolean isUniqueLocalAddress(InetAddress inetAddress) {
        byte[] ip = inetAddress.getAddress();
        return (ip[0] & 255) == 253;
    }
}