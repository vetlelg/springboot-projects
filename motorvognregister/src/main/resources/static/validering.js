function validerPersonnummer() {
    const regexp = /^[0-9]{11}$/;
    const ok = regexp.test($("#personnummer").val());
    if(ok) {
        $("#feilPersonnummer").html("");
        return true;
    } else {
        $("#feilPersonnummer").html("Personnummeret må bestå av 11 siffer");
        return false;
    }
}

function validerNavn() {
    const regexp = /^[a-zA-ZæøåÆØÅ. \-]{2,20}$/;
    const ok = regexp.test($("#navn").val());
    if(ok) {
        $("#feilNavn").html("");
        return true;
    } else {
        $("#feilNavn").html("Navn må bestå av 2 til 20 bokstaver");
        return false;
    }
}

function validerAdresse() {
    const regexp = /^[0-9a-zA-ZæøåÆØÅ. \-]{2,30}$/;
    const ok = regexp.test($("#adresse").val());
    if(ok) {
        $("#feilAdresse").html("");
        return true;
    } else {
        $("#feilAdresse").html("Adressen må bestå av 2 til 30 bokstaver og tall");
        return false;
    }
}

function validerRegistreringsnummer() {
    const regexp = /^[A-Z][A-Z][0-9]{5}$/;
    const ok = regexp.test($("#registreringsnummer").val());
    if(ok) {
        $("#feilRegistreringsnummer").html("");
        return true;
    } else {
        $("#feilRegistreringsnummer").html("Registreringsnummeret må ha to store bokstaver og 5 tall");
        return false;
    }
}

function validerMerke() {
    if(!($("#merke").val() === "Velg merke")) {
        $("#feilMerke").html("");
        return true;
    } else {
        $("#feilMerke").html("Må velge et merke.");
        return false;
    }
}

function ingenValideringsFeilRegistrere() {
    return(validerPersonnummer() && validerNavn() && validerAdresse() &&
        validerRegistreringsnummer() && validerMerke());
}

function ingenValideringsFeilEndre() {
    return(validerNavn() && validerAdresse() &&
        validerRegistreringsnummer() && validerMerke());
}