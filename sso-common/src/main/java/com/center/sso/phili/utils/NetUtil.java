package com.center.sso.phili.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetUtil {
    public NetUtil() {
    }

    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            Enumeration ifaces = NetworkInterface.getNetworkInterfaces();

            while(ifaces.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface)ifaces.nextElement();
                Enumeration inetAddrs = iface.getInetAddresses();

                while(inetAddrs.hasMoreElements()) {
                    InetAddress inetAddr = (InetAddress)inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            return inetAddr;
                        }

                        if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }

            if (candidateAddress != null) {
                return candidateAddress;
            } else {
                InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
                if (jdkSuppliedAddress == null) {
                    throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
                } else {
                    return jdkSuppliedAddress;
                }
            }
        } catch (Exception var5) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + var5);
            unknownHostException.initCause(var5);
            throw unknownHostException;
        }
    }

    public static void main(String[] args) {
        String hostAddress = "127.0.0.1";

        try {
            hostAddress = getLocalHostLANAddress().getHostAddress();
            System.out.println(hostAddress);
        } catch (UnknownHostException var3) {
            var3.printStackTrace();
        }

        System.out.println(hostAddress);
    }
}
