package backend.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    @ResponseBody
    public String dashboard(Authentication authentication) throws Exception {

        String username = authentication.getName();

        ClassPathResource resource =
                new ClassPathResource("static/dashboard.html");

        String html = new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        html = html.replace(
                "Welcome back!",
                "Welcome back, " + username + "!"
        );

        html = html.replace(
                "<div class=\"profile\" id=\"profileName\">\n            Student\n        </div>",
                "<div class=\"profile\" id=\"profileName\">\n            "
                        + username
                        + "\n        </div>"
        );

        return html;
    }
}
