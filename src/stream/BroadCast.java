package src.stream;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BroadCast extends Remote {

    String sayHello() throws RemoteException;
}
