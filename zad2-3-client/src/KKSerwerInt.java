import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KKSerwerInt extends Remote {

    char registerClient(KKKlientInt client) throws RemoteException;


    boolean makeMove(KKKlientInt client, int position) throws RemoteException;


    char[] getBoard() throws RemoteException;


    boolean isGameOver() throws RemoteException;


    char getWinner() throws RemoteException;
}
