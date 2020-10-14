package src.stream;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ClientMulticast {

    private static boolean firstClient = true;
    private static String groupName = "";

    public static void main(String [] args) {

        Scanner sc = new Scanner(System.in);

        /*if(firstClient){
            System.out.print("Saisir le nom du group : ");
            groupName = sc.nextLine();
        }else {
            firstClient = false;
        }*/


        System.out.print("Saisir votre pseudo : ");
        String nickname = sc.nextLine();

        try {
            // Group IP address
            InetAddress groupAddr = InetAddress.getByName("228.5.6.7");
            int groupPort = 6789;

            // Create a multicast socket
            MulticastSocket s = new MulticastSocket(groupPort);

            // Join the group
            s.joinGroup(groupAddr);
            System.out.println("Bienvenu dans le groupe "+nickname);
            System.out.println("Pour quitter le groupe, tapez \"q\"");
            // Build a datagram packet for a message
            // to send to the group
            //String msg = "Hello";
            DatagramPacket msg = null;

            String line;

            ClientMulticastOutputThread clmot = new ClientMulticastOutputThread(s);
            clmot.start();
            while (true) {

                line=sc.nextLine();
                String message = nickname+" : "+line;
                if (line.equals("q") ) {
                    message=nickname+" : Je quitte le groupe, au revoir !";
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
        } catch (Exception e){
            System.out.println("Error : " + e);
        }
        // Send a multicast message to the group
    }

}
