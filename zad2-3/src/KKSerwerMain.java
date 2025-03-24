import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class KKSerwerMain {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1199);

            System.setProperty("java.security.policy", "security.policy");
            System.setProperty("java.rmi.server.hostname", "82.139.138.113");
            System.setProperty("javax.net.ssl.keyStore", "/home/marchlak/studia/java-zadanka/klucze/server.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "admin123");

            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }


            KKSerwerImpl server = new KKSerwerImpl();
            Naming.rebind("rmi://82.139.138.113:1199/KKSerwer", server);
            System.out.println("Serwer kółko i krzyżyk wystartował...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
