package example.webservicesclient;


import example.rsi.HelloWorldImplService;
import example.rsi.HelloWorldInt;

public class WebServicesClient {
    public static void main(String[] args) {
        HelloWorldImplService service = new HelloWorldImplService();
        HelloWorldInt hello = service.getHelloWorldImplPort();

        String Zapytanie = "Aha 32";
        String Odpowiedz = hello.getHelloWorldAsString(Zapytanie);
        System.out.println(Zapytanie);
        System.out.println(Odpowiedz);
    }
}
