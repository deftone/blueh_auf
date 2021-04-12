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

    public List<BluehEvent> getAllBlueEvents() {
        return ALL_BLUE_EVENTS;
    }

    /**
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
    public String checkCoordinates(BluehLocation newBlueLocation) {
        String errorMsg = null;

        //latitude sollte zw. 49.850 und 49.877 sein
        if (newBlueLocation.getLatitude() < 49.850 ||
                newBlueLocation.getLatitude() > 49.877) {
            errorMsg = "Latitude ist nicht innerhalb Rossdorf! Muss zw. 49.850 und 49.877 sein.";
            log.error(errorMsg);
        }

        //longitude sollte zw. 8.730 und 8.806 sein
        if (newBlueLocation.getLongitude() < 8.730 ||
                newBlueLocation.getLongitude() > 8.806) {
            errorMsg += "Longitude ist nicht innerhalb Rossdorf! Muss zw. 8.730 und 8.806 sein.";
            log.error(errorMsg);
        }

        return errorMsg;
    }
}
