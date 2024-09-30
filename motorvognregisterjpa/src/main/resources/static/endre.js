// Event listeners
document.addEventListener("DOMContentLoaded", () => { hentBiler(); });
document.querySelector('#navn').addEventListener('change', () => { validerNavn(); });
document.querySelector('#adresse').addEventListener('change', () => { validerAdresse(); });
document.querySelector('#registreringsnummer').addEventListener('change', () => { validerRegistreringsnummer(); });
document.querySelector('#merke').addEventListener('change', () => { finnTyper(); validerMerke(); });
document.querySelector('#endre').addEventListener('click', () => { endre(); });
document.querySelector('#personnummer').addEventListener('change', () => { validerPersonnummer(); });


function endre() {
    const motorvogn = {
        id: '',
        personnummer: document.querySelector('#personnummer').value,
        navn: document.querySelector('#navn').value,
        adresse: document.querySelector('#adresse').value,
        registreringsnummer: document.querySelector('#registreringsnummer').value,
        merke: document.querySelector('#merke').value,
        type: document.querySelector('#type').value
    };

    if(ingenValideringsFeil()) {
        $.post('endre?'+window.location.search.substring(1), motorvogn, () => {
            window.location.href = '/liste.html'
        });
    }

}

// Henter bilmerker fra server og legg til i dropdown meny på klient
function hentBiler() {
    $.get("hentBiler", biler => {
        let ut = "<option>Velg merke</option>";

        const merker = []
        for (const bil of biler) {
            if (!merker.includes(bil.merke)) {
                merker.push(bil.merke);
                ut += "<option>" + bil.merke + "</option>";
            }
        }
        document.querySelector('#merke').innerHTML = ut;
    });
}

// Hent typer fra server basert på hvilket merke som er valgt og legg til i dropdown meny på klient
function finnTyper() {
    const valgtMerke = document.querySelector('#merke').value;
    $.get("hentBiler", biler => {
        let ut = "";
        for (const bil of biler) {
            if (bil.merke === valgtMerke) {
                ut += "<option>" + bil.type + "</option>";
            }
        }
        document.querySelector('#type').innerHTML = ut;
    });
}