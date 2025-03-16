import java.rmi.Naming;
import java.util.Scanner;

public class MyClientMain {
    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            MyServerInt server = (MyServerInt) Naming.lookup("rmi://82.139.141.152:1199/ABC");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Wpisz operację w formacie: <liczba1> <operator> <liczba2>");
            System.out.println("Dostępne operatory: +, -, *, /");
            System.out.println("Aby zakończyć wpisz 'exit'.");

            while (true) {
                System.out.print("Podaj operację: ");
                String line = scanner.nextLine().trim();
                if (line.equalsIgnoreCase("exit")) {
                    System.out.println("Kończenie programu.");
                    break;
                }

                String[] tokens = line.split("\\s+");
                if (tokens.length != 3) {
                    System.out.println("Błędny format. Użyj: liczba operator liczba");
                    continue;
                }

                try {
                    double a = Double.parseDouble(tokens[0]);
                    String op = tokens[1];
                    double b = Double.parseDouble(tokens[2]);

                    if (!op.equals("+") && !op.equals("-") && !op.equals("*") && !op.equals("/")) {
                        System.out.println("Niedozwolony operator. Dostępne operatory: +, -, *, /");
                        continue;
                    }

                    double result = server.calculate(a, b, op);
                    System.out.println("Wynik: " + result);
                } catch (NumberFormatException e) {
                    System.out.println("Błędne dane liczbowe. Spróbuj ponownie.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

