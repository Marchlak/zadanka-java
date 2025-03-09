import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DatabaseServerInt extends Remote {
    List<ProductInterface> getProducts() throws RemoteException;
    ProductInterface searchProduct(String name) throws RemoteException;
}
