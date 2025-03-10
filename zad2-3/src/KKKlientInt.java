import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KKKlientInt extends Remote {

    void showMessage(String msg) throws RemoteException;

    void updateBoard(char[] board) throws RemoteException;
}
