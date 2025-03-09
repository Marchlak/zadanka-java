import java.rmi.Naming;
import java.util.Scanner;

public class KKKlientMain {
    public static void main(String[] args) {
        KKSerwerInt server = null;
        KKKlientInt client = null;
        Scanner scanner = null;
        char mySymbol = ' ';
        try {
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            System.setProperty("java.rmi.server.hostname", "192.168.0.103");

            scanner = new Scanner(System.in);

            client = new KKKlientImpl();

            server = (KKSerwerInt) Naming.lookup("rmi://192.168.0.103:1199/KKSerwer");

            mySymbol = server.registerClient(client);

            if (mySymbol == 'X' || mySymbol == 'O') {
                System.out.println("Jesteś graczem '" + mySymbol + "'");
            } else {
                System.out.println("Jesteś obserwatorem (gra jest pełna).");
            }

            while (true) {
                if (server.isGameOver()) {
                    char winner = server.getWinner();
                    if (winner == 'X' || winner == 'O') {
                        System.out.println("Koniec gry! Zwyciężył gracz '" + winner + "'");
                    } else {
                        System.out.println("Koniec gry! Remis.");
                    }
                    break;
                }

                if (mySymbol == ' ') {
                    try { Thread.sleep(2000); } catch (InterruptedException e) {}
                    continue;
                }

                System.out.print("Podaj pole (1-9) lub 'exit': ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Kończenie programu...");
                    break;
                }
                try {
                    int pos = Integer.parseInt(input);
                    server.makeMove(client, pos);
                } catch (NumberFormatException e) {
                    System.out.println("Nieprawidłowy format. Wpisz liczbę 1-9.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
