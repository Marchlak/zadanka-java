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

    public synchronized char registerClient(KKKlientInt client) throws RemoteException {
        if (clients.size() < 2) {
            clients.add(client);
            if (playerX == null) {
                playerX = client;
                broadcastMessage("Dołączył gracz X.");
                broadcastUpdate();
                return 'X';
            } else if (playerO == null) {
                playerO = client;
                broadcastMessage("Dołączył gracz O.");
                broadcastUpdate();
                return 'O';
            }
        }
        System.out.println("Siema");
        clients.add(client);
        broadcastUpdate();
        return ' ';
    }

    public synchronized boolean makeMove(char symbol, int position) throws RemoteException {
        int idx = position - 1;
        if (idx < 0 || idx > 8) {
            return false;
        }
        if (symbol != currentPlayer) {
            return false;
        }
        if (board[idx] != ' ') {
            return false;
        }
        board[idx] = symbol;
        if (checkWinner() == symbol) {
            broadcastMessage("Gracz " + symbol + " wygrywa!");
        } else if (isBoardFull()) {
            broadcastMessage("Remis!");
        }
        currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
        broadcastUpdate();
        broadcastMessage("Teraz ruch gracza " + currentPlayer);
        return true;
    }

    public synchronized char[] getBoard() throws RemoteException {
        return board.clone();
    }

    public synchronized boolean isGameOver() throws RemoteException {
        if (checkWinner() != ' ' || isBoardFull()) {
            return true;
        }
        return false;
    }

    public synchronized char getWinner() throws RemoteException {
        return checkWinner();
    }

    public synchronized void unregisterClient(KKKlientInt client) throws RemoteException {
        clients.remove(client);
        System.out.println(clients.size());
        if (client == playerX) playerX = null;
        if (client == playerO) playerO = null;
        broadcastMessage("Jeden z graczy się wylogował.");
        resetGame(client);
        broadcastUpdate();
    }

    @Override
    public synchronized char resetGame(KKKlientInt client) throws RemoteException {
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i]);
            board[i] = ' ';
        }
        currentPlayer = 'X';
        if (!clients.contains(client)) {
            clients.add(client);
        }
        if (playerX == null) {
            playerX = client;
            broadcastMessage("Nowa gra. Gracz X rozpoczyna.");
            broadcastUpdate();
            return 'X';
        } else if (playerO == null && playerX != client) {
            playerO = client;
            broadcastMessage("Gracz O dołącza do nowej gry.");
            broadcastUpdate();
            return 'O';
        } else {
            broadcastUpdate();
            return ' ';
        }
    }


    private boolean isBoardFull() {
        for (char c : board) {
            if (c == ' ') return false;
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
            if (board[w[0]] != ' ' && board[w[0]] == board[w[1]] && board[w[1]] == board[w[2]]) {
                return board[w[0]];
            }
        }
        return ' ';
    }

    private void broadcastUpdate() throws RemoteException {
        for (KKKlientInt c : clients) {
            c.updateBoard(board);
        }
    }

    private void broadcastMessage(String msg) throws RemoteException {
        for (KKKlientInt c : clients) {
            c.showMessage(msg);
        }
    }
}
