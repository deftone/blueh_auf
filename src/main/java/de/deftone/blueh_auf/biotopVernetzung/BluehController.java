package de.deftone.blueh_auf.biotopVernetzung;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BluehController {

    private final BluehService service;
    private final GeoHelper helper = new GeoHelper();
    private BluehLocation newLocation;
    private String errormessage;

    @GetMapping("/biotopvernetzung")
    public String map(Model model) {
        List<BluehEvent> allBlueEvents = service.getAllBlueEvents();
        model.addAttribute("blueEvents", allBlueEvents);
        model.addAttribute("newBlueLocation", new BluehLocation());
        if (newLocation != null) {
            model.addAttribute("showNewLocation", newLocation);
            // jetzt muss sie aber reseted werden
            newLocation = null;
        }
        if (errormessage != null) {
            model.addAttribute("error", errormessage);
            errormessage = null;
        }
        return "map";
    }

    @PostMapping("/biotopvernetzung/addNewBluehEvent")
    public String add(@Valid @ModelAttribute BluehLocation newBlueLocation,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            errormessage = "Alle Felder muessen richtig ausgefuellt werden!";
            return "redirect:/biotopvernetzung";
        }

        String errorMsg = service.checkCoordinates(newBlueLocation);
        if (errorMsg != null) {
            errormessage = errorMsg;
        }

        //ueber model geht es nicht, das wird ueberschrieben
        this.newLocation = newBlueLocation;
        return "redirect:/biotopvernetzung";
    }

    @PostMapping("/biotopvernetzung/saveNewBluehEvent")
    public String save(@Valid @ModelAttribute BluehLocation newBlueLocation,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            errormessage = "Alle Felder muessen richtig ausgefuellt werden!";
            return "redirect:/biotopvernetzung";
        }

        String errorMsg = service.checkCoordinates(newBlueLocation);
        if (errorMsg != null) {
            errormessage = errorMsg;
        }

        if (errorMsg == null){
            service.saveNewBlueEvent(newBlueLocation);
            errormessage = "wurde gespeichert. neuer punkt kann eingegeben werden";
            // deshalb muss das objekt hier wieder resetted werden
            this.newLocation  = null;
        }

        return "redirect:/biotopvernetzung";
    }

    @PostMapping("/biotopvernetzung/addNewBluehEventAddress")
    public String addAddress(@RequestParam(value = "address") String address, Model model) {


        try {
            //todo: address validieren, strasse und hausnummer, mehr nicth!
            BluehLocation bluehLocation = helper.getCoordinatesFromAddress(address + ", 64380 Roßdorf, Germany");
            if (bluehLocation!= null){
                //ueber model geht es nicht, das wird ueberschrieben
                this.newLocation = bluehLocation;
                return "redirect:/biotopvernetzung";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:/biotopvernetzung";
    }


}
