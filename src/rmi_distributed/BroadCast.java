package src.rmi_distributed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BroadCast extends Remote {

    String sayHello() throws RemoteException;

    void sendMessage(String message) throws RemoteException;
}
