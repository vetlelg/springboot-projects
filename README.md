Oblig 3
=======
OsloMet brukernavn: s339862

GitHub brukernavn: vetlelg

Fullt navn: Vetle Larsen Gundersen

Kort beskrivelse av applikasjon (5-10 setninger):
Applikasjon for å registrere kinobilletter. Tar inn billett-informasjon fra bruker via input-felter. Når bruker trykker "kjøp billett" blir informasjonen i input-feltene lagt inn i et objekt på klientsiden og deretter sendes objektet med en POST-request til webserveren som deretter lagrer billetten i en h2 in memory SQL database. Dersom feltene ikke inneholder noe vil det vises en feilmelding ved siden av input-feltet og billett blir ikke registrert. Innholdet i Billett-tabellen i databasen hentes med en GET-request fra klienten og vises i en tabell på klientsiden. Tabellen/listen sorteres på server alfabetisk på etternavn før den sendes til klient. Når man trykker "slett alle billettene" blir det sendt en GET-request til server som kaller en metode som sletter alt innhold i Billett-tabellen i databasen. Billett-tabellen blir opprettet via schema.sql ved oppstart av webapplikasjonen.

Link til YouTube-video som viser oppstart av webapplikasjonen, opprettelse og sletting av billett i applikasjonen og databasen:
https://youtu.be/-j2D15ZuiDo
