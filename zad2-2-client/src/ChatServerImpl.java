import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInt {
    private List<ChatClientInt> clients;

    protected ChatServerImpl() throws RemoteException {
        super();
        clients = new ArrayList<>();
    }

    @Override
    public synchronized void registerClient(ChatClientInt client) throws RemoteException {
        clients.add(client);
        System.out.println("Nowy klient zarejestrowany. Liczba klientów: " + clients.size());
    }

    @Override
    public synchronized void unregisterClient(ChatClientInt client) throws RemoteException {
        clients.remove(client);
        System.out.println("Klient wyrejestrowany. Liczba klientów: " + clients.size());
    }

    @Override
    public synchronized void broadcastMessage(String message) throws RemoteException {
        System.out.println("Rozsyłanie wiadomości: " + message);
        for (ChatClientInt client : clients) {
            try {
                client.receiveMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
