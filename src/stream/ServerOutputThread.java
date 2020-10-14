package src.stream;

import javafx.util.Pair;
import java.io.PrintStream;

public class ServerOutputThread extends Thread {

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

                for (int j = 0; j<EchoServerMultiThreaded.getClientsOutput().size(); j++) {
                    Pair<Boolean, PrintStream> pairSocOut = EchoServerMultiThreaded.getClientsOutput().get(j);
                    if(pairSocOut.getKey()){
                        for (int i = 0; i<EchoServerMultiThreaded.getMessages().size(); i++) {
                            pairSocOut.getValue().println(EchoServerMultiThreaded.getMessages().get(i).getKey()+" : "+EchoServerMultiThreaded.getMessages().get(i).getValue());
                            EchoServerMultiThreaded.setOldClient(j, pairSocOut.getValue());
                        }
                    }else {
                        System.out.println("entree 0000 : "+this.currentIndex);
                        if (EchoServerMultiThreaded.getMessages().size() > this.currentIndex) {
                            System.out.println("entree");
                            for (int i = this.currentIndex; i < EchoServerMultiThreaded.getMessages().size(); i++) {
                                pairSocOut.getValue().println(EchoServerMultiThreaded.getMessages().get(i).getKey()+" : "+EchoServerMultiThreaded.getMessages().get(i).getValue());
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
