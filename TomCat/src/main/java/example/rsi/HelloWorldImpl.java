package example.rsi;
import javax.jws.WebService;
@WebService(endpointInterface = "example.rsi.HelloWorldInt")
public class HelloWorldImpl implements HelloWorldInt {
    @Override
    public String getHelloWorldAsString(String name) {
        return "Witaj świecie JAX-WS: " + name;
    }
}
