package de.deftone.blueh_auf.biotopVernetzung;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

@Service
@Slf4j
public class BluehService {

    public static final List<BluehEvent> ALL_BLUE_EVENTS = new ArrayList<>();

    static {
        ALL_BLUE_EVENTS.add(new BluehEvent(49.859532796957275, 8.74570825379904, "Katrin", LocalDate.now()));
        ALL_BLUE_EVENTS.add(new BluehEvent(49.85964959437167, 8.746080369340579, "Katrin", LocalDate.now()));
        ALL_BLUE_EVENTS.add(new BluehEvent(49.86477095041105, 8.75050033600434, "Kathrin", LocalDate.now()));
        ALL_BLUE_EVENTS.add(new BluehEvent(49.8675274489317, 8.759033316932028, "Kita", LocalDate.now()));
        ALL_BLUE_EVENTS.add(new BluehEvent(49.86832962252158, 8.780362242654158, "Frieder", LocalDate.now()));
    }

    public List<BluehEvent> getAllBlueEvents(){
                return ALL_BLUE_EVENTS;
    }

    public boolean checkCoordinates(BluehLocation newBlueLocation) {
        boolean ret = true;

        if (abs(newBlueLocation.getLatitude()-49.8)>0.2){
            ret = false;
            log.error("Latitude weicht mehr als 0.2 von 49.8 ab!");

        }

        if (abs(newBlueLocation.getLatitude()-8.7) > 0.2){
            ret = false;
            log.error("Longitude weicht mehr als 0.2 von 8.7 ab!");
        }

        return ret;
    }
}
