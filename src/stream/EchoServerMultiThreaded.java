/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package src.stream;

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
 	private static ArrayList<PrintStream> clientsOutput = new ArrayList<>();

 	public static void main(String args[]){

        ServerSocket listenSocket;
        
		if (args.length != 1) {
			  System.out.println("Usage: java EchoServer <EchoServer port>");
			  System.exit(1);
		}
		try {
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
			System.out.println("Server ready...");
			ClientOutputThread cot = new ClientOutputThread();
			cot.start();
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connexion from:" + clientSocket.getInetAddress()+", Port: "+clientSocket.getPort());
				ClientThread ct = new ClientThread(clientSocket);
				clientsOutput.add(new PrintStream(clientSocket.getOutputStream()));
				ct.start();

			}
			} catch (Exception e) {
				System.err.println("Error in EchoServer:" + e);
			}
 	}

	public static ArrayList<PrintStream> getClientsOutput() {
		return clientsOutput;
	}

	public static void setClientsOutput(ArrayList<PrintStream> clientsOutput) {
		EchoServerMultiThreaded.clientsOutput = clientsOutput;
	}

	public static synchronized ArrayList<String> getMessages() {
		return messages;
	}

	public static synchronized void addMessage(String message) {
		EchoServerMultiThreaded.messages.add(message);
	}
}

  
