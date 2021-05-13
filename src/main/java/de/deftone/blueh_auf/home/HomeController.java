package de.deftone.blueh_auf.home;

import de.deftone.blueh_auf.registration.User;
import de.deftone.blueh_auf.registration.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserRepo userRepo;

    @GetMapping({"/", "/home/{user}", "/home"})
    public String home(Model model,
                       @PathVariable(name = "user", required = false) String username) {

        if (username != null) {
            User user = userRepo.findByUsername(username);
            //kann auch null sein! wird im template geprueft
            //falls es null ist knallt es sonst
            model.addAttribute("user", user);
        }
        return "home";
    }

}
