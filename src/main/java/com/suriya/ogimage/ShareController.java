package com.suriya.ogimage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShareController {

    @GetMapping(value = "/share", produces = "text/html")
    public String share(
            @RequestParam(defaultValue = "Dynamic OG Image") String title,
            @RequestParam(defaultValue = "Java Spring Boot OG Generator") String subtitle
    ) {

        String imageUrl =
                "https://og-image-generator-7kgr.onrender.com/og"
                        + "?title=" + title.replace(" ", "+")
                        + "&subtitle=" + subtitle.replace(" ", "+")
                        + "&v=" + System.currentTimeMillis();


        return """
        <!DOCTYPE html>
        <html>
        <head>
            <title>%s</title>

           <!-- Open Graph -->
            <meta property="og:title" content="%s"/>
            <meta property="og:description" content="%s"/>
            <meta property="og:image" content="%s"/>
            
            <meta property="og:image:type" content="image/png"/>
            <meta property="og:image:width" content="1200"/>
            <meta property="og:image:height" content="630"/>
            <meta property="og:type" content="website"/>

            <!-- Twitter -->
            <meta name="twitter:card" content="summary_large_image"/>
            <meta name="twitter:title" content="%s"/>
            <meta name="twitter:description" content="%s"/>
            <meta name="twitter:image" content="%s"/>
        </head>
        <body>
            <h2>OG Preview Generated</h2>
        </body>
        </html>
        """.formatted(
                title, title, subtitle, imageUrl,
                title, subtitle, imageUrl
        );
    }
}


