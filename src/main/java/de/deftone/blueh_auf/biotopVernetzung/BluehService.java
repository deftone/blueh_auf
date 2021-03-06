package de.deftone.blueh_auf.biotopVernetzung;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BluehService {

    private final BluehEventRepo bluehEventRepo;

    public List<BluehEvent> getAllBlueEvents() {
        return bluehEventRepo.findAll();
    }

    public boolean addressStringOk(String address) {
        if (address == null || address.isEmpty() || address.isBlank())
            return false;
        //* = Occurs zero or more times
        //+ = Occurs one or more times
        // pruefen ob mit komma statt punkt eingegeben, falls ja, ersetzen
        String pattern = "(\\w+ *-*.*)+\\d+";
        return address.trim().matches(pattern);
    }

    public boolean coordinateStringOk(String coordinates) {
        if (coordinates == null || coordinates.isEmpty() || coordinates.isBlank())
            return false;

        // pruefen ob mit komma statt punkt eingegeben, falls ja, ersetzen
        String pattern = "49,\\d+, *8,\\d+";
        if (coordinates.trim().matches(pattern)) {
            coordinates = coordinates.replace("49,", "49.").replace("8,", "8.");
        }
        //die Koordinaten muessen 49.8xxxx 8.xxxxx sein
        pattern = "49.\\d+, *8.\\d+";
        return coordinates.trim().matches(pattern);
    }

    /**
     * tiho3Nge#geo
     * https://developer.mapquest.com/documentation/open/geocoding-api/
     * https://opencagedata.com/pricing
     * https://developer.here.com/blog/how-to-use-geocoding-in-java-with-the-here-geocoding-search-api
     * https://opencagedata.com/tutorials/geocode-in-java
     * grob mit google maps die raender von rossdorf/gundernhausen geholt:
     * oben links:
     * 49.867353931107296, 8.729685074927188
     * unten links:
     * 49.850842786862664, 8.74182336668133
     * oben rechts:
     * 49.87697630782485, 8.796567062492525
     * unten rechts:
     * 49.861876925732666, 8.805913547143215
     *
     * @param newBlueLocation Koordinate des neuen BluehEvents
     * @return true, wenn alles ok ist
     */
    public String checkCoordinates(GeoLocation newBlueLocation) {
        String errorMsg = null;

        //latitude sollte zw. 49.850 und 49.877 sein
        if (newBlueLocation.getLatitude() < 49.850 ||
                newBlueLocation.getLatitude() > 49.877) {
//            errorMsg = "Latitude ist nicht innerhalb Rossdorf! Muss zw. 49.850 und 49.877 sein. ";
            errorMsg = "Die Adresse/Koordinate liegt nicht in Ro??dorf. Bitte nochmal versuchen.";
            log.error(errorMsg);
        }

        //longitude sollte zw. 8.730 und 8.806 sein
        if (newBlueLocation.getLongitude() < 8.730 ||
                newBlueLocation.getLongitude() > 8.806) {
//            String error2 = "Longitude ist nicht innerhalb Rossdorf! Muss zw. 8.730 und 8.806 sein.";
            errorMsg = "Die Adresse/Koordinate liegt nicht in Ro??dorf. Bitte nochmal versuchen.";
            log.error(errorMsg);
        }

        return errorMsg;
    }

    public BluehEvent saveNewBlueEvent(GeoLocation geoLocation, String userName) {
        BluehEvent bluehEvent = new BluehEvent(geoLocation, userName);
        return bluehEventRepo.save(bluehEvent);
    }

    public List<BluehEvent> addEvents(List<BluehEvent> events) {
        List<BluehEvent> savedEvents = new ArrayList<>();
        for (BluehEvent event : events) {
            GeoLocation location = new GeoLocation(event.getLatitude(), event.getLongitude());
            if (checkCoordinates(location) == null) {
                bluehEventRepo.save(event);
                savedEvents.add(event);
            }
        }
        return savedEvents;
    }
}
