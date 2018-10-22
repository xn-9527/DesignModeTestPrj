package cn.test.xiaomi.groupsend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class LumiServer {
    private static final String TAG = "LumiServer";

    public static void main(String[] args) throws IOException {
        MulticastSocket ms = new MulticastSocket(9898);
        InetAddress address = InetAddress.getByName("224.0.0.50");
        ms.joinGroup(address);
        new Thread(new Monitor(ms)).start();
    }
}

class Monitor implements Runnable {
    private MulticastSocket ms;

    Monitor(MulticastSocket ms) {
        this.ms = ms;
    }

    @Override
    public void run() {
        System.out.println("start running");
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, 1024);
        while (true) {
            System.out.println("aaa");
            try {
                ms.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("bbb");
            String msg = new String(buffer, 0, packet.getLength());
            System.out.println("get " + msg);
        }
    }
}