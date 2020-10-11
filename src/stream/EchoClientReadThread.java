package src.stream;

import java.io.BufferedReader;
import java.io.IOException;

public class EchoClientReadThread extends Thread {
    BufferedReader socIn;

    public EchoClientReadThread(BufferedReader socIn){
        this.socIn = socIn;
    }

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
