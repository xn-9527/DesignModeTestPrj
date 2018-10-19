package cn.test.xiaomi.noconnection;

/**
 * Created by xiaoni on 2018/10/19.
 */
import cn.test.xiaomi.MyDatagramSocket;

import java.net.*;

/**
 * 采用无连接的方式实现进程间通信
 * @author michael
 *
 */
public class Sender {

    public static void main(String[] args) {
        try {
            InetAddress receiverHost = InetAddress.getByName("172.16.0.116");
            int myPort = 5689;
            /*int receiverPort = 4321;
            String message = "{\n" +
                    "   \"cmd\":\"whois\"\n" +
                    "}";*/
            /*int receiverPort = 9898;
            //7811dcf782bf
            String message = "{\"cmd\":\"write\",\"model\":\"gateway\",\"sid\":\"7811dcf782bf\",\"short_id\":0,\"key\":\"8\",\"token\":\"3C8452CE435F4433\",\"data\":\"{\\\"rgb\\\":4278255360}\" }";
            */
            int receiverPort = 9898;
            //7811dcf782bf
            String message = "{\n" +
                    "   \"cmd\":\"discovery\"\n" +
                    "}";

            MyDatagramSocket mySocket = new MyDatagramSocket(myPort);
            mySocket.sendMessage(receiverHost, receiverPort, message);
            System.out.println(mySocket.receiveMessage());
            mySocket.close();
        } catch (Exception ex) {

        }
    }
}

