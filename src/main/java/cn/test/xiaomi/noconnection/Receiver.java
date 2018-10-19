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
public class Receiver {

    public static void main(String[] args) {
        try {
            InetAddress receiverHost = InetAddress.getByName("172.16.0.116");
            int myPort = 1234;// 本进程端口
            int receiverPort = 9898;// 接收进程的端口
            String message = "Hi Sender";
            MyDatagramSocket mySocket = new MyDatagramSocket(myPort);
            System.out.println(mySocket.receiveMessage());
            mySocket.sendMessage(receiverHost, receiverPort, message);
            mySocket.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

