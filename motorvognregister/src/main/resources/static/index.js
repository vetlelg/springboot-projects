document.querySelector('#loggInn').addEventListener('click', () => { loggInn(); });
document.querySelector('#krypter').addEventListener('click', () => { krypter(); });

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