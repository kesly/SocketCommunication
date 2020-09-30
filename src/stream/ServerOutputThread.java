package src.stream;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerOutputThread extends Thread {

    //private Socket clientSocket;
    private int currentIndex = 0;

    ServerOutputThread() {
        //this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            while (true) {

                //System.out.println("Size : "+EchoServerMultiThreaded.getMessages().size()+"Index : "+this.currentIndex);
                //for (Pair<Boolean, PrintStream> pairSocOut : EchoServerMultiThreaded.getClientsOutput()) {
                for (int j = 0; j<EchoServerMultiThreaded.getClientsOutput().size(); j++) {
                    Pair<Boolean, PrintStream> pairSocOut = EchoServerMultiThreaded.getClientsOutput().get(j);
                    if(pairSocOut.getKey()){
                        for (int i = 0; i<EchoServerMultiThreaded.getMessages().size(); i++) {
                            pairSocOut.getValue().println(EchoServerMultiThreaded.getMessages().get(i));
                            EchoServerMultiThreaded.setOldClient(j, pairSocOut.getValue());
                        }
                    }else {
                        if (EchoServerMultiThreaded.getMessages().size() > this.currentIndex) {
                            for (int i = this.currentIndex; i < EchoServerMultiThreaded.getMessages().size(); i++) {
                                pairSocOut.getValue().println(EchoServerMultiThreaded.getMessages().get(i));
                            }
                        }
                    }
                }
                this.currentIndex = EchoServerMultiThreaded.getMessages().size();
            }

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
