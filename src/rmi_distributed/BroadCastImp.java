package src.rmi_distributed;

import java.io.Serializable;
import java.rmi.RemoteException;

public class BroadCastImp implements BroadCast, Serializable {

    private String message;

    public BroadCastImp(String massage) {
        this.message = massage;
    }

    @Override
    public String sayHello() throws RemoteException {
        return message;
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        this.message = message;
    }

    public String  getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
