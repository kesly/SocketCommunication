package src.rmi_distributed;

import src.rmi_distributed.BroadCast;
import src.rmi_distributed.BroadCastImp;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerDistributed {
    //-Djava.rmi.server.hostname=127.0.0.1
    public static  void main(String [] args) {
        if(System.getSecurityManager()==null) {
            System.setProperty("java.security.policy","D:\\INSA\\4IFA\\Prog_réseau\\SocketCommunication\\src\\security.policy");
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
