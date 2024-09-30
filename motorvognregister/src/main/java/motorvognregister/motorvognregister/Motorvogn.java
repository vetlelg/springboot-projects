package motorvognregister.motorvognregister;

public class Motorvogn {

    private String personnummer, navn, adresse, registreringsnummer, merke, type;

    public Motorvogn(String personnummer, String navn, String adresse, String registreringsnummer, String merke, String type) {
        this.personnummer = personnummer;
        this.navn = navn;
        this.adresse = adresse;
        this.registreringsnummer = registreringsnummer;
        this.merke = merke;
        this.type = type;
    }

    public Motorvogn() {
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public void setPersonnummer(String personnummer) {
        this.personnummer = personnummer;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRegistreringsnummer() {
        return registreringsnummer;
    }

    public void setRegistreringsnummer(String registreringsnummer) {
        this.registreringsnummer = registreringsnummer;
    }

    public String getMerke() {
        return merke;
    }

    public void setMerke(String merke) {
        this.merke = merke;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
