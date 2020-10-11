package src.stream;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ClientMulticastOutputThread extends Thread {

    private int currentIndex = 0;
    private MulticastSocket multicastSocket;

    byte[] buf;
    DatagramPacket recv;

    ClientMulticastOutputThread(MulticastSocket ms) {
        this.multicastSocket = ms;
        buf = new byte[1000];
        recv = new  DatagramPacket(buf, buf.length);
    }

    String msg = "";
    public void run() {
        try {
            while (true) {
                multicastSocket.receive(recv);
                System.out.println(new String(recv.getData() ) );
                buf = new byte[1000];
                recv = new  DatagramPacket(buf, buf.length);
            }

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
