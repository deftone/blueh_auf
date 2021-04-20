package de.deftone.blueh_auf.biotopVernetzung;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BluehEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Double latitude; //wo
    @NonNull
    private Double longitude;
    //    @NonNull
//    private String name;  //wer
    private LocalDate date; //wann

    public BluehEvent(BluehLocation bluehLocation){
        this.latitude = bluehLocation.getLatitude();
        this.longitude= (bluehLocation.getLongitude());
//        this.name = (bluehLocation.getName());
        this.date = (LocalDate.now());
    }
}
