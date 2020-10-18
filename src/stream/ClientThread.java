package stream;

import java.io.*;
import java.net.*;

/**
 * This class provide a Thread that been launched by EchoServerMultiThreaded for every client
 * This class is used to send read client message and send it to the server
 */
public class ClientThread extends Thread {


    /**
     * Client socket
     */
    private Socket clientSocket;

    /**
     * client nickname enter by client
     */
    private String clientNickname;

    ClientThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * Read client message and it to the server
     **/
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            this.clientNickname = socIn.readLine();

            while (true) {
                String line = socIn.readLine();
                EchoServerMultiThreaded.addMessage(clientNickname, line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}

  
