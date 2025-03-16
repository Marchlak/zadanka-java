import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServerMain {
    public static void main(String[] args) {
        try {

            System.setProperty("java.rmi.server.hostname", "82.139.141.152");
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            LocateRegistry.createRegistry(1199);

            ChatServerImpl server = new ChatServerImpl();
            Naming.rebind("rmi://82.139.141.152:1199/ChatServer", server);
            System.out.println("Chat server dziala...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
