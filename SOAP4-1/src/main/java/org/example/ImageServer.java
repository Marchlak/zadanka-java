package org.example;

import java.awt.Image;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ImageServer {
    @WebMethod
    Image downloadImage(String name);

    @WebMethod
    String uploadImage(Image data);
}