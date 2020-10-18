package stream;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

/**
 * This class provide client connection in multicast communication
 * <p>
 * This class is useful for sending and receiving IP multicast packets
 */
public class ClientMulticast {

    /**
     * @param args array of arguments, first argument is the multicast address, and second argument is the port
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Saisir votre pseudo : ");
        String nickname = sc.nextLine();

        try {
            // Group IP address
//            InetAddress groupAddr = InetAddress.getByName("228.5.6.7");
            InetAddress groupAddr = InetAddress.getByName(args[0]);
            int groupPort = Integer.parseInt(args[1]);

            // Create a multicast socket
            MulticastSocket s = new MulticastSocket(groupPort);

            // Join the group
            s.joinGroup(groupAddr);
            System.out.println("Bienvenu dans le groupe " + nickname);
            System.out.println("Pour quitter le groupe, tapez \"q\"");


            // a datagram packet for a message

            DatagramPacket msg = null;

            String line;

            ClientMulticastOutputThread clmot = new ClientMulticastOutputThread(s);
            clmot.start();
            while (true) {

                line = sc.nextLine();
                String message = nickname + " : " + line;
                if (line.equals("q")) {
                    message = nickname + " : Je quitte le groupe, au revoir !";
                    msg = new DatagramPacket(message.getBytes(), message.length(), groupAddr, groupPort);
                    s.send(msg);
                    s.leaveGroup(groupAddr);
                    break;
                }
                msg = new DatagramPacket(message.getBytes(), message.length(), groupAddr, groupPort);
                s.send(msg);
            }
            System.out.println("Sortie");
            clmot.interrupt();
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

}
