/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package src.stream;

import javafx.util.Pair;

import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;

public class EchoServerMultiThreaded  {
  
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
 	private static ArrayList<String> messages = new ArrayList<>();
 	private static ArrayList<Pair<Boolean, PrintStream> > clientsOutput = new ArrayList<>();

 	public static void main(String args[]){

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
				System.out.println("Connexion from:" + clientSocket.getInetAddress()+", Port: "+clientSocket.getPort());
				ClientThread ct = new ClientThread(clientSocket);
				clientsOutput.add(new Pair<>(true, new PrintStream(clientSocket.getOutputStream())));
				ct.start();

			}
			} catch (Exception e) {
				System.err.println("Error in EchoServer:" + e);
			}
 	}

	public static ArrayList<Pair<Boolean, PrintStream>> getClientsOutput() {
		return clientsOutput;
	}

	public static void setOldClient(int index, PrintStream clientStream){
 		clientsOutput.set(index, new Pair<>(false, clientStream));
	}

	public static synchronized ArrayList<String> getMessages() {
		return messages;
	}

	public static synchronized void addMessage(String message) {
		EchoServerMultiThreaded.messages.add(message);
	}
}

  
