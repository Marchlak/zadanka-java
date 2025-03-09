import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServerImpl extends UnicastRemoteObject implements DatabaseServerInt {
    private List<ProductInterface> products;

    protected DatabaseServerImpl() throws RemoteException {
        super();
        products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 2999.99));
        products.add(new Product(2, "Smartphone", 1999.99));
        products.add(new Product(3, "Tablet", 999.99));
    }

    @Override
    public List<ProductInterface> getProducts() throws RemoteException {
        return products;
    }

    @Override
    public ProductInterface searchProduct(String name) throws RemoteException {
        for (ProductInterface p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
