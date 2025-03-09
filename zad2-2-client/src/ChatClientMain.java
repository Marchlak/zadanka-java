import java.rmi.Naming;
import java.util.Scanner;

public class ChatClientMain {
    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj swój nick: ");
            String nickname = scanner.nextLine();

            ChatClientInt client = new ChatClientImpl();

            ChatServerInt server = (ChatServerInt) Naming.lookup("rmi://192.168.0.103:1199/ChatServer");

            server.registerClient(client);

            System.out.println("Wpisz wiadomości (wpisz 'exit' aby zakończyć):");
            while (true) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
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
