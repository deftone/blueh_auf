package de.deftone.blueh_auf.biotopVernetzung;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BluehEvent extends BluehLocation {

    private LocalDate date; //wann

    public BluehEvent(Double lat, Double lon, String name, LocalDate date) {
        this.setLatitude(lat);
        this.setLongitude(lon);
        this.setName(name);
        this.date = date;
    }
}
