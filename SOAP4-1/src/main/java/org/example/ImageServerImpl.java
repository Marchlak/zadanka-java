package org.example;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;

@MTOM
@WebService(endpointInterface = "org.example.ImageServer")
public class ImageServerImpl implements ImageServer {
    @Override
    public Image downloadImage(String name) {
        try {
            File image = new File("/home/marchlak/Studia/java-zadanka/SOAP4-1/src/main/resources/" + name);
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String uploadImage(Image data) {
        if (data != null) {
            try {
                BufferedImage bufferedImage;
                if (data instanceof BufferedImage) {
                    bufferedImage = (BufferedImage) data;
                } else {
                    bufferedImage = new BufferedImage(data.getWidth(null), data.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    bufferedImage.getGraphics().drawImage(data, 0, 0, null);
                }
                File outputfile = new File("uploaded.png");
                ImageIO.write(bufferedImage, "png", outputfile);
                return "Upload Successful";
            } catch (IOException e) {
                throw new WebServiceException("Upload Failed!", e);
            }
        }
        throw new WebServiceException("Upload Failed!");
    }

}
