/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

public class ClientThread
	extends Thread {
	
	private Socket clientSocket;
	private String inputClient;
	
	ClientThread(Socket s) {
		this.clientSocket = s;
	}

 	/**
  	* receives a request from client then sends an echo to the client
	 **/
	public void run() {
		try {
    		BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		while (true) {
    		  	String line = socIn.readLine();
    		  	inputClient = line;
				EchoServerMultiThreaded.addMessage(line);
				//socOut.println(EchoServerMultiThreaded.getMessage());
    		}
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
	}

	public String getInput(){
		return inputClient;
	}
  
  }

  
