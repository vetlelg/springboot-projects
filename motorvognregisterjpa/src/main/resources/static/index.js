document.querySelector('#loggInn').addEventListener('click', () => { loggInn(); });
document.querySelector('#krypter').addEventListener('click', () => { krypter(); });
document.querySelector('#nybruker').addEventListener('click', () => { nybruker(); });
document.querySelector('#leggTilBiler').addEventListener('click', () => { leggTilBiler(); });

function loggInn() {
    const url = "/loggInn?brukernavn="+$("#brukernavn").val()+"&passord="+$("#passord").val();
    $.get(url, (OK) => {
        if(OK) {
            window.location.href = "liste.html";
        } else {
            $("#feil").html("Feil brukernavn eller passord");
        }
    }).fail((jqXHR) => {
        const json = $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function krypter() {
    $.get("/krypter", (OK) => {
        if(OK) {
            $("#feil").html("Kryptering utført!");
        } else {
            $("#feil").html("Feil i kryptering");
        }
    })
        .fail((jqXHR) => {
            const json = $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

function nybruker() {
    $.get("nybruker");
}

function leggTilBiler() {
    $.get("leggTilBiler");
}