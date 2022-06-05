package de.deftone.blueh_auf.home;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping({"/"})
    public String home() {
        return "home";
    }

    @GetMapping("/navigation")
    public String addNavigation() {
        return "navigation";
    }

    @GetMapping({"/contact"})
    public String contact() {
        return "contact";
    }

    @GetMapping({"/info"})
    public String info() {
        return "info";
    }

    @GetMapping({"/aktuelles"})
    public String aktuelles() {
        return "aktuelles";
    }

    @GetMapping({"/automaten"})
    public String automaten() {
        return "automaten";
    }

    @GetMapping({"/aktionen"})
    public String aktionen() {
        return "aktionen";
    }

    @GetMapping({"/galerie"})
    public String galerie() {
        return "galerie";
    }
}
