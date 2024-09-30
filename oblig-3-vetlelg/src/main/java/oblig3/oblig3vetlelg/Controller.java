package oblig3.oblig3vetlelg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private BillettRepository rep;

    // Håndterer POST-request med billett-objekt fra klient
    // Og sender billetten til metode i repository for lagring i database
    @PostMapping("bestill")
    public void bestill(Billett billett) { rep.bestill(billett); }

    // Håndterer GET-request fra klient
    // Og kaller metode i repository som sletter alle billettene i databasen
    @GetMapping("slett")
    public void slett() { rep.slett(); }

    // Håndterer GET-request fra klient
    // Kaller metode i repository som henter billettene fra databasen og lagrer de i en liste
    // Sorterer listen med billett-objekter alfabestisk på etternavn
    // Sender listen med billetter tilbake til klient
    @GetMapping("vis")
    public List<Billett> vis() {
        List<Billett> billetter = rep.vis();

        // Kaller list sin sort() metode som tar inn en Comparator
        // Definerer Comparatoren med en lambda-expression som
        // sammenligner etternavnene til objektene b1 og b2
        // Sorterer alfabetisk på etternavn og returnerer listen med billetter
        billetter.sort((b1, b2) -> b1.getEtternavn().compareTo(b2.getEtternavn()));
        return billetter;
    }
}
