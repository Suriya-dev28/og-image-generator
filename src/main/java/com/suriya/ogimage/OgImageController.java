package com.suriya.ogimage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class OgImageController {

    @GetMapping("/og")
    public ResponseEntity<byte[]> generateOgImage(
            @RequestParam(defaultValue = "Dynamic OG Image") String title,
            @RequestParam(defaultValue = "Java Spring Boot") String subtitle
    ) {
        try {
            int width = 1200;
            int height = 630;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(40, 48, 72),
                    width, height, new Color(133, 147, 152)
            );
            g.setPaint(gradient);
            g.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);
            Font titleFont = new Font("Arial", Font.BOLD, 72);
            g.setFont(titleFont);

            FontMetrics fm = g.getFontMetrics();
            int titleX = (width - fm.stringWidth(title)) / 2;
            int titleY = height / 2 - 20;
            g.drawString(title, titleX, titleY);

            g.setFont(new Font("Arial", Font.PLAIN, 40));
            int subX = (width - g.getFontMetrics().stringWidth(subtitle)) / 2;
            g.drawString(subtitle, subX, titleY + 60);

            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
