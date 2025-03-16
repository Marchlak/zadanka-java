import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class DatabaseServerMain {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.codebase", "file://home/marchlak/studia/java-zadanka/zad2-1/out/production/zad1");
            LocateRegistry.createRegistry(1199);

            System.setProperty("java.rmi.server.hostname", "82.139.141.152");
            System.setProperty("java.security.policy", "security.policy");

            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            DatabaseServerImpl server = new DatabaseServerImpl();
            Naming.rebind("rmi://82.139.141.152:1199/DatabaseServer", server);
            System.out.println("Serwer bazy danych oczekuje na połączenia...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
