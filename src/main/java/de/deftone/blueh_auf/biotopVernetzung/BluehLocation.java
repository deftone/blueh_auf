package de.deftone.blueh_auf.biotopVernetzung;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BluehLocation {

    private Double lattitude; //wo
    private Double longitude;
    private String name;  //wer
}
