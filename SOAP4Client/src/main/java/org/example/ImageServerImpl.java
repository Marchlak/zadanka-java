package org.example;

import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@MTOM
@WebService(endpointInterface = "org.example.ImageServer")
public class ImageServerImpl implements ImageServer {
    @Override
    public Image downloadImage(String name) {
        try {
            File image = new File("/home/marchlak/Downloads/oh-4x.png" + name);
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadImage(Image data) {
        if (data != null)
            return "Upload Successful";

        throw new WebServiceException("Upload Failed!");
    }
}
