package de.deftone.blueh_auf.biotopVernetzung;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BluehController {

    private final BluehService service;

    @GetMapping("/")
    public String map(Model model) {
        List<BluehEvent> allBlueEvents = service.getAllBlueEvents();
        model.addAttribute("blueEvents", allBlueEvents);
        model.addAttribute("newBlueLocation", new BluehLocation());
        return "map";
    }

    @PostMapping("/addNewBluehEvent")
    public String add(@Valid @ModelAttribute BluehLocation newBlueLocation,
                      BindingResult bindingResult,
                      Model model,
                      RedirectAttributes redirectAttrs) {

        if (bindingResult.hasErrors()) {
            //todo: ein pop up? dass beides angegeben werden muss?
            // oder etwas ins html hinzufuegen?
            return "redirect:#error";
        }

//        if (!service.checkCoordinates(newBlueLocation)) {
//            return "redirect:/#error";
//        }

        //to do add new location as green point
        //nix davon funktionert :( nach dem redirekt ist alles null :(
        redirectAttrs.addFlashAttribute("showNewLocation", newBlueLocation);
        model.addAttribute("showNewLocation", newBlueLocation);
        model.addAttribute("test", "test test");
        return "redirect:#coordinates"; //deshalb wird das model wieder ueberschrieben!
    }
}
