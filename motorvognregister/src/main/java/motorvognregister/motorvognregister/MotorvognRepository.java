package motorvognregister.motorvognregister;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorvognRepository {
    Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);
    @Autowired
    private JdbcTemplate db;

    public boolean registrer(Motorvogn motorvogn) {
        String sql = "INSERT INTO Motorvogn (personnummer, navn, adresse, registreringsnummer, merke, type) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            db.update(sql, motorvogn.getPersonnummer(), motorvogn.getNavn(), motorvogn.getAdresse(),
                    motorvogn.getRegistreringsnummer(), motorvogn.getMerke(), motorvogn.getType());
            return true;
        }
        catch(Exception e) {
            logger.error("Feil i registrer: " + e);
            return false;
        }
    }

    public boolean endre(Motorvogn motorvogn, String personnummer) {
        String sql = "UPDATE Motorvogn " +
                "SET navn=?, adresse=?, registreringsnummer=?, merke=?, type=? " +
                "WHERE personnummer=?";
        try {
            db.update(sql, motorvogn.getNavn(), motorvogn.getAdresse(),
                    motorvogn.getRegistreringsnummer(), motorvogn.getMerke(), motorvogn.getType(), personnummer);
            return true;
        }
        catch(Exception e) {
            logger.error("Feil i endre: " + e);
            return false;
        }
    }

    public List<Motorvogn> vis() {
        String sql = "SELECT * FROM Motorvogn";
        try {
            List<Motorvogn> motorvogn = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));
            return motorvogn;
        }
        catch(Exception e) {
            logger.error("Feil i vis: " + e);
            return null;
        }
    }

    public boolean slett() {
        String sql = "DELETE FROM Motorvogn";
        try {
            db.update(sql);
            return true;
        }
        catch(Exception e) {
            logger.error("Feil i slett: " + e);
            return false;
        }
    }

    public boolean slettRad(String personnummer) {
        String sql = "DELETE FROM Motorvogn WHERE personnummer=?";
        try {
            db.update(sql, personnummer);
            return true;
        }
        catch(Exception e) {
            logger.error("Feil i slettRad" + e);
            return false;
        }
    }

    public List<Bil> hentBiler() {
        String sql = "SELECT * FROM Bil";
        try {
            List<Bil> biler = db.query(sql, new BeanPropertyRowMapper(Bil.class));
            return biler;
        }
        catch (Exception e) {
            logger.error("Feil i hentBiler: " + e);
            return null;
        }
    }

    public boolean sjekkPassord(String passord, String hashPassord) {
        return BCrypt.checkpw(passord, hashPassord);
    }

    public boolean loggInn(String brukernavn, String passord) {
        String sql = "SELECT * FROM Bruker WHERE brukernavn = ?";
        try {
            List<Bruker> brukere = db.query(sql, new BeanPropertyRowMapper(Bruker.class), brukernavn);
            if(brukere != null) {
                if(sjekkPassord(passord, brukere.get(0).getPassord())) {
                    return true;
                }
            }
            return false;
        }
        catch(Exception e) {
            logger.error("Feil i loggInn: " + e);
            return false;
        }
    }

    private String krypterPassord(String passord) {
        return BCrypt.hashpw(passord, BCrypt.gensalt(15));
    }

    public boolean krypter() {
        String sql = "SELECT * FROM Bruker";
        String kryptertPassord;
        try {
            List<Bruker> brukere = db.query(sql, new BeanPropertyRowMapper(Bruker.class));
            for(Bruker b : brukere) {
                kryptertPassord = krypterPassord(b.getPassord());
                sql = "UPDATE Bruker SET passord=? WHERE id=?";
                db.update(sql, kryptertPassord, b.getId());
            }
            return true;
        }
        catch(Exception e) {
            logger.error("Feil i krypter: " + e);
            return false;
        }
    }
}
