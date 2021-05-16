package de.deftone.blueh_auf.registration;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Get the Spring Authentication object of the current request.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // In case you are not filtering the users of this request url.
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication); // <= This is the call you are looking for.
        }
        return "redirect:/login";
    }

    @PostMapping("/registerNewUser")
    public String processRegistration(@Valid User user,
                                      BindingResult bindingResult) {

        if (userRepo.findByUsername(user.getUsername().trim()) != null) {
            bindingResult.addError(new FieldError("user",
                    "username",
                    "Dieser Benutzername existiert bereits. Bitte einen anderen wählen."));
        }

        //todo: email ueberpruefen
//        boolean result = true;
//        try {
//            InternetAddress emailAddr = new InternetAddress(email);
//            emailAddr.validate();
//        } catch (AddressException ex) {
//            result = false;
//        }
//        return result;
//        if (result) {
//            bindingResult.addError(new FieldError("user",
//                    "email",
//                    "Bitte eine gültige E-Mail Adresse eingeben."));
//        }

        if (bindingResult.hasErrors()) {
            return "login";
        }

        //alles trimmen
        user.trimUser();

        //password verschluesseln
        String passwordVerschluesselt = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordVerschluesselt);

        try {
            userRepo.save(user);
        } catch (Exception e) {
            bindingResult.addError(new FieldError("user",
                    "username",
                    "Beim Speichern kam es zu einem Problem. Bitte katrin.rose@posteo.de kontaktieren."));
            return "login";
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

