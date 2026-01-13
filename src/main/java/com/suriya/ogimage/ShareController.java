package com.suriya.ogimage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShareController {

    @GetMapping(value = "/share", produces = "text/html")
    public String share(
            @RequestParam(defaultValue = "Dynamic OG Image") String title,
            @RequestParam(defaultValue = "Java Spring Boot Generator") String subtitle
    ) {

        String imageUrl =
                "https://og-image-generator.onrender.com/og"
                        + "?title=" + title.replace(" ", "+")
                        + "&subtitle=" + subtitle.replace(" ", "+");

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <title>%s</title>

            <meta property="og:title" content="%s"/>
            <meta property="og:description" content="%s"/>
            <meta property="og:image" content="%s"/>
            <meta property="og:type" content="website"/>

            <meta name="twitter:card" content="summary_large_image"/>
            <meta name="twitter:title" content="%s"/>
            <meta name="twitter:description" content="%s"/>
            <meta name="twitter:image" content="%s"/>
        </head>
        <body>
            <h2>OG Preview Generated</h2>
            <p>Share this link on WhatsApp / Facebook / LinkedIn</p>
        </body>
        </html>
        """.formatted(
                title, title, subtitle, imageUrl,
                title, subtitle, imageUrl
        );
    }
}
