package de.deftone.blueh_auf.biotopVernetzung;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BluehLocation {

    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;
}
