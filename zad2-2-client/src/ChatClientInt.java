import java.rmi.Remote;
import java.rmi.RemoteException;



public interface ChatClientInt extends Remote {
    void receiveMessage(String message) throws RemoteException;
}