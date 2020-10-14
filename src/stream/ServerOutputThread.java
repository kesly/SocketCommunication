package stream;

import javafx.util.Pair;

import java.io.PrintStream;

public class ServerOutputThread extends Thread {

    private int currentIndex = 0;

    ServerOutputThread() {
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            while (true) {
                int size = EchoServerMultiThreaded.getMessages().size();
                for (int j = 0; j < EchoServerMultiThreaded.getClientsOutput().size(); j++) {
                    Pair<Boolean, PrintStream> pairSocOut = EchoServerMultiThreaded.getClientsOutput().get(j);
                    if (pairSocOut.getKey()) { // new client

                        for (Pair message : EchoServerMultiThreaded.getMessages()) {
                            pairSocOut.getValue().println(message.getKey() + " : " + message.getValue());
                        }
                        EchoServerMultiThreaded.setOldClient(j, pairSocOut.getValue());
                    } else {
                        if (size > this.currentIndex) {
                            System.out.println("entree");
                            for (int i = this.currentIndex; i < size; i++) {
                                Pair message = EchoServerMultiThreaded.getMessages().get(i);
                                pairSocOut.getValue().println(message.getKey() + " : " + message.getValue());
                            }
                        }
                    }
                }
                this.currentIndex = size;
            }

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
