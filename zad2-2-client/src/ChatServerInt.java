import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ChatServerInt extends Remote {
    void registerClient(ChatClientInt client) throws RemoteException;
    void broadcastMessage(String message) throws RemoteException;
    void unregisterClient(ChatClientInt client) throws RemoteException;

}
