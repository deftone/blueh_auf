package de.deftone.blueh_auf.biotopVernetzung;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocation {

    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;

    public GeoLocation(String coordinateString) {
        int indexOfKomma = coordinateString.indexOf(",");
        latitude = Double.valueOf(coordinateString.substring(0, indexOfKomma));
        longitude = Double.valueOf(coordinateString.substring(indexOfKomma + 1));
    }
}
