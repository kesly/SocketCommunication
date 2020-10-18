package stream;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * This class is a Thread that been launched by EchoClient, the Thread is used to read all message sent by the server and print it
 */
public class EchoClientReadThread extends Thread {
    BufferedReader socIn;

    public EchoClientReadThread(BufferedReader socIn){
        this.socIn = socIn;
    }

    /**
     *  Read server message and print it
     */
    public void run() {

        while (true){
            try {
                System.out.println(this.socIn.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
