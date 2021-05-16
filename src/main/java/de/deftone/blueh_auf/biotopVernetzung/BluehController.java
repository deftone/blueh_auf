package de.deftone.blueh_auf.biotopVernetzung;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            //wenn es soweit kommt, dann ist die newBlueLocation richtig
            model.addAttribute("showNewLocation", newBluehLocation);
            //wegen speichern darf es hier nicht zurueckgesetzt werden!
        }

        if (coordinatesString != null){
            model.addAttribute("coordinatesString", coordinatesString);
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
            //pruefen ob die adresse richtig eingegeben wurde
            if (!service.addressStringOk(address)){
                this.newBluehLocation = null;
                this.errormessage = "Bitte Adresse im richtigen Format angeben. zB Erbacher Str. 1";
                this.address = address;
                return "redirect:/biotopvernetzungUI";
            }

            GeoLocation geoLocation = helper.getCoordinatesFromAddress(address + ", 64380 Roßdorf, Germany");
            if (geoLocation != null) {
                String errorMsg = service.checkCoordinates(geoLocation);
                if (errorMsg != null) {
                    //falls koordinaten trotz address pruefung vorab falsch sind
                    this.newBluehLocation = null;
                    this.errormessage = errorMsg;
                    this.address = address;
                    return "redirect:/biotopvernetzungUI";
                }
                // alles ok!
                //ueber model geht es nicht, das wird ueberschrieben
                this.newBluehLocation = geoLocation;
                this.address = address;
                return "redirect:/biotopvernetzungUI";
            } else {
                //geolocation wurde nicht ermittelt
                this.newBluehLocation = null;
                this.errormessage = "Adresse wurde in Roßdorf/Gundernhausen nicht gefunden. Bitte Straße und Hausnummer prüfen.";
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

        if (!service.coordinateStringOk(coordinates)){
            newBluehLocation = null;
            errormessage = "Bitte Koordinaten im richtigen Format und aus Roßdorf angeben. zB 49.858, 8.756";
            coordinatesString = coordinates;
            return "redirect:/biotopvernetzungUI";
        }

        GeoLocation newGeoLocation = new GeoLocation(coordinates);

        String errorMsg = service.checkCoordinates(newGeoLocation);
        if (errorMsg != null) {
            newBluehLocation = null;
            errormessage = errorMsg;
        } else {
            // nur wenn kein Fehler den Punkt anzeigen
            newBluehLocation = newGeoLocation;
        }

        //ueber model geht es nicht, das wird ueberschrieben
        coordinatesString = coordinates;
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
