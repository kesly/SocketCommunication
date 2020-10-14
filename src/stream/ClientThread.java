/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package src.stream;
import java.io.*;
import java.net.*;

public class ClientThread
	extends Thread {
	
	private Socket clientSocket;
	private String clientNickname;
	
	ClientThread(Socket s) {
		this.clientSocket = s;
		this.clientNickname = clientNickname;
	}

 	/**
  	* receives a request from client then sends an echo to the client
  	* @param clientSocket the client socket
  	**/
	public void run() {
		try {
    		BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());

			this.clientNickname = socIn.readLine();


			while (true) {
    		  	String line = socIn.readLine();
				EchoServerMultiThreaded.addMessage(clientNickname, line);
				//socOut.println(EchoServerMultiThreaded.getMessage());
    		}
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
	}


  }

  
