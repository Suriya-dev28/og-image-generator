package com.suriya.ogimage;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OgImageController {

    @GetMapping("/og")
    public ResponseEntity<byte[]> generateOgImage(
            @RequestParam(defaultValue = "Dynamic OG Image") String title,
            @RequestParam(defaultValue = "Java Spring Boot OG Generator") String subtitle
    ) {
        try {
            int width = 1200;
            int height = 630;

            BufferedImage image =
                    new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            /* ========= BASE BACKGROUND ========= */
            g.setColor(new Color(10, 15, 30));
            g.fillRect(0, 0, width, height);

            /* ========= GLOW BLOBS ========= */
            g.setPaint(new GradientPaint(
                    200, 200, new Color(56, 189, 248, 180),
                    600, 600, new Color(99, 102, 241, 80)
            ));
            g.fill(new Ellipse2D.Double(-150, 120, 600, 600));

            g.setPaint(new GradientPaint(
                    800, 100, new Color(236, 72, 153, 160),
                    1000, 400, new Color(139, 92, 246, 80)
            ));
            g.fill(new Ellipse2D.Double(650, -200, 600, 600));

            /* ========= SAFE TEXT AREA ========= */
            int safeLeft = 300;
            int safeWidth = width - 600;


            /* ========= AUTO FONT SIZE ========= */
            int titleFontSize = 70;
            if (title.length() > 32) titleFontSize = 58;
            if (title.length() > 55) titleFontSize = 48;

            g.setFont(new Font("SansSerif", Font.BOLD, titleFontSize));
            g.setColor(Color.WHITE);

            List<String> lines =
                    wrapText(title, g.getFontMetrics(), safeWidth);

            int lineHeight = g.getFontMetrics().getHeight();
            int startY = height / 2 - (lines.size() * lineHeight) / 2 - 20;

            for (int i = 0; i < lines.size(); i++) {
                int lineWidth = g.getFontMetrics().stringWidth(lines.get(i));
                int x = safeLeft + (safeWidth - lineWidth) / 2;
                int y = startY + (i * lineHeight);
                g.drawString(lines.get(i), x, y);
            }

            /* ========= SUBTITLE ========= */
            if (!subtitle.isBlank()) {
                g.setFont(new Font("SansSerif", Font.PLAIN, 34));
                g.setColor(new Color(203, 213, 225));

                int subWidth = g.getFontMetrics().stringWidth(subtitle);
                int subX = safeLeft + (safeWidth - subWidth) / 2;
                int subY = startY + (lines.size() * lineHeight) + 45;

                g.drawString(subtitle, subX, subY);
            }

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

    /* ========= TEXT WRAP ========= */
    private List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String test = line + word + " ";
            if (fm.stringWidth(test) > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(word + " ");
            } else {
                line.append(word).append(" ");
            }
        }
        lines.add(line.toString());
        return lines;
    }
}
