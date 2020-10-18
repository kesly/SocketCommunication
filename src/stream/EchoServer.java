package stream;

import java.io.*;
import java.net.*;

/**
 * Simple class that provide a socket communication with a single client
 */
public class EchoServer {

    /**
     * Read the client message and send it back the same message
     *
     * @param clientSocket the client socket
     **/
    static void doService(Socket clientSocket) {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                String line = socIn.readLine();
                socOut.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

    /**
     * Main method
     *
     * @param args -- array of parameters to launch the server, fist parameter is the port to launch
     **/
    public static void main(String[] args) {
        ServerSocket listenSocket;

        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("connexion from:" + clientSocket.getInetAddress() + ", Port: " + clientSocket.getLocalPort());
                doService(clientSocket);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

  
