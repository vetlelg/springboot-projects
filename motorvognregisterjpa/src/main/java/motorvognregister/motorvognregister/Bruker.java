package motorvognregister.motorvognregister;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Bruker")
@Data
@NoArgsConstructor
public class Bruker {
    @Id
    @GeneratedValue
    private Integer id;
    private String brukernavn, passord;

    public Bruker(String brukernavn, String passord) {
        this.brukernavn = brukernavn;
        this.passord = passord;
    }
}
