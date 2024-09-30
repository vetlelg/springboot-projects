CREATE TABLE Motorvogn
(
    personnummer VARCHAR(255) NOT NULL,
    navn VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    registreringsnummer VARCHAR(255) NOT NULL,
    merke VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    PRIMARY KEY (personnummer)
);

CREATE TABLE Bil
(
    id INTEGER AUTO_INCREMENT NOT NULL,
    merke VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Bruker
(
    id INTEGER AUTO_INCREMENT NOT NULL,
    brukernavn VARCHAR(255) NOT NULL,
    passord VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);