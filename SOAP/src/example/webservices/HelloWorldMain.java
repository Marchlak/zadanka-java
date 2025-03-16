package example.webservices;

import example.rsi.HelloWorldImpl;

import javax.xml.ws.Endpoint;

public class HelloWorldMain {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());
        System.out.println("Web Service Wait for Clien");
    }

}
