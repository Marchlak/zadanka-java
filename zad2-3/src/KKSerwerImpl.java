import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class KKSerwerImpl extends UnicastRemoteObject implements KKSerwerInt {
    private static final long serialVersionUID = 1L;

    private char[] board;

    private List<KKKlientInt> clients;

    private KKKlientInt playerX;
    private KKKlientInt playerO;

    private char currentPlayer;

    public KKSerwerImpl() throws RemoteException {
        super();
        board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
        clients = new ArrayList<>();
        currentPlayer = 'X';
    }

    @Override
    public synchronized char registerClient(KKKlientInt client) throws RemoteException {
        if (clients.size() >= 2) {
            client.showMessage("Serwer: Gra jest już pełna. Obserwujesz, ale nie grasz.");
            clients.add(client);
            return ' ';
        } else {
            clients.add(client);
            char symbol = (clients.size() == 1) ? 'X' : 'O';
            if (symbol == 'X') {
                playerX = client;
                client.showMessage("Serwer: Zarejestrowano jako gracz 'X'.");
            } else {
                playerO = client;
                client.showMessage("Serwer: Zarejestrowano jako gracz 'O'.");
            }
            broadcastUpdate();
            return symbol;
        }
    }

    @Override
    public synchronized boolean makeMove(KKKlientInt client, int position) throws RemoteException {
        int idx = position - 1;
        if (idx < 0 || idx > 8) {
            client.showMessage("Niepoprawna pozycja. Wybierz 1-9.");
            return false;
        }

        char symbol = (client == playerX) ? 'X' : (client == playerO) ? 'O' : ' ';

        if (symbol == ' ') {
            client.showMessage("Nie jesteś aktywnym graczem (obserwator).");
            return false;
        }

        if (symbol != currentPlayer) {
            client.showMessage("Teraz ruch gracza " + currentPlayer + ", poczekaj na swoją kolej.");
            return false;
        }

        if (board[idx] != ' ') {
            client.showMessage("Pole jest już zajęte!");
            return false;
        }

        board[idx] = symbol;

        if (checkWinner() == symbol) {
            broadcastMessage("Gracz " + symbol + " wygrywa!");
        } else if (isBoardFull()) {
            broadcastMessage("Remis! Plansza jest pełna.");
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        broadcastUpdate();
        return true;
    }

    @Override
    public synchronized char[] getBoard() throws RemoteException {
        return board.clone();
    }

    @Override
    public synchronized boolean isGameOver() throws RemoteException {
        if (checkWinner() != ' ' || isBoardFull()) {
            return true;
        }
        return false;
    }

    @Override
    public synchronized char getWinner() throws RemoteException {
        return checkWinner();
    }

    private void broadcastUpdate() throws RemoteException {
        for (KKKlientInt cli : clients) {
            cli.updateBoard(board);
        }
    }

    private void broadcastMessage(String msg) throws RemoteException {
        for (KKKlientInt cli : clients) {
            cli.showMessage(msg);
        }
    }

    private boolean isBoardFull() {
        for (char c : board) {
            if (c == ' ') {
                return false;
            }
        }
        return true;
    }

    private char checkWinner() {
        int[][] wins = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };
        for (int[] w : wins) {
            if (board[w[0]] != ' ' &&
                    board[w[0]] == board[w[1]] &&
                    board[w[1]] == board[w[2]]) {
                return board[w[0]];
            }
        }
        return ' ';
    }
}
