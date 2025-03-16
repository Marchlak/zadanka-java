import java.rmi.Naming;
import java.util.Scanner;

public class KKKlientMain {
    public static void main(String[] args) {
        KKSerwerInt server = null;
        KKKlientInt client = null;
        KKKlientInt clientStub = null;
        Scanner scanner = null;
        char mySymbol = ' ';
        try {
            System.setProperty("java.security.policy", "security.policy");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            System.setProperty("java.rmi.server.hostname", "192.168.0.103");
            System.setProperty("javax.net.ssl.trustStore", "/home/marchlak/studia/java-zadanka/klucze/client.truststore");
            System.setProperty("javax.net.ssl.trustStorePassword", "admin123");

            scanner = new Scanner(System.in);

            client = new KKKlientImpl();
            clientStub = client;

            server = (KKSerwerInt) Naming.lookup("rmi://192.168.0.103:1199/KKSerwer");

            mySymbol = server.registerClient(clientStub);

            if (mySymbol == 'X' || mySymbol == 'O') {
                System.out.println("Jesteś graczem '" + mySymbol + "'");
            } else {
                System.out.println("Jesteś obserwatorem (gra jest pełna).");
            }

            final KKSerwerInt finalServer = server;
            final KKKlientInt finalClientStub = clientStub;

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    finalServer.unregisterClient(finalClientStub);
                    System.out.println("Zakończono przez Ctrl+C. Wylogowano z serwera.");
                } catch (Exception ignored) {}
            }));

            while (true) {
                if (finalServer.isGameOver()) {
                    char winner = finalServer.getWinner();
                    if (winner == 'X' || winner == 'O') {
                        System.out.println("Koniec gry! Zwyciężył gracz '" + winner + "'");
                    } else {
                        System.out.println("Koniec gry! Remis.");
                    }
                    System.out.print("Aby zresetować grę wpisz 'reset', aby wyjść wpisz 'exit': ");
                    String cmd = scanner.nextLine();
                    if (cmd.equalsIgnoreCase("exit")) {
                        finalServer.unregisterClient(finalClientStub);
                        System.out.println("Wylogowano z serwera. Zakończono.");
                        break;
                    } else if (cmd.equalsIgnoreCase("reset")) {
                        mySymbol = finalServer.resetGame(finalClientStub);
                        if (mySymbol == 'X' || mySymbol == 'O') {
                            System.out.println("Jesteś graczem '" + mySymbol + "'");
                        } else {
                            System.out.println("Jesteś obserwatorem (gra jest pełna).");
                        }
                        continue;
                    }
                    break;
                }
                if (mySymbol == ' ') {
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                    continue;
                }
                System.out.print("Podaj pole (1-9) lub 'exit': ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    finalServer.unregisterClient(finalClientStub);
                    System.out.println("Wylogowano z serwera. Zakończono.");
                    break;
                }
                try {
                    int pos = Integer.parseInt(input);
                    finalServer.makeMove(mySymbol, pos);
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
