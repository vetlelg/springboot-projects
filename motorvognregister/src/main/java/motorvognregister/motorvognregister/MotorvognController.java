package motorvognregister.motorvognregister;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class MotorvognController {
    private Logger logger = LoggerFactory.getLogger(MotorvognController.class);

    @Autowired
    private MotorvognRepository rep;

    @Autowired
    private HttpSession session;

    @GetMapping("hentBiler")
    public List<Bil> hentBiler(HttpServletResponse response) throws IOException {
        List<Bil> biler = rep.hentBiler();
        if(biler == null) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen.");
        }
        return biler;
    }

    @PostMapping("registrer")
    public void registrer(@RequestBody Motorvogn motorvogn, HttpServletResponse response) throws IOException {
        if(validerMotorvognOK(motorvogn)) {
            if (!rep.registrer(motorvogn)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen.");
            }
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil");
        }
    }

    @PostMapping("endre")
    public void endre(Motorvogn motorvogn, String personnummer, HttpServletResponse response) throws IOException {
        if(validerMotorvognOK(motorvogn)) {
            if (!rep.endre(motorvogn, personnummer)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen");
            }
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Valideringsfeil");
        }
    }

    @GetMapping("slett")
    public void slett(HttpServletResponse response) throws IOException {
        if(!rep.slett()) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen.");
        }
    }

    @GetMapping("slettRad")
    public void slettRad(String personnummer, HttpServletResponse response) throws IOException {
        if(!rep.slettRad(personnummer)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen.");
        }
    }

    @GetMapping("vis")
    public List<Motorvogn> vis(HttpServletResponse response) throws IOException {
        if((boolean)session.getAttribute("loggetInn")) {
            List<Motorvogn> motorvogner = rep.vis();
            if(motorvogner == null) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen.");
            }
            return motorvogner;
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Du må logge inn.");
            return null;
        }
    }

    @GetMapping("loggInn")
    public boolean loggInn(String brukernavn, String passord) {
        if(rep.loggInn(brukernavn, passord)) {
            session.setAttribute("loggetInn", true);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/loggUt")
    public void loggUt() {
        session.setAttribute("loggetInn", false);
    }

    @GetMapping("/krypter")
    public boolean krypter(HttpServletResponse response) throws IOException {
        if(rep.krypter()) {
            return true;
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i databasen");
            return false;
        }
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
