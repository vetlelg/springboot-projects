package motorvognregister.motorvognregister;

import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MotorvognController {
    private Logger logger = LoggerFactory.getLogger(MotorvognController.class);

    @Autowired
    private MotorvognRepository motorvognRepository;
    @Autowired
    private BilRepository bilRepository;
    @Autowired
    private BrukerRepository brukerRepository;
    @Autowired
    private HttpSession session;

    @GetMapping("hentBiler")
    public List<Bil> hentBiler(HttpServletResponse response) throws IOException {
        try {
            List<Bil> biler = bilRepository.findAll();
            return biler;
        } catch (Exception e) {
            logger.error("Feil ved spørring mot databasen." + e);
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil ved spørring mot databasen" + e);
            return null;
        }
    }
    @GetMapping("leggTilBiler")
    public void leggTilBiler() {
        bilRepository.save(new Bil("Volkswagen","Polo"));
        bilRepository.save(new Bil("Volkswagen","Beetle"));
        bilRepository.save(new Bil("Toyota","Corolla"));
        bilRepository.save(new Bil("Suzuki","Swift"));
        bilRepository.save(new Bil("Suzuki","Cultus"));
        bilRepository.save(new Bil("Suzuki","Alto"));
    }

    @PostMapping("registrer")
    public void registrer(@RequestBody Motorvogn motorvogn, HttpServletResponse response) throws IOException {
        if(validerMotorvognOK(motorvogn)) {
            try {
                motorvognRepository.save(motorvogn);
            } catch (Exception e) {
                logger.error("Feil ved lagring i databasen." + e);
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil ved lagring i databasen." + e);
            }
        } else {
            logger.error("Valideringsfeil.");
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil");
        }
    }

    @PostMapping("endre")
    public void endre(Motorvogn motorvogn, Integer id, HttpServletResponse response) throws IOException {
        if(validerMotorvognOK(motorvogn)) {
            try {
                motorvogn.setId(id);
                motorvognRepository.save(motorvogn);
            } catch (Exception e) {
                logger.error("Feil ved endring i databasen." + e);
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil ved endring i databasen." + e);
            }
        } else {
            logger.error("Valideringsfeil");
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil");
        }
    }

    @GetMapping("slett")
    public void slett(HttpServletResponse response) throws IOException {
        try {
            motorvognRepository.deleteAll();
        } catch (Exception e) {
            logger.error("Feil ved sletting i databasen" + e);
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil ved sletting.");
        }
    }

    @GetMapping("slettRad")
    public void slettRad(String id, HttpServletResponse response) throws IOException {
        try {
            motorvognRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Feil ved sletting i databasen" + e);
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil ved sletting.");
        }
    }

    @GetMapping("vis")
    public List<Motorvogn> vis(HttpServletResponse response) throws IOException {
        if((boolean)session.getAttribute("loggetInn")) {
            try {
                List<Motorvogn> motorvogner = motorvognRepository.findAll();
                return motorvogner;
            } catch (Exception e) {
                logger.error("Feil ved spørring i databasen" + e);
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil ved spørring i databasen." + e);
                return null;
            }
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Du må logge inn.");
            return null;
        }
    }

    @GetMapping("loggInn")
    public boolean loggInn(String brukernavn, String passord) {
        try {
            List<Bruker> brukere = brukerRepository
                    .findAll()
                    .stream()
                    .filter(bruker -> bruker.getBrukernavn().equals(brukernavn))
                    .collect(Collectors.toList());
            Bruker bruker = brukere.get(0);
            if(BCrypt.checkpw(passord, bruker.getPassord())) {
                session.setAttribute("loggetInn", true);
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            logger.error("Feil i loggInn: " + e);
            return false;
        }
    }

    @GetMapping("/loggUt")
    public void loggUt() {
        session.setAttribute("loggetInn", false);
    }

    @GetMapping("/krypter")
    public boolean krypter(HttpServletResponse response) throws IOException {
        try {
            List<Bruker> brukere = brukerRepository.findAll();
            for (Bruker b : brukere) {
                b.setPassord(BCrypt.hashpw(b.getPassord(), BCrypt.gensalt(15)));
                brukerRepository.save(b);
            }
            return true;
        } catch (Exception e) {
            logger.error("Feil i databasen ved kryptering" + e);
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen ved kryptering" + e);
            return false;
        }
    }

    @GetMapping("nybruker")
    public void nybruker() {
        String passord = BCrypt.hashpw("Admin1234", BCrypt.gensalt(15));
        brukerRepository.save(new Bruker("Admin", passord));
    }

    private boolean validerMotorvognOK(Motorvogn motorvogn) {
        String regexPersonnummer = "[0-9]{11}";
        String regexNavn = "[a-zA-ZæøåÆØÅ. \\-]{2,20}";
        String regexAdresse = "[0-9a-zA-ZæøåÆØÅ. \\-]{2,30}";
        String regexRegistreringsnummer = "[A-Z][A-Z][0-9]{5}";
        String regexMerke = "[0-9a-zA-ZæøåÆØÅ. \\-]{2,10}";

        boolean personnummerOK = motorvogn.getPersonnummer().matches(regexPersonnummer);
        boolean navnOK = motorvogn.getNavn().matches(regexNavn);
        boolean adresseOK = motorvogn.getAdresse().matches(regexAdresse);
        boolean registreringsnummerOK = motorvogn.getRegistreringsnummer().matches(regexRegistreringsnummer);
        boolean merkeOK = motorvogn.getMerke().matches(regexMerke);
        if (!personnummerOK) {
            logger.error("Valideringsfeil personnummer");
        }
        if (!navnOK) {
            logger.error("Valideringsfeil navn");
        }
        if (!adresseOK) {
            logger.error("Valideringsfeil adresse");
        }
        if (!registreringsnummerOK) {
            logger.error("Valideringsfeil registreringsnummer");
        }
        if (!merkeOK) {
            logger.error("Valideringsfeil merke");
        }
        if (personnummerOK && navnOK && adresseOK && registreringsnummerOK && merkeOK) {
            return true;
        }
        return false;

    }

}
