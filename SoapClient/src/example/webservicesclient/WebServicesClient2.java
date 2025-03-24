package example.webservicesclient;

import example.rsi.HelloWorldImplService;
import example.rsi.HelloWorldInt;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServicesClient2 {
    public static void main(String[] args) {
        try {
            URL wsdlURL = new URL("http://localhost:8080/ws/hello?wsdl");
            QName SERVICE_NAME = new QName("http://rsi.example/", "HelloWorldImplService");
            HelloWorldImplService service = new HelloWorldImplService(wsdlURL, SERVICE_NAME);
            HelloWorldInt hello = service.getHelloWorldImplPort();
            String zapytanie = "Aha 32";
            String odpowiedz = hello.getHelloWorldAsString(zapytanie);
            System.out.println(zapytanie);
            System.out.println(odpowiedz);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
