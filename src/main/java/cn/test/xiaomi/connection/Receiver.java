package cn.test.xiaomi.connection;

/**
 * Created by xiaoni on 2018/10/19.
 */
import cn.test.xiaomi.MyDatagramSocket;

import java.net.*;

/**
 * 采用面向连接的方式实现进程间通信
 * @author michael
 *
 */
public class Receiver {

    public static void main(String[] args) {
        try {
            InetAddress senderHost = InetAddress.getByName("127.0.0.1");
            int senderPort = 1234;
            int myPort = 4568;
            String message = "Hi Sender";
            MyDatagramSocket mySocket = new MyDatagramSocket(myPort);
            //与对方建立连接
            mySocket.connect(senderHost, senderPort);
            System.out.println(mySocket.receiveMessage());
            mySocket.sendMessage(senderHost, senderPort, message);
            //断开连接
            mySocket.disconnect();
            mySocket.close();
        }
        catch (Exception ex) {
            System.out.println("An exception has occured: " + ex);
        }
    }
}

