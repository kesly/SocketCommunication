/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package stream;

import java.io.*;
import java.net.*;

/**
 * This class is used to create a socket communication with a server
 * It's launch a Thread (EchoClientReadThread) that read all message receive from the server and print it
 */
public class EchoClient {

    private static String nickName = "";

    /**
     * Create socket and connect to the server
     *
     * @param args list of parameters, the first parameter is the ip address of the server and the second parameter is the port
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
            echoSocket = new Socket(args[0], new Integer(args[1]).intValue());
            socIn = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            socOut = new PrintStream(echoSocket.getOutputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            stdOut = new DataOutputStream(echoSocket.getOutputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + args[0]);
            System.exit(1);
        }

        while (nickName.equals("")) {
            System.out.print("Saisir votre pseudo : ");
            nickName = stdIn.readLine();
            socOut.println(nickName);
        }
        System.out.println("Bienvenue " + nickName + " dans le groupe, vous pouvez commencer tchater !");

        String line;
        EchoClientReadThread ecrt = new EchoClientReadThread(socIn);
        ecrt.start();
        while (true) {
            line = stdIn.readLine();
            if (line.equals(".")) break;
            socOut.println(line);
            //System.out.println("echo: " + socIn.readLine());
        }
        socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
    }
}


