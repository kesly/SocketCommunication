package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientOutputThread extends Thread {

    //private Socket clientSocket;
    private int currentIndex = 0;

    ClientOutputThread() {
        //this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            while (true) {
                if(EchoServerMultiThreaded.getMessages().size()>this.currentIndex){
                    for (int i = this.currentIndex; i<EchoServerMultiThreaded.getMessages().size(); i++) {
                        for (PrintStream socOut : EchoServerMultiThreaded.getClientsOutput()) {
                            socOut.println(EchoServerMultiThreaded.getMessages().get(i));
                        }
                    }
                    this.currentIndex = EchoServerMultiThreaded.getMessages().size();
                }
            }
            /*BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                if(EchoServerMultiThreaded.getMessages().size()>currentIndex){
                    System.out.println("Size : "+EchoServerMultiThreaded.getMessages().size());
                    for (int i = currentIndex; i<EchoServerMultiThreaded.getMessages().size(); i++) {
                        socOut.println(EchoServerMultiThreaded.getMessages().get(i));
                    }
                    currentIndex = EchoServerMultiThreaded.getMessages().size();
                }

            }*/
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
