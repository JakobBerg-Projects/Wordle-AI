# Runtime Analysis
For each method of the tasks give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well, e.g. any helper methods. You need to show how you analyzed any methods used by the methods listed below.**

The runtime should be expressed using these three parameters:
   * `n` - number of words in the list allWords
   * `m` - number of words in the list possibleWords
   * `k` - number of letters in the wordleWords


## Task 1 - matchWord
* `WordleAnswer::matchWord`: O(k)
    Algoritmen for å matche et gjetning med et svar i Wordle har en tidskompleksitet på O(k), der k er lengden på ordene (både gjetningen og svaret). 

    Initialisering av array feedback og array isGreen hvor begge har lengde k vil begge ha kompleksitet O(k)
    
    Markering av Eksakte (Grønne) Matcher:
    for (int i = 0; i < wordLength; i++) 
    I den første løkken itererer algoritmen gjennom hver karakter i gjetningen og svaret én gang O(k).
    Den sammenligner hver karakter på samme posisjon i gjetningen og svaret.
    Hvis karakterene stemmer overens, oppdateres feedback og isGreen arrays.
    Hvis ikke, oppdateres answerCharCount for å telle forekomsten av den aktuelle bokstaven i svaret.
    Disse operasjonene gjøres i konstant tid O(1)

    Håndtering av Delvise (Gule) Matcher:
    for (int i = 0; i < wordLength; i++) 
    I den andre løkken itererer algoritmen igjen gjennom hvert tegn O(k).
    Den sjekker om karakteren i gjetningen finnes i answerCharCount (som ble fylt ut i første løkke).
    Hvis karakteren er til stede, oppdateres feedback og teller redusert.
    Disse operasjonene gjøres i konstant tid O(1)

    Til slutt oppretter og returnerer metoden et nytt WordleWord objekt, som har tidskompleksitet O(k).

    Siden algoritmen utfører flere separate O(k)-operasjoner, forblir den totale tidskompleksiteten O(k). Hver operasjon er uavhengig av hverandre og kjøres sekvensielt.

## Task 2 - EliminateStrategy
* `WordleWordList::eliminateWords`: O(m*k)
    Algoritmen for eliminateWords har en tidskompleksitet på O(m*k), der m er antall mulige svar (ord) i possibleAnswers, og k er lengden på hvert ord.

    Iterasjon over Mulige Svar:
    Algoritmen itererer gjennom listen possibleAnswers, som inneholder alle mulige ord. Denne operasjonen er O(m), der m er antallet ord i listen.
    
    Sjekk for Hvert Ord:
    For hvert ord i possibleAnswers kalles metoden WordleWord.isPossibleWord(word, feedback).
    Possible Word kaller igjen på matchword, som vi allerede har vist har en tidskompleksitet på O(k)
    k er lengden på ordet (f.eks. 5 bokstaver for Wordle).
    Dette betyr at for hvert ord sjekker algoritmen om det er et gyldig ord basert på tilbakemeldingen.

    Fjerning av Ugyldige Ord:
    Hvis et ord er mulig basert på tilbakemeldingen, legges det til i den nye possible word listen. Dette vil gjennomsnittlig være en O(1) operasjon, men worst 
    case vil være når et nytt Array må opprettes, og alle elementene må kopieres over (Da vil det være O(n)).
    Etter at alle ordene er vurdert, settes den nye listen som nåværende liste, som er en O(1) operasjon.


    Kombinert Tidskompleksitet:
    Den totale kjøretiden for eliminateWords er derfor O(m * k), fordi du gjør m sjekker, hver med en kostnad på O(k).

## Task 3 - FrequencyStrategy
* `FrequencyStrategy::makeGuess`: O(m^2*k)
    Algoritmen i FrequencyStrategy har en tidskompleksitet på O(m^2 * k), der m er antall mulige svar i guesses.possibleAnswers() og 
    k er lengden på hvert ord (typisk 5 bokstaver i Wordle). 

    Eliminering av umulige ord:
    Dersom funksjonen mottar feedback vil det gjøres et kall til eliminateWords, som vi nettopp har vist har en tidskompleksitet på O(m*k)

    Iterasjon over Mulige Ord:
    Algoritmen itererer gjennom listen av mulige ord i guesses.possibleAnswers(), som inneholder 
    m ord. Denne iterasjonen har en kostnad på O(m).

    Telling av Grønne Matcher:
    For hvert ord kalles metoden WordleWordList.countGreenMatches(possibleWord, guesses.possibleAnswers()).
    Countgreenmatches går over alle ord i possible answers og teller bokstavene på hver posisjon. Dette har en kompleksitet på O(m * k) der m er antall mulige ord, og k er lengden på hvert ord.
    Dermed blir den totale kostnaden for å beregne grønne matcher for alle mulige ord O(m*k).

    Valg av Beste Ord:
    Algoritmen sammenligner antall grønne matcher for hvert ord og oppdaterer bestString hvis det nåværende ordet har flere grønne matcher enn tidligere beste valg. Denne operasjonen er O(1) og påvirker ikke den totale tidskompleksiteten.

    Total Kjøretid
    Den totale tidskompleksiteten for FrequencyStrategy blir derfor O(m*k) + O(m) * O(m*k) = O(m*k + m^2 * k) = O(m^2*k) på grunn av de dominerende operasjonene som involverer telling av grønne matcher.



# Task 4 - Make your own (better) AI
For this task you do not need to give a runtime analysis. 
Instead, you must explain your code. What was your idea for getting a better result? What is your strategy?

Min ide var å få mest mulig informasjon ut av de første gjettene. Jeg har tatt utgangspunkt i Frequency Strategien, men med visse endringer. 
Blant annet har jeg satt et "treshold" for å få mest mulig informasjon ut av de første gjettene. Dersom størrelsen på possibleAnswers ikke har blitt minst halvert sin opprinnelige dictionairy størrrelse utifra feedbacken, vil jeg at den skal velge et ord med forskjellige bokstaver, og at bokstaven ikke skal ha blitt brukt i et tidligere gjett. Dersom denne tresholden ikke er nådd, og ordet har like bokstaver i seg, eller at ordet er brukt før. Vil metoden hoppe over til neste ord i listen. Dette ser ut til å fungere godt, og har i følge AIPerformance redusert gjennomsnittlig gjett i forhold til frequency fra 3,93 til 3,63 i tillegg til at den gjetter 199/200 ord i forhold til 197/200.
