package de.deftone.blueh_auf.biotopVernetzung;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BluehController {

    private final BluehService service;

    @GetMapping("/map")
    public String map(Model model) {
        List<BluehEvent> allBlueEvents = service.getAllBlueEvents();
        model.addAttribute("blueEvents", allBlueEvents);
        model.addAttribute("newBlueLocation", new BluehLocation());
        return "map";
    }

    @PostMapping("/addNewBluehEvent")
    //todo: @Valid
    public String add( @ModelAttribute BluehLocation newBlueLocation,
                       BindingResult bindingResult,
                       Model model) {
//todo: hier weiter, das klappt noch nicht richtig
        return "map";
    }
}
