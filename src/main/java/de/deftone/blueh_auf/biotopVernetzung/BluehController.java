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
    private GeoLocation newBluehLocation;
    private String coordinatesString;
    private String address;
    private String errormessage;

    @GetMapping("/biotopvernetzung")
    public String map(Model model) {
        List<BluehEvent> allBlueEvents = service.getAllBlueEvents();
        //alle bisherigen blueEvents:
        model.addAttribute("blueEvents", allBlueEvents);
        return "map";
    }

    @GetMapping("/biotopvernetzungUI")
    public String mapUI(Model model) {
        List<BluehEvent> allBlueEvents = service.getAllBlueEvents();
        //alle bisherigen blueEvents:
        model.addAttribute("blueEvents", allBlueEvents);

        if (newBluehLocation != null) {
            //todo: muss hier nochmal geprueft werden, ob die location in rossdorf ist?
            model.addAttribute("showNewLocation", newBluehLocation);
            //wegen speichern darf es hier nicth zurueckgesetzt werden!
        }

        if (coordinatesString != null){
            model.addAttribute("coordinatesString", coordinatesString);
            //hier oder nach speichern? was ist besser?
            coordinatesString = null;
        }

        if (address != null){
            model.addAttribute("address", address);
            address = null;
        }

        if (errormessage != null) {
            model.addAttribute("error", errormessage);
            errormessage = null;
        }
        return "mapUI";
    }

    // wenn neues blueEvent ueber adresse eingegeben wird
    @PostMapping("/biotopvernetzungUI/newBluehEventAddress")
    public String addAddress(@RequestParam(value = "address") String address) {

        try {
            //todo: address validieren, strasse und hausnummer, mehr darf nicht eingegeben werden
            GeoLocation geoLocation = helper.getCoordinatesFromAddress(address + ", 64380 Roßdorf, Germany");
            // todo: und was, wenn addresse nicht exisitert??
            if (geoLocation != null) {
                //ueber model geht es nicht, das wird ueberschrieben
                this.newBluehLocation = geoLocation;
                this.address = address;
                return "redirect:/biotopvernetzungUI";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "redirect:/biotopvernetzungUI";
    }

    // wenn neues blueEvent ueber lat und lon als String (copy&paste von google maps) eingegeben wird
    @PostMapping("/biotopvernetzungUI/newBluehEventCoordinatesStr")
    public String addCoordinatesString(@RequestParam(value = "coordinates") String coordinates) {

        //todo: aus String ein GeoLocation machen
        GeoLocation newGeoLocation = new GeoLocation(49.86273557682863, 8.750928081003872);

        String errorMsg = service.checkCoordinates(newGeoLocation);
        if (errorMsg != null) {
            errormessage = errorMsg;
        }

        //ueber model geht es nicht, das wird ueberschrieben
        this.newBluehLocation = newGeoLocation;
        this.coordinatesString = coordinates;
        return "redirect:/biotopvernetzungUI";
    }


    // wenn neues BluehEvent gespeichert wird (gilt fuer alle 3 eingabe typen)
    @PostMapping("/biotopvernetzungUI/saveNewBluehEvent")
    public String save() {

        //sollte ueberfuessig sein - nein, wegen adresse nicht!
        String errorMsg = service.checkCoordinates(newBluehLocation);

        if (errorMsg != null) {
            errormessage = errorMsg;
        }

        if (errorMsg == null) {
            service.saveNewBlueEvent(newBluehLocation);
            // deshalb muss das objekt hier wieder resetted werden
            this.newBluehLocation = null;
        }

        return "redirect:/biotopvernetzungUI";
    }

}
