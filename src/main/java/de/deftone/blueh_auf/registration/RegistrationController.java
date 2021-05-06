package de.deftone.blueh_auf.registration;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user/register")
public class RegistrationController {

    private final UserRepo userRepo;
//    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepo userRepository
            //, PasswordEncoder passwordEncoder
                                  ) {
        this.userRepo = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegistration(Model model) {

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid User user,
                                      BindingResult bindingResult) {

//        if (userRepo.findByUsername(user.getUsername()) != null) {
//            bindingResult.addError(new FieldError("newsUser",
//                    "username",
//                    "Benutzer existiert bereits."));
//        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

//        //password verschluesseln
//        String passwordVerschluesselt = passwordEncoder.encode(user.getPassword());
//        user.setPassword(passwordVerschluesselt);
//
//        newsUserRepository.save(user);

        return "redirect:/home";// + user.getUsername();

    }

}

