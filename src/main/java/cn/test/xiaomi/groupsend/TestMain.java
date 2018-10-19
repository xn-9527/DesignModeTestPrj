package cn.test.xiaomi.groupsend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Rollini on 2018/10/19.
 */
public class TestMain {
    private static MulticastSocket ds;
//    static String multicastHost="239.0.0.255";
    static String multicastHost="224.0.0.50";
    static InetAddress receiveAddress;
    public static void main(String[] args) throws IOException
    {
        ds = new MulticastSocket(9898);
        receiveAddress=InetAddress.getByName(multicastHost);
        ds.joinGroup(receiveAddress);
        new Thread(new udpRunnable(ds)).start();
    }
}
class udpRunnable implements Runnable
{
    MulticastSocket ds;
    public udpRunnable(MulticastSocket ds)
    {
        this.ds=ds;
    }
    @Override
    public void run()
    {
        byte buf[] = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, 1024);
        while (true)
        {
            try
            {
                ds.receive(dp);
                System.out.println("receive client message : "+new String(buf, 0, dp.getLength()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}

