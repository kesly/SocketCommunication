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

public class EchoServerMultiThreaded  {
  
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
 	private static ArrayList<Pair<String, String>> messages = new ArrayList<>();
 	private static ArrayList<Pair<Boolean, PrintStream> > clientsOutput = new ArrayList<>();
	private static int index=0;
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
				BufferedReader socIn = null;
				socIn = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				String nickname = socIn.readLine();
				System.out.println("Connexion from:" + clientSocket.getInetAddress()+", Port: "+clientSocket.getPort()+", Name : "+nickname);

				ClientThread ct = new ClientThread(clientSocket, nickname);
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

	public static synchronized ArrayList<Pair<String, String>> getMessages() {
		return messages;
	}

	public static synchronized void addMessage(String nickName, String message) {
 		index++;
 		System.out.print("index="+index);
		EchoServerMultiThreaded.messages.add(new Pair<>(nickName, message));
	}
}

  
