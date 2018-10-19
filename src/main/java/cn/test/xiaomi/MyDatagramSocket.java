package cn.test.xiaomi;

/**
 * Created by xiaoni on 2018/10/19.
 */
import java.net.*;
import java.io.*;

/**
 * 继承数据报套接字类
 * 实现发送消息和接收消息的方法
 * @author michael
 *
 */
public class MyDatagramSocket extends DatagramSocket {
    static final int MAX_LEN = 100;

    MyDatagramSocket() throws SocketException {
        super();
    }

    public MyDatagramSocket(int port) throws SocketException {
        super(port);
    }

    public void sendMessage(InetAddress receiverHost, int receiverPort,
                            String message) throws IOException {
        byte[] sendBuffer = message.getBytes();
        DatagramPacket datagram = new DatagramPacket(sendBuffer,
                sendBuffer.length, receiverHost, receiverPort);
        this.send(datagram);
    }

    public String receiveMessage() throws IOException {
        byte[] receiveBuffer = new byte[MAX_LEN];
        DatagramPacket datagram = new DatagramPacket(receiveBuffer, MAX_LEN);
        this.receive(datagram);
        String message = new String(receiveBuffer);
        return message;
    }
}
