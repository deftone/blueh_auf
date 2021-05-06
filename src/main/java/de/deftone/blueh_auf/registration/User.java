package de.deftone.blueh_auf.registration;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Size(min = 4, message = "Muss mind. {min} Zeichen lang sein.")
    private String username;
    @Size(min = 4, message = "Muss mind. {min} Zeichen lang sein.")
    private String password;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String email;
    @Version
    private Integer version;

    //allArgsConstr. geht wegen version nicht mehr, deshalb so:
    public User(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
