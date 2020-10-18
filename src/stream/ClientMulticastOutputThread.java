package stream;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * This class provide a Thread that been used to receive message from multicast socket and print it
 */
public class ClientMulticastOutputThread extends Thread {

    private MulticastSocket multicastSocket;

    byte[] buf;
    DatagramPacket recv;

    ClientMulticastOutputThread(MulticastSocket ms) {
        this.multicastSocket = ms;
        this.buf = new byte[1000];
        this.recv = new DatagramPacket(buf, buf.length);
    }

    /**
     * Receive message and print it
     */
    public void run() {
        try {
            while (true) {
                multicastSocket.receive(recv);
                System.out.println(new String(recv.getData()));
                buf = new byte[1000];
                recv = new DatagramPacket(buf, buf.length);
            }

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
