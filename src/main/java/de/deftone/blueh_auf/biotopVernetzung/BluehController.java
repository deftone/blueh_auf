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
    private BluehLocation newLocation;

    @GetMapping("/")
    public String map(Model model) {
        List<BluehEvent> allBlueEvents = service.getAllBlueEvents();
        model.addAttribute("blueEvents", allBlueEvents);
        model.addAttribute("newBlueLocation", new BluehLocation());
        if (newLocation != null){
            model.addAttribute("showNewLocation", newLocation);
            // jetzt muss sie aber reseted werden
            newLocation = null;
        }
        return "map";
    }

    @PostMapping("/addNewBluehEvent")
    public String add(@Valid @ModelAttribute BluehLocation newBlueLocation,
                      BindingResult bindingResult,
                      Model model) {

        if (bindingResult.hasErrors()) {
            //todo: ein pop up? dass beides angegeben werden muss?
            // oder etwas ins html hinzufuegen?
            return "redirect:#error";
        }

//        if (!service.checkCoordinates(newBlueLocation)) {
//            return "redirect:/#error";
//        }

        //ueber model geht es nicht, das wird ueberschrieben
        this.newLocation = newBlueLocation;
        return "redirect:#coordinates";
    }
}
