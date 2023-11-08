package sip.proyecto.app.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import sip.proyecto.app.service.QRCodeService;

@RestController
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;
    
    @GetMapping("/generateQRCode")
    public void generateQRCode(HttpServletResponse response,
                               @RequestParam String text,
                               @RequestParam(defaultValue = "1000") int width,
                               @RequestParam(defaultValue = "1000") int height) throws Exception {
        BufferedImage image = qrCodeService.generateQRCode(text, width, height);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        System.out.print(image);
        outputStream.flush();
        outputStream.close();
        
        
    }
}