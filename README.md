# Introductie
Deze workshop is onderdeel van de BEWD course aan de Hogeschool Arnhem Nijmegen.
In deze workshop bouwen we *zelf* eenvoudige authenticatie en autorisatie om te leren wat het is en hoe dit zou kunnen werken.

### Security in Spring Boot?
Het is ongebruikelijk om dit zelf te bouwen.
Spring Boot heeft immers ook hiervoor handige functionaliteit. Deze heeft echter een hoge leercurve.
De kracht zit hem in de magie, maar als het niet werkt heb je veel kennis nodig om dit te herstellen.

### Zelf aan de slag
**Hoofddoel**
Endpoints toegankelijk maken voor bepaalde (groepen) gebruikers.

**Subdoel 1**
(Authenticatie): Op een betrouwbare manier erachter komen wie een gebruiker is (username/password) en een 401 geven als de gebruiker onbekend is.

**Subdoel 2**
(Autorisatie): Per endpoint controleren of de gebruiker er bij mag en een 403 geven als dat niet zo is.

# Workshop
## 1. Clone deze repo
Gebruik je favoriete git client, download de zip van github of clone direct vanuit intellij (File -> new project from version control).
Bekijk de code en run het. Er zijn al een aantal endpoints aanwezig. Test deze met Postman.
In de loop van deze workshop gaan we onderscheid maken in wie deze endpoints mogen benaderen.

## 2. Gebruiken TokenService
Nu kan nog iedereen elke endpoint benaderen. Dat willen we niet. We willen naar een systeem waar je moet inloggen om de juiste toegang te krijgen.
Daarvoor nemen we eerst de bestaande *AuthenticationService* in gebruik.
Deze wordt al geïnjecteerd in de *MovieController*.
Om de methodes te kunnen aanroepen hebben we een gebruikersnaam en wachtwoord nodig.
- Zet een username en een wachtwoord voorlopig als hardcoded String constanten in de *MovieController*.

## 3. De eerste stap naar autorisatie
In de *MovieController* bevindt zich een methode authenticate.
Roep deze aan vanuit iedere endpoint.
Je zult zien dat je op iedere endpoint een exception krijgt.
Bouw een ControllerAdvice die zorgt voor een nette status 401 (unauthorized).
Door de isValidToken methode true laten retourneren ipv false, kun je zorgen dat de exception niet wordt gegooid.
Dit is natuurlijk niet zo netjes. Je wil hier echt controleren.

## 4. Tokens genereren
In de *AuthenticationService* staan de methodes login, isValidRequest en getUsername.
Herschrijf deze methodes zodanig dat er een usertoken wordt gegeneerd en de juiste gegevens worden teruggegeven.

## 5 Login endpoint maken
Maak een methode login die je bindt aan de url /login.
Voor nu maken we er get request van zonder parameters.
In de login methode roep je met je (constante) username en wachtwoord als parameters, de methode authenticationService.login aan.
Bewaar het gegenereerde token in een publieke variabele.
Gebruik deze waarde in de authentication methode.
Nu werken de endpoints alleen als je eerst /login hebt uitgevoerd

## 6 Eindelijk authenticatie: Username en wachtwoord controleren
De volgende stap is het uitbreiden van de /login url met username en wachtwoord.
Voor nu geven we deze als parameters mee in de url.
Verwijder de username en wachtwoord constanten uit de controller. Deze hebben we niet meer nodig. We krijgen het immers als parameter mee.
Controleer *hardcoded* op username wachtwoord in de AuthenticationService. Maak alleen een token als het wachtwoord klopt.
(Met hardcoded wordt bedoeld dat je de gegevens niet hoeft te checken tegen een database, maar dat de juiste waarden 'hard' in de if staan).

## 7. Token meegeven in berichten
Vanaf nu gaan we de token steeds mee geven met ieder bericht.
Hiervoor heb je in Spring Boot de annotatie *@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization*.

Voeg deze als parameter aan je endpoints toe.
Geef vervolgens de *authorization* parameter mee aan de authenticate-methode.

## 8. Een tweede stap naar autorisatie
Het is natuurlijk suf om de username en wachtwoord mee te geven als parameters als je de login aanroept.
Maak daarom nu een user klasse. Deze heeft een username en een wachtwoord.
Geef in plaats van de url parameters een json object mee met daarin username en wachtwoord. Maak van de get een post.

Maak ook een klasse rol. Attributen zijn naam (String) en beheerder (boolean, true indien beheerder.).
Definieer 2 rollen in de constructor van AuthenticationService. Een beheerder en een eindgebruiker.
Maak voor beide rollen een gebruiker met wachtwoord.

## 9. Autorisatie
Bouw nu dat enkel de user met de beheerderrol, films mag toevoegen en verwijderen.

## 10 Gebruik database (deel 1)
Maak de Tabellen in H2 en haal daar de data vandaan (user en movie)

## 11 Hashing
In deze stap ga je het wachtwoord gehashed in de database opslaan.
Vervolgens vergelijk je de hash van het gestuurde wachtwoord met de hash uit de database.
Kijk voor het gebruik van hashes naar stap 5.2 van https://www.baeldung.com/java-password-hashing.
Let op: hier wordt gebruik gemaakt van een salt. Deze moet dus ook worden opgeslagen.

## 12 Encryptie
Onze applicatie is inmiddels behoorlijk veilig.
We hebben authenticatie, autorisatie en de wachtwoorden worden gehashed opgeslagen.
Er is echter nog een potentieel probleem.
De properties voor spring boot staan in plain tekst in de resources map.
Daar kunnen gevoelige gegevens in staan. Zoals je wachtwoord om contact te maken met een database.
Deze gevoelige properties wil je versleuteld opslaan.

- Lees  https://www.baeldung.com/spring-boot-jasypt/
- Bedenk een property waarvan je de waarde versleuteld op wil slaan en doe dit.
- Schrijf een test waarmee je aantoont dat de encryptie werkt.

## 13 Gebruik database (deel 2)
We hebben nu de user en de movie in de database. Dat is eigenlijk niet voldoende.
Je wil daar ook de rollen definieren en daar de gebruiker aan koppelen.
- Maak een tabel "roles" (name, description) en een tabel user_roles (username, rolename) aan.
- Pas vervolgens je code zo aan dat de rol hier vandaan wordt gehaald.
- Pas ook je code aan dat op deze rol wordt gecontroleerd ipv op de boolean.

## EXTRA Omzetten naar Spring Boot
De database staat nu goed en de endpoints ook. Authenticatie en autorisatie hebben we handmatig gebouwd.
Spring Boot biedt hier echter oplossingen voor.
- Ga op zoek naar bronnen om dit op te lossen met Spring Boot
- Pas deze oplossingen toe.
- Wat is je mening hierover? Makkelijk, moeilijk, overzichtelijk, of juist niet?

De bron voor deze oplossing is https://www.javaguides.net/2024/01/spring-boot-security-jwt-tutorial.html