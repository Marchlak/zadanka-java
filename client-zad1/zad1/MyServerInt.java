import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyServerInt extends Remote{
    String getDescription(String text) throws RemoteException;
    double calculate(double a, double b, String op) throws RemoteException;

}

