package stream;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerDistributed {
    //-Djava.rmi.server.hostname=127.0.0.1
    public static  void main(String [] args) {
        if(System.getSecurityManager()==null) {
            System.setProperty("java.security.policy","/Users/keslygassant/Documents/insa/4ifa/sequence1/ecole/reseaux/projet/SocketCommunication/src/security.policy");
            System.setSecurityManager(new SecurityManager());
        }

        try {
            BroadCastImp broadCastImp = new BroadCastImp("Hello guys");
            BroadCast broadCast = (BroadCast) UnicastRemoteObject.exportObject(broadCastImp, 0);
            Registry registry = LocateRegistry.createRegistry(2021);
            registry.bind("Great", broadCast);
            System.out.println("server ready...");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Error on server : "+e);
            e.printStackTrace();
        }

    }
}
