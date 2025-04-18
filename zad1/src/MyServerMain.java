import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
// ...
public class MyServerMain {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1199);
            System.setProperty("java.rmi.server.hostname", "82.139.141.152");
            MyServerImpl server = new MyServerImpl();
       //     Naming.rebind("rmi://localhost:1199/ABC", server);
            Naming.rebind("rmi://82.139.141.152:1199/ABC", server);
            System.out.println("Serwer oczekuje na połączenia...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
