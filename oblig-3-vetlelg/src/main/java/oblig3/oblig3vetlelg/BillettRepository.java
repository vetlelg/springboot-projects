package oblig3.oblig3vetlelg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BillettRepository {
    @Autowired
    private JdbcTemplate db;

    // Tar inn billett fra controlleren og lagrer billetten i Billett-tabellen i databasen
    public void bestill(Billett billett) {
        String sql = "INSERT INTO Billett(film, antall, fornavn, etternavn, telefonnr, epost) VALUES(?,?,?,?,?,?)";
        db.update(sql, billett.getFilm(), billett.getAntall(), billett.getFornavn(),
                billett.getEtternavn(), billett.getTelefonnr(), billett.getEpost());
    }

    // Sletter alle billettene i Billett-tabellen i databasen
    public void slett() {
        String sql = "DELETE FROM Billett";
        db.update(sql);
    }

    // Returnerer alle billettene fra Billett-tabellen i databasen som en liste med Billett-objekter
    public List<Billett> vis() {
        String sql = "SELECT * FROM Billett";
        return db.query(sql, new BeanPropertyRowMapper(Billett.class));
    }
}
