package stream;

import javafx.util.Pair;

import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

/**
 * Server multiThreaded class that provide a group chat
 * <p>
 * Client can come and chat with others clients that present in the chat, all message are preserve and so new client have access to older message
 * <p>
 * For each client, we launch a Thread to manage him
 */
public class EchoServerMultiThreaded {

    /**
     * The list of all messages sended by all clients
     */
    private static ArrayList<Pair<String, String>> messages = new ArrayList<>();

    /**
     * ArrayList of Pair of boolean (client is new or not) and client output stream
     */
    private static ArrayList<Pair<Boolean, PrintStream>> clientsOutput = new ArrayList<>();

    /**
     * Accept client connection and launch Thread to manage him
     *
     * @param args list of arguments, the first parameter is the listening port of the server
     **/
    public static void main(String[] args) {

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
     * Access to the clientsOutput
     *
     * @return clientsOutput array
     */
    public static ArrayList<Pair<Boolean, PrintStream>> getClientsOutput() {
        return clientsOutput;
    }

    /**
     * Set client status to old
     *
     * @param index        client index in the clientsOutput array
     * @param clientStream client to set to old
     */
    public static void setOldClient(int index, PrintStream clientStream) {
        clientsOutput.set(index, new Pair<>(false, clientStream));
    }

    /**
     * Access to the messages array
     *
     * @return messages array
     */
    public static synchronized ArrayList<Pair<String, String>> getMessages() {
        return messages;
    }

    /**
     * Add client message to messages array
     *
     * @param nickName client that send the message
     * @param message  the message sent by the client
     */
    public static synchronized void addMessage(String nickName, String message) {
        EchoServerMultiThreaded.messages.add(new Pair<>(nickName, message));
    }
}

  
