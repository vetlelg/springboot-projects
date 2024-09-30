// Event listeners
document.addEventListener("DOMContentLoaded", () => {
    // Vis motorvognene når siden lastes
    vis();
});
// Slett alle biler når knapp trykkes
document.querySelector('#slett').addEventListener('click', () => { slett(); });
document.querySelector('#loggUt').addEventListener('click', () => { loggUt(); });

// Henter motorvognene fra server og viser de i tabellen på klient
function vis() {
    fetch('vis')
        .then(response => response.json())
        .then(motorvogner => {
            // Legger først til første rad i tabellen i en variabel
            let html =
                '<tr>' +
                '<th>Personnummer</th>' +
                '<th>Navn</th>' +
                '<th>Adresse</th>' +
                '<th>Registreringsnummer</th>' +
                '<th>Merke</th>' +
                '<th>Type</th>' +
                '<th></th>' +
                '<th></th>' +
                '</tr>';
            // Looper gjennom motorvogner-arrayet og legger til en rad for hver motorvogn
            for (let motorvogn of motorvogner) {
                html +=
                    '<tr>' +
                    `<td>${motorvogn.personnummer}</td>` +
                    `<td>${motorvogn.navn}</td>` +
                    `<td>${motorvogn.adresse}</td>` +
                    `<td>${motorvogn.registreringsnummer}</td>` +
                    `<td>${motorvogn.merke}</td>` +
                    `<td>${motorvogn.type}</td>` +
                    `<td><a href="endre.html?personnummer=${motorvogn.personnummer}"><input type="button" value="Endre"><a></td>` +
                    `<td><input type="button" value="Slett" onclick="slettRad('${motorvogn.personnummer}')"></td>` +
                    '</tr>';
            }
            // Setter innholdet i tabell-elementet lik html-variabelen
            document.querySelector('#motorvogner').innerHTML = html;
        })
        .catch(error => document.querySelector('#motorvogner').innerHTML = error);
    /*$.get('vis', motorvogner => {
        // Legger først til første rad i tabellen i en variabel
        let html =
            '<tr>' +
            '<th>Personnummer</th>' +
            '<th>Navn</th>' +
            '<th>Adresse</th>' +
            '<th>Registreringsnummer</th>' +
            '<th>Merke</th>' +
            '<th>Type</th>' +
            '<th></th>' +
            '<th></th>' +
            '</tr>';
        // Looper gjennom motorvogner-arrayet og legger til en rad for hver motorvogn
        for (let motorvogn of motorvogner) {
            html +=
                '<tr>' +
                `<td>${motorvogn.personnummer}</td>` +
                `<td>${motorvogn.navn}</td>` +
                `<td>${motorvogn.adresse}</td>` +
                `<td>${motorvogn.registreringsnummer}</td>` +
                `<td>${motorvogn.merke}</td>` +
                `<td>${motorvogn.type}</td>` +
                `<td><a href="endre.html?personnummer=${motorvogn.personnummer}"><input type="button" value="Endre"><a></td>` +
                `<td><input type="button" value="Slett" onclick="slettRad('${motorvogn.personnummer}')"></td>` +
                '</tr>';
        }
        // Setter innholdet i tabell-elementet lik html-variabelen
        document.querySelector('#motorvogner').innerHTML = html;
    }).fail((jqXHR) => {
        const json = $.parseJSON(jqXHR.responseText);
        document.querySelector('#motorvogner').innerHTML = json.message;
    });*/
}
// Sletter alle oppføringer i databasen og viser innholdet i tabellen
function slett() { $.get('slett', () => { vis(); }); }
// Sletter en oppføring i databasen og viser innholdet i tabellen
function slettRad(personnummer) { $.get('slettRad?personnummer='+personnummer, () => { vis(); }); }

function loggUt() {
    $.get("/loggUt", () => {
        window.location.href = "/";
    });
}