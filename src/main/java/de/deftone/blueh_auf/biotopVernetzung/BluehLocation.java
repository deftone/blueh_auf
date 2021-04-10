package de.deftone.blueh_auf.biotopVernetzung;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BluehLocation {

    @NonNull
    private Double latitude; //wo
    @NonNull
    private Double longitude;
    @NonNull
    private String name;  //wer
}
