import java.rmi.Naming;

public class MyClientMain {
    public static void main(String[] args) {
        try {
             System.setProperty("java.security.policy", "security.policy");
             if (System.getSecurityManager() == null) {
                 System.setSecurityManager(new SecurityManager());
             }

            MyServerInt server = (MyServerInt) Naming.lookup("rmi://192.168.0.103:1199/ABC");
            String request = "Hallo :-)";
            String response = server.getDescription(request);
            System.out.println("Wysłano: " + request);
            System.out.println("Otrzymano: " + response);

            double a = 10;
            double b = 5;
            String op = "/";

            double result = server.calculate(a, b, op);
            System.out.println("Obliczenia: " + a + " " + op + " " + b + " = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

