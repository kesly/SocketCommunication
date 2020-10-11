/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package src.stream;

import java.io.*;
import java.net.*;

public class EchoClient {

    private static String nickName="";
  /**
  *  main method
  *  accepts a connection, receives a message from client then sends an echo to the client
  **/
  public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;
        OutputStream stdOut = null;

        if (args.length != 2) {
          System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
          System.exit(1);
        }


        try {
      	    // creation socket ==> connexion
      	    echoSocket = new Socket(args[0],new Integer(args[1]).intValue());
            socIn = new BufferedReader(
                              new InputStreamReader(echoSocket.getInputStream()));
            socOut= new PrintStream(echoSocket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            stdOut = new DataOutputStream(echoSocket.getOutputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ args[0]);
            System.exit(1);
        }

        while( nickName.equals("") ){
            System.out.print("Saisir votre pseudo : ");
            nickName=stdIn.readLine();
            socOut.println(nickName);
        }


        String line;
        EchoClientReadThread ecrt = new EchoClientReadThread(socIn);
        ecrt.start();
        while (true) {
        	line=stdIn.readLine();
        	if (line.equals(".")) break;
        	socOut.println(line);
        	//System.out.println("echo: " + socIn.readLine());
        }
        socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
    }

    public static String getNickName() {
        return nickName;
    }
}


