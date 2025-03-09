import java.rmi.Naming;
import java.util.Scanner;

public class ChatClientMain {
    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            System.setProperty("java.rmi.server.hostname", "192.168.0.103");


            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj swój nick: ");
            String nickname = scanner.nextLine();

            ChatClientInt client = new ChatClientImpl();

            ChatServerInt server = (ChatServerInt) Naming.lookup("rmi://192.168.0.103:1199/ChatServer");

            server.registerClient(client);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if (server != null && client != null) {
                        server.unregisterClient(client);
                        System.out.println("Klient został wyrejestrowany w shutdown hook.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            System.out.println("Wpisz wiadomości (wpisz 'exit' aby zakończyć):");
            while (true) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    server.unregisterClient(client);
                    break;
                }
                server.broadcastMessage(nickname + ": " + message);
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
