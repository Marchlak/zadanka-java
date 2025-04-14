package org.example;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.awt.*;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ImageServer {
    @WebMethod
    Image downloadImage(String name);

    @WebMethod
    String uploadImage(Image data);
}