// Vis billettene når siden lastes
$(vis());

// Event listeners
$('#bestill').on('click', () => { bestill(); });
$('#slett').on('click', () => { slett(); });

// Henter billettene fra server og viser de i tabellen på klient
function vis() {
    // GET-request som sender tilbake et array med billettene
    $.get('vis', (billetter) => {
        // Legger først til første rad i tabellen
        let html =
            '<tr>' +
                '<th>Film</th>' +
                '<th>Antall</th>' +
                '<th>Fornavn</th>' +
                '<th>Etternavn</th>' +
                '<th>Telefonnr</th>' +
                '<th>Epost</th>' +
            '</tr>';
        // Looper gjennom billetter-arrayet og legger til en rad for hver billett
        for (let billett of billetter) {
            html +=
                '<tr>' +
                    `<td>${billett.film}</td>` +
                    `<td>${billett.antall}</td>` +
                    `<td>${billett.fornavn}</td>` +
                    `<td>${billett.etternavn}</td>` +
                    `<td>${billett.telefonnr}</td>` +
                    `<td>${billett.epost}</td>` +
                '</tr>';
        }
        // Setter innholdet i tabell-elementet lik html-variabelen
        $('#billetter').html(html);
    });
};

// Henter data fra input-feltene og sender billett til server
function bestill() {
    // Henter data fra alle feltene og lagrer de i et objekt
    const billett = {
        film: $('#film').val(),
        antall: $('#antall').val(),
        fornavn: $('#fornavn').val(),
        etternavn: $('#etternavn').val(),
        telefonnr: $('#telefonnr').val(),
        epost: $('#epost').val()
    };

    // Inputvalidering. Returnerer hvis ikke alle feltene inneholder noe
    if (inputValidering(billett)) { return; }

    // Sender en POST-request med billett-objektet til server
    $.post('bestill', billett, () => {
        // Blanker ut alle feltene og viser alle billettene i tabellen
        $('#form')[0].reset();
        vis();
    });
};

// Sletter alle billettene på server
function slett() {
    // Sender en GET-request til server som kjører en metode som sletter alle billettene
    $.get('slett', () => { vis(); });
};

// Tar inn billett-objekt som inneholder data fra input-feltene
function inputValidering(billett) {
    let inputErTom = false;

    // Henter keys fra key-value pairs i billett-objektet og lagrer de i et array
    let keys = Object.keys(billett);

    // Itererer gjennom alle input-feltene i billett-objektet
    for (let i = 0; i < keys.length; i++) {

        // Hvis input-felt er tomt, sett feilmelding som inneholder key
        if (billett[keys[i]] === "" || billett[keys[i]]  === null) {
            inputErTom = true;
            $(`#feilmelding-${keys[i]}`).html(`Mangler ${keys[i]}`);
        } else {
            // Fjern feilmelding hvis input-felt inneholder noe
            $(`#feilmelding-${keys[i]}`).html("");
        }
    }
    // Returnerer false hvis alle input-felt har innhold
    // Returnerer true hvis minst ett input-felt er tomt
    return inputErTom;
}