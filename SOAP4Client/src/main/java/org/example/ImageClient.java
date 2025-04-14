package org.example;

import org.example.ImageServer;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;


public class ImageClient {

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:9999/ws/image?wsdl");
        QName qname = new QName("http://example.org/", "ImageServerImplService");

        Service service = Service.create(url, qname);
        ImageServer imageServer = service.getPort(ImageServer.class);

        // upload
        Image imgUpload = ImageIO.read(new File("/home/marchlak/Studia/java-zadanka/SOAP4Client/src/main/resources/oh-4x.png"));

        BindingProvider bp = (BindingProvider) imageServer;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        binding.setMTOMEnabled(true);

        String status = imageServer.uploadImage(imgUpload);
        System.out.println("imageServer.uploadImage() : " + status);

        // download
        Image image = imageServer.downloadImage("ohCry-4x.png");

        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);

        System.out.println("imageServer.downloadImage() : Download Successful!");
    }

}
