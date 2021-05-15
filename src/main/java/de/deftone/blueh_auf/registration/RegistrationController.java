package de.deftone.blueh_auf.registration;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    // wenn man es customizen moechte:
    //https://docs.spring.io/spring-security/site/docs/4.2.20.RELEASE/guides/html5/form-javaconfig.html
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registerNewUser")
    public String processRegistration(@Valid User user,
                                      BindingResult bindingResult) {

        if (userRepo.findByUsername(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("user",
                    "username",
                    "Dieser Benutzername existiert bereits. Bitte einen anderen wählen."));
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        //password verschluesseln
        String passwordVerschluesselt = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordVerschluesselt);

        try {
            //todo: vorher alles trimmen!!!!
            userRepo.save(user);
        } catch (Exception e) {
            bindingResult.addError(new FieldError("user",
                    "username",
                    "Beim Speichern kam es zu einem Problem. Bitte katrin.rose@posteo.de kontaktieren."));
            return "register";
        }
        //todo hier ein pop up oder so etwas? das andere macht probleme beim einbinden vom nav
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

}

