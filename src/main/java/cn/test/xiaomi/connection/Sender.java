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
public class Sender {

    public static void main(String[] args) {
        try {
            InetAddress receiverHost = InetAddress.getByName("127.0.0.1");
            int receiverPort = 4568;
            int myPort = 1234;
            String message = "Hello Receiver";
            MyDatagramSocket mySocket = new MyDatagramSocket(myPort);
            mySocket.connect(receiverHost, receiverPort);
            mySocket.sendMessage(receiverHost, receiverPort, message);
            System.out.println(mySocket.receiveMessage());
            mySocket.disconnect();
            mySocket.close();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

