package motorvognregister.motorvognregister;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

@Entity
@Table(name="Bil")
@Data
@NoArgsConstructor
public class Bil {
    @Id
    @GeneratedValue
    private Integer id;
    private String merke, type;
    public Bil(String merke, String type) {
        this.merke = merke;
        this.type = type;
    }
}
