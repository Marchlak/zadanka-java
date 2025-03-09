import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class KKSerwerMain {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "192.168.0.103");
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            LocateRegistry.createRegistry(1199);

            KKSerwerImpl server = new KKSerwerImpl();
            Naming.rebind("rmi://192.168.0.103:1199/KKSerwer", server);
            System.out.println("Serwer kółko i krzyżyk wystartował...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
