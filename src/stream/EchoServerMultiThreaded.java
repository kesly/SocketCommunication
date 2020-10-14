/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

/**
 * class EchoServerMultiThreaded
 * Server multiThreaded class with launch a client thread with every client to communicate with him
 */
public class EchoServerMultiThreaded {

    private static ArrayList<Pair<String, String>> messages = new ArrayList<>();
    private static ArrayList<Pair<Boolean, PrintStream>> clientsOutput = new ArrayList<>();
    private static int index = 0;

    /**
     * main method
     **/
    public static void main(String args[]) {

        ServerSocket listenSocket;

        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            ServerOutputThread cot = new ServerOutputThread();
            cot.start();
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress() + ", Port: " + clientSocket.getPort());
                ClientThread ct = new ClientThread(clientSocket);
                clientsOutput.add(new Pair<>(true, new PrintStream(clientSocket.getOutputStream())));
                ct.start();

            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

    /**
     * getClientsOutput
     *
     * @return ArrayList of Pair of boolean (client is new or not) and client output stream
     */
    public static ArrayList<Pair<Boolean, PrintStream>> getClientsOutput() {
        return clientsOutput;
    }

    /**
     * @param index        of current
     * @param clientStream
     */
    public static void setOldClient(int index, PrintStream clientStream) {
        clientsOutput.set(index, new Pair<>(false, clientStream));
    }

    public static synchronized ArrayList<Pair<String, String>> getMessages() {
        return messages;
    }

    public static synchronized void addMessage(String nickName, String message) {
        index++;
        System.out.print("index=" + index);
        EchoServerMultiThreaded.messages.add(new Pair<>(nickName, message));
    }
}

  
