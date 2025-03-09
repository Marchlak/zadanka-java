import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyServerImpl extends UnicastRemoteObject implements MyServerInt {

    int i = 0;

    protected MyServerImpl() throws RemoteException {
        super();
    }
    @Override
    public String getDescription(String text) throws RemoteException {
        i++;
        System.out.println("MyServerImpl.getDescription: " + text + " " + i);
        return "getDescription: " + text + " " + i;
    }
    @Override
    public double calculate(double a, double b, String op) throws RemoteException {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Dzielenie przez zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Nieobsługiwany operator: " + op);
        }
    }

}
