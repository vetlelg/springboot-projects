package motorvognregister.motorvognregister;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table(name="Motorvogn")
@Data
@NoArgsConstructor
public class Motorvogn {
    @Id
    @GeneratedValue
    private Integer id;
    private String personnummer, navn, adresse, registreringsnummer, merke, type;
    public Motorvogn(String personnummer, String navn, String adresse, String registreringsnummer, String merke, String type) {
        this.personnummer = personnummer;
        this.navn = navn;
        this.adresse = adresse;
        this.registreringsnummer = registreringsnummer;
        this.merke = merke;
        this.type = type;
    }
}
