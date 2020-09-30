package src.stream;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ClientMulticast {

    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Group IP address
            InetAddress groupAddr = InetAddress.getByName("228.5.6.7");
            int groupPort = 6789;

            // Create a multicast socket
            MulticastSocket s = new MulticastSocket(groupPort);

            // Join the group
            s.joinGroup(groupAddr);

            // Build a datagram packet for a message
            // to send to the group
            //String msg = "Hello";
            DatagramPacket msg = null;

            String line;
            ClientMulticastOutputThread clmot = new ClientMulticastOutputThread(s);
            clmot.start();
            while (true) {
                line=sc.nextLine();
                if (line.equals(".")) break;
                msg = new DatagramPacket(line.getBytes(), line.length(), groupAddr, groupPort);
                s.send(msg);

                //System.out.println("echo: " + socIn.readLine());
            }

        } catch (Exception e){
            System.out.println("Error : " + e);
        }
        // Send a multicast message to the group
    }

}
