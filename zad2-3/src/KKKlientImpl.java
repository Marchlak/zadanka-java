import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class KKKlientImpl extends UnicastRemoteObject implements KKKlientInt {
    private static final long serialVersionUID = 1L;

    protected KKKlientImpl() throws RemoteException {
        super();
    }

    @Override
    public void showMessage(String msg) throws RemoteException {
        System.out.println(msg);
    }

    @Override
    public void updateBoard(char[] board) throws RemoteException {

        System.out.println("\nAktualna plansza:");
        for (int i = 0; i < 9; i++) {
            char c = board[i];
            if (c == ' ') {
                System.out.print(i+1);
            } else {
                System.out.print(c);
            }
            if ((i+1) % 3 == 0 && i != 8) {
                if (i != 2 && i != 5) {
                    System.out.println();
                    System.out.println("---+---+---");
                } else {
                    System.out.println();
                }
            } else if ((i+1) % 3 != 0) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }
}
