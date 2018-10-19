package cn.test.xiaomi.groupsend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Rollini on 2018/10/19.
 */
public class SendUdp {
    public static void main(String[] args) throws IOException
    {
        MulticastSocket ms=null;
        DatagramPacket dataPacket = null;
        ms = new MulticastSocket();
        ms.setTimeToLive(32);
        //将本机的IP（这里可以写动态获取的IP）地址放到数据包里，其实server端接收到数据包后也能获取到发包方的IP的
        String testString = "{\n" +
                "   \"cmd\":\"whois\"\n" +
                "}";
        byte[] data = testString.getBytes();
//        InetAddress address = InetAddress.getByName("239.0.0.255");
        InetAddress address = InetAddress.getByName("224.0.0.50");
//        dataPacket = new DatagramPacket(data, data.length, address,8899);
        dataPacket = new DatagramPacket(data, data.length, address,9898);
        ms.send(dataPacket);
        ms.close();
    }
}
