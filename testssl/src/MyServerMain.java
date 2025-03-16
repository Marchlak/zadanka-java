import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

public class MyServerMain {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1199);
            System.setProperty("java.rmi.server.hostname", "82.139.134.87");
            System.setProperty("javax.net.ssl.keyStore", "/home/marchlak/studia/java-zadanka/klucze/server.keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "admin123");

            MyServerImpl server = new MyServerImpl();

            Naming.rebind("rmi://82.139.134.87:1199/ABC", server);
            System.out.println("Serwer oczekuje na połączenia...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
