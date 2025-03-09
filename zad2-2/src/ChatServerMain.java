import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServerMain {
    public static void main(String[] args) {
        try {

            System.setProperty("java.rmi.server.hostname", "192.168.0.103");
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            LocateRegistry.createRegistry(1199);

            ChatServerImpl server = new ChatServerImpl();
            Naming.rebind("rmi://192.168.0.103:1199/ChatServer", server);
            System.out.println("Chat server dziala...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
