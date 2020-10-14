package stream;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientDistributed {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Clientdistributed <server host>");
        }

        try {
            if (System.getSecurityManager() == null) {
                System.setProperty("java.security.policy", "/Users/keslygassant/Documents/insa/4ifa/sequence1/ecole/reseaux/projet/SocketCommunication/src/security.policy");
                System.setSecurityManager(new SecurityManager());
            }

            String host = args[0];
            System.out.println(host);
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2021);
            BroadCast broadCast = (BroadCast) registry.lookup("Great");
            String res = broadCast.sayHello();
            System.out.println(res);
//            System.out.println(broadCast.getMessage());
        } catch (Exception e) {
            System.err.println("Error on client: " + e);
            e.printStackTrace();
        }
    }
}
