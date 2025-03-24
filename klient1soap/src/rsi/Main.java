package rsi;

public class Main {
    public static void main(String[] args) {
        HelloWorldImplService helloService = new HelloWorldImplService();
        HelloWorldInt hello = helloService.getHelloWorldImplPort();

        String request = "Pozdrawiam Klient";
        String response = hello.getHelloWorldAsString(request);
        System.out.println("Klient wysłał: " + request);
        System.out.println("Klient otrzymał: " + response);
    }
}