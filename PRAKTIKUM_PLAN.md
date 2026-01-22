# 🎓 2-Wochen Praktikum: mikrobank

> Ein praktisches Lernprogramm für Schüler (ab 9. Klasse) zur Einführung in professionelle Java-Entwicklung mit einer realistischen Mini-Börse.

**Zeitrahmen:** 2 Wochen (10 Arbeitstage)  
**Zielgruppe:** Anfänger in Java (aber mit Grundkenntnissen)  
**Schwierigkeitsgrad:** ⭐⭐⭐ (Einsteigerfreundlich bis mittelschwer)

---

## 🎯 Überblick

### Woche 1: Implementierung der Core Services
Implementiere die Geschäftslogik der Mini-Börse, sodass alle vorbereiteten Unit Tests **grün** (erfolgreich) werden.

**Lernziele:**
- ✅ Java-Services schreiben
- ✅ Exception-Handling
- ✅ Unit Tests verstehen und debuggen
- ✅ Geschäftslogik in Code umsetzen

### Woche 2: Börsen-Roboter & Test-Driven Development
Schreibe Tests für einen neuen KI-Service (Börsen-Roboter), finde Bugs, und erweiter das System.

**Lernziele:**
- ✅ Tests schreiben (Test-Driven Development)
- ✅ Code-Review und Debugging
- ✅ Neue Services basierend auf bestehenden Services bauen
- ✅ Logisches Denken & Algorithmen

---

# 📅 WOCHE 1: Implementierung

## 🗓️ Tag 1 (Montag) - Projekt-Setup & Verstehen

**⏱️ Zeitaufwand:** 3-4 Stunden  
**🎯 Lernziele:** Projekt struktur verstehen, IDE aufsetzen, erste Tests laufen lassen

### 📋 Aufgaben

#### 1. Projekt verstehen (30 Minuten)
- [ ] README.md ganz durchlesen
- [ ] Alle Dateien in `src/main/java/de/dwpbank/mikrobank/` durchschauen
- [ ] Model-Klassen (`Aktie`, `Konto`) verstehen
- [ ] Die 4 Services (`KontoService`, `PreisService`, etc.) anschauen

#### 2. Tests laufen lassen (30 Minuten)
```bash
# Terminal öffnen, ins Projektverzeichnis, und folgende Befehle ausführen:
mvn clean test
```
- [ ] Schau dir die Test-Ausgabe an
- [ ] Wie viele Tests gibt es? Wie viele davon schlagen fehl?
- [ ] Markiere dir in einer Datei: Wieviele Tests müssen noch grün werden?

#### 3. Eine Test-Datei durchlesen (1-2 Stunden)
- [ ] Öffne `src/test/java/de/dwpbank/mikrobank/KontoServiceTest.java`
- [ ] Lese den Test `einzahlenErhoehtKontostand()` ganz genau
- [ ] Verstehe das AAA-Pattern:
  - **Arrange** (Vorbereitung): Was wird aufgebaut?
  - **Act** (Ausführung): Was wird aufgerufen?
  - **Assert** (Überprüfung): Was wird geprüft?

#### 4. IDE erkunden (30 Minuten)
- [ ] Öffne das Projekt in VS Code oder einer IDE
- [ ] Navigiere zu `KontoService.java`
- [ ] Schau dir die Javadoc-Kommentare an
- [ ] Versuche, auf `KontoService` zu klicken → Es sollte zur Test-Datei führen

### 💡 Tipps

> **"Warum schlagen Tests fehl?"** Die Service-Methoden sind noch leer! Sie haben nur Kommentare, aber keinen echten Code. Deine Aufgabe ist es, diese Tests grün zu machen, indem du den Code schreibst.

> **"Wo finde ich Informationen?"** Die Javadoc-Kommentare in den Services erklären genau, was jede Methode tun soll. Das sind deine **Spezifikationen**!

### ✅ Checkpoint

Beantworte diese Fragen (schreib die Antworten auf):
1. Was ist das AAA-Pattern in Unit Tests?
2. Wie viele Tests gibt es insgesamt?
3. Was muss `KontoService.einzahlen(Konto, 50)` mit einem Konto (Kontostand: 100) machen?

---

## 🗓️ Tag 2 (Dienstag) - KontoService implementieren

**⏱️ Zeitaufwand:** 4 Stunden  
**🎯 Lernziele:** Exception-Handling, Geschäftslogik schreiben, Tests debuggen

### 📋 Aufgaben

#### 1. Spezifikation lesen (30 Minuten)
- [ ] Öffne `src/main/java/de/dwpbank/mikrobank/service/KontoService.java`
- [ ] Lese die Javadoc-Kommentare für `einzahlen()`
- [ ] Frage dich: Welche Regeln muss ich implementieren?
  - Bedingung 1: Betrag > 0?
  - Bedingung 2: Was passiert sonst?

#### 2. `einzahlen()` implementieren (1 Stunde)
```java
// Hier ist ein Skelett:
public void einzahlen(Konto konto, double betrag) {
    // 1. Validierung: Ist betrag > 0?
    if (betrag <= 0) {
        // wirfe Exception
    }
    // 2. Addiere betrag zum Kontostand
    // 3. Logge eine Info-Nachricht mit log.info(...)
}
```

- [ ] Schreibe den Code für `einzahlen()`
- [ ] **Hinweis:** Verwende `if` für die Bedingung
- [ ] **Hinweis:** Verwende `throw new IllegalArgumentException("Nachricht")` für Fehler
- [ ] **Hinweis:** Verwende `log.info("...")` für Logging

#### 3. `einzahlen()`-Tests ausführen (1 Stunde)
```bash
mvn test -Dtest=KontoServiceTest#einzahlenErhoehtKontostand
```
- [ ] Der Test sollte grün werden! ✅
- [ ] Führe auch den Fehlerfall-Test aus:
```bash
mvn test -Dtest=KontoServiceTest#einzahlenMitNegativemBetragWirftException
```

#### 4. `auszahlen()` implementieren (1,5 Stunden)
- [ ] Lese die Javadoc für `auszahlen()`
- [ ] Diese Methode hat 2 Validierungen:
  1. Betrag > 0?
  2. Kontostand - Betrag ≥ 0? (nicht negativ!)
- [ ] Implementiere `auszahlen()` ähnlich wie `einzahlen()`
- [ ] Führe die Tests aus, bis sie grün sind

### 💡 Tipps

> **"Wie logge ich?"** Nutze `log.info("Text hier")`. Du brauchst dich nicht selbst um Imports kümmern - Lombok macht das mit der `@Slf4j`-Annotation.

> **"Welche Exception werfe ich?"** Die Test-Dateien verwenden `IllegalArgumentException`. Das ist die Norm für ungültige Eingaben in Java.

> **"Mein Test schlägt fehl. Was tun?"** 
> 1. Lese die Fehlermeldung genau
> 2. Vergleiche: Was erwartet der Test? Was macht dein Code?
> 3. Debugge Schritt für Schritt

### ✅ Checkpoint

- [ ] Führe folgendes aus: `mvn test -Dtest=KontoServiceTest`
- [ ] Zähle: Wie viele Tests sind grün?
- [ ] **Sollte sein:** Alle 11 KontoServiceTest-Tests sollten grün sein ✅

---

## 🗓️ Tag 3 (Mittwoch) - OrderValidierungsService implementieren

**⏱️ Zeitaufwand:** 3 Stunden  
**🎯 Lernziele:** Ähnliche Strukturen erkennen, Code-Duplikation vermeiden

### 📋 Aufgaben

#### 1. Spezifikation verstehen (30 Minuten)
- [ ] Öffne `src/main/java/de/dwpbank/mikrobank/service/OrderValidierungsService.java`
- [ ] Lese die Javadoc für `validiereKauf()` und `validiereVerkauf()`
- [ ] Was ist der Unterschied? **Antwort:** (Hinweis: Es gibt nur einen!)

#### 2. `validiereKauf()` implementieren (1 Stunde)
Diese Methode prüft 2 Dinge:
1. Ist `aktie` nicht null?
2. Ist `menge > 0`?

```java
public void validiereKauf(Aktie aktie, int menge) {
    // 1. Prüfe if (aktie == null)
    // 2. Prüfe if (menge <= 0)
    // Bei Fehler: throw new IllegalArgumentException(...)
}
```

- [ ] Implementiere die Methode
- [ ] Tests ausführen: `mvn test -Dtest=OrderValidierungsServiceTest`

#### 3. `validiereVerkauf()` implementieren (30 Minuten)
- [ ] Diese Methode hat **exakt die gleiche Logik** wie `validiereKauf()`
- [ ] Kopiere den Code oder schreibe ihn analog
- [ ] Tests sollten grün werden

### 💡 Tipps

> **"Die Tests sind sehr ähnlich!"** Du wirst merken: `validiereKauf()` und `validiereVerkauf()` machen das Gleiche. Das ist normal - in der echten Welt würde man hier vielleicht eine gemeinsame Methode schreiben, aber für Anfänger ist Code-Duplikation erstmal ok.

> **"Null-Checks in Java"** Nutze `if (aktie == null)` oder `if (aktie != null)` um zu prüfen.

### ✅ Checkpoint

- [ ] `mvn test -Dtest=OrderValidierungsServiceTest` → Alle grün?
- [ ] Zähle die Tests: Sollten 14 sein

---

## 🗓️ Tag 4 (Donnerstag) - PreisService implementieren

**⏱️ Zeitaufwand:** 3-4 Stunden  
**🎯 Lernziele:** Zufallszahlen, Mathematik in Java, Objekt-Zustand ändern

### 📋 Aufgaben

#### 1. Anforderungen verstehen (30 Minuten)
- [ ] Lese die Javadoc für `ermittleAktuellenPreis()`
- [ ] Verstehe:
  - Der alte Preis der Aktie ist der Startpunkt
  - Dieser Preis ändert sich zufällig um ±5%
  - Der neue Preis darf niemals ≤ 0 sein
  - Der neue Preis wird in der Aktie gespeichert UND zurückgegeben

#### 2. Zufallszahlen in Java (30 Minuten)
```java
// So generierst du Zufallszahlen in Java:
import java.util.Random;

Random random = new Random();
double zufallsZahl = random.nextDouble(); // Zahl zwischen 0.0 und 1.0
```

- [ ] Experimentiere: Erstelle ein kleines Test-Programm
- [ ] Wie kriegst du eine Zahl zwischen -0.05 und +0.05? (Hinweis: -0.05 + (random.nextDouble() * 0.10))

#### 3. `ermittleAktuellenPreis()` implementieren (2 Stunden)

Pseudo-Code:
```
1. Lese den aktuellen Preis aus der Aktie
2. Generiere eine Zufallszahl zwischen -5% und +5%
3. Berechne: neuerPreis = alterPreis * (1 + prozentuale_Aenderung)
4. Falls neuerPreis <= 0, setze neuerPreis auf einen Mindestwert (z.B. 0.01)
5. Speichere den neuen Preis in der Aktie
6. Gib den neuen Preis zurück
```

- [ ] Implementiere diese Logik
- [ ] Tests: `mvn test -Dtest=PreisServiceTest`

#### 4. Tests debuggen (30-60 Minuten)
Falls Tests fehlschlagen:
- [ ] Prüfe: Wird der Preis wirklich verändert?
- [ ] Prüfe: Bleibt der Preis immer positiv?
- [ ] Prüfe: Liegt die Änderung wirklich zwischen ±5%?

### 💡 Tipps

> **"Wie teste ich Zufallszahlen?"** Das ist knifflig! Deshalb testen wir nur: "Ist der neue Preis im erlaubten Bereich?" nicht "War die Änderung exakt 3,5%?"

> **"Math.random() vs Random?"** Beide funktionieren. Nutze das, das dir einfacher vorkommt.

> **"Was ist ein 'Mindestwert'?"** In der Realität kann eine Aktie nicht 0€ kosten. Wir setzen einen Mindestwert wie 0.01€.

### ✅ Checkpoint

- [ ] `mvn test -Dtest=PreisServiceTest` → Alle grün?
- [ ] Zähle: Sollten 8 Tests sein

---

## 🗓️ Tag 5 (Freitag) - BoerseService implementieren

**⏱️ Zeitaufwand:** 4-5 Stunden  
**🎯 Lernziele:** Orchestrierung, Services zusammensetzen, größere Geschäftslogik, Service-Integration

### 📋 Aufgaben

#### 1. Anforderungen verstehen (1 Stunde)
- [ ] Lese die Javadoc für `kaufe()` und die neue `verkaufe()` Methode
- [ ] Der Ablauf ist:
  1. Validiere die Order
  2. Ermittle den aktuellen Preis
  3. Berechne Gesamtkosten/Erlös
  4. Prüfe ob genug Guthaben vorhanden ist
  5. Buche das Geld ab/ein
  6. **NEU:** Speichere den neuen Kurs im KursService!
- [ ] **Wichtig:** Diese Methode nutzt die anderen Services (einschließlich KursService)!

#### 2. Grundgerüst für `kaufe()` schreiben (30 Minuten)
```java
public void kaufe(Konto konto, Aktie aktie, int menge) {
    log.info("Starten Kauforder...");
    
    // 1. Validierung
    validierungsService.validiereKauf(aktie, menge);
    
    // 2. Preis ermitteln
    double preis = preisService.ermittleAktuellenPreis(aktie);
    
    // 3. Gesamtkosten berechnen
    double gesamtkosten = preis * menge;
    
    // 4. Guthaben prüfen
    if (konto.getKontostand() < gesamtkosten) {
        // Fehler!
    }
    
    // 5. Abbuchen
    kontoService.auszahlen(konto, gesamtkosten);
    
    // 6. NEUER SCHRITT: Speichere den Kurs!
    kursService.speichereKurs(aktie);
    
    log.info("Kauforder erfolgreich!");
}
```

- [ ] Tippe das Grundgerüst ab
- [ ] Denke: Welche Exception werfe ich bei "nicht genug Guthaben"? (Hinweis: Lese die Javadoc!)

#### 3. `verkaufe()` Methode implementieren (1 Stunde)
- [ ] Diese Methode funktioniert ähnlich wie `kaufe()`, aber andersherum
- [ ] Schritte:
  1. Validiere mit `validiereVerkauf()`
  2. Ermittle Preis
  3. Berechne Erlös
  4. Zahle auf Konto ein mit `kontoService.einzahlen()`
  5. Speichere Kurs in KursService
- [ ] Siehe Javadoc in BoerseService.java für Details

#### 4. Fehlerbehandlung hinzufügen (1-2 Stunden)
- [ ] Was passiert, wenn Validierung fehlschlägt? → Exception propagiert automatisch
- [ ] Was passiert, wenn nicht genug Guthaben? → Werfe `IllegalStateException`
- [ ] Teste mit: `mvn test -Dtest=BoerseServiceTest`

#### 5. Integration mit KursService prüfen (30 Minuten)
- [ ] Nach jedem Kauf/Verkauf sollte `kursService.speichereKurs(aktie)` aufgerufen werden
- [ ] Das bedeutet: Der aktuelle Preis der Aktie wird in der Kurshistorie gespeichert
- [ ] Der KursService kann dann später von BörsenRoboter für intelligente Entscheidungen genutzt werden

### 💡 Tipps

> **"Die Validierung wirft eine Exception - ist das ok?"** JA! Das ist ein Feature. Wenn `validiereKauf()` eine Exception wirft, wird die `kaufe()`-Methode gestoppt.

> **"Wo speichere ich den Kurs?"** Mit: `kursService.speichereKurs(aktie)` - Das ist eine neue Zeile!

> **"Warum speichere ich den Kurs?"** Damit hat der KursService eine Historien aller Kurse. Der Handelsroboter nutzt diese später zur Analyse!

### ✅ Checkpoint - WOCHE 1 ABSCHLUSS

Führe folgendes aus:
```bash
mvn test
```

**Ziel:** Alle Tests sollten grün sein! 🎉

Zähle die Tests:
- KontoServiceTest: 11 ✅
- PreisServiceTest: 8 ✅
- OrderValidierungsServiceTest: 14 ✅
- BoerseServiceTest: 18 ✅
- **TOTAL: 51 Tests** ✅

Falls nicht alle grün sind:
1. Welche schlagen fehl?
2. Lies die Fehlermeldung
3. Frag einen Mentor oder debugge selbst

---

# 📅 WOCHE 2: Börsen-Roboter & Testing

## 🎯 Lernziele Woche 2

- ✅ Test-Driven Development (TDD) praktizieren
- ✅ Code-Review und Debugging
- ✅ Ein neues Feature selbständig designen
- ✅ Komplexe Logik umsetzen
- ✅ Fehler systematisch finden und fixen

---

## 🗓️ Tag 6 (Montag) - BörsenRoboter/HandelsRoboter verstehen & Tests erweitern

**⏱️ Zeitaufwand:** 4-5 Stunden  
**🎯 Lernziele:** TDD, Service-Design, KursService-Integration, Test-Strategie

### 📋 Aufgaben

#### 1. HandelsRoboter Anforderungen lesen (1 Stunde)
- [ ] Öffne `src/main/java/de/dwpbank/mikrobank/service/HandelsRoboter.java`
- [ ] Lies die LANGE Dokumentation oben in der Klasse!
- [ ] Verstehe die **Handelslogik**:
  - KAUFEN wenn Kurs günstig ist (KursService.istKursGuenstig())
  - VERKAUFEN wenn Kurs teuer ist (KursService.istKursTeuer())
  - Das Depot (Map) speichert gehaltene Aktien
- [ ] Verstehe die Datenstrukturen:
  - `name`: String
  - `konto`: Konto mit Guthaben
  - `depot`: Map<String, Integer> mit Aktienbestanden
  - `boerseService`: Zum Kaufen/Verkaufen
  - `kursService`: Zur Marktanalyse

#### 2. Bestehende Tests verstehen (1 Stunde)
- [ ] Öffne `src/test/java/de/dwpbank/mikrobank/HandelsRoboterTest.java`
- [ ] Es gibt schon 20+ Tests geschrieben!
- [ ] Lese folgende Test-Kategorien:
  - **Teil 1:** Grundlegende Tests (Name, Kapital, Depot)
  - **Teil 2:** Kauf-Tests
  - **Teil 3:** Verkauf-Tests
  - **Teil 4:** Handelssession-Tests
  - **Teil 5:** Gewinn/Vermögen-Tests
  - **Teil 6:** Edge Cases

#### 3. Tests durchführen und sehen welche fehlschlagen (30 Minuten)
```bash
mvn test -Dtest=HandelsRoboterTest
```

- [ ] Wie viele Tests fehlschlagen?
- [ ] Welche sind die einfachsten? (Diese implementierst du zuerst!)

#### 4. Spezifikation verstehen - TODO-Kommentare lesen (1-2 Stunden)
Alle Methoden im HandelsRoboter haben "TODO: Implementiere..." Kommentare.

**Reihenfolge der Implementierung (vom einfach zum schwer):**
1. Konstruktor
2. `getName()`, `getKonto()`, `getDepot()`
3. `besitztAktie()`, `gibAnzahlAktien()`
4. `handleAnEinemTag()` - DIE HAUPTMETHODE!
5. `handeleSession()`
6. `berechnetGesamtvermoegen()`
7. `gibStatus()`

- [ ] Lese die TODO-Kommentare für jede Methode
- [ ] Verstehe was jede Methode machen soll

### 💡 Tipps

> **"Wie schreibe ich gute Tests?"** Denk an die Anforderungen: Was MUSS der Roboter können? Das ist ein Test!

> **"Der Test schlägt fehl, weil der Service nicht existiert"** GENAU! Das ist TDD: Tests schreiben BEVOR man den Code schreibt.

> **"Wie nutzt der Roboter den KursService?"** 
> ```java
> if (kursService.istKursGuenstig(aktie)) {
>     // KAUF-SIGNAL!
> } else if (kursService.istKursTeuer(aktie)) {
>     // VERKAUF-SIGNAL!
> }
> ```

### ✅ Checkpoint

- [ ] HandelsRoboter.java existiert und wird verstanden
- [ ] HandelsRoboterTest.java existiert mit 20+ Tests
- [ ] Tests laufen (viele schlagen fehl, das ist ok!)
- [ ] Du weißt in welcher Reihenfolge du implementieren wirst

---

## 🗓️ Tag 7 (Dienstag) - HandelsRoboter implementieren (Teil 1)

**⏱️ Zeitaufwand:** 4-5 Stunden  
**🎯 Lernziele:** Komplexe Logik umsetzen, Tests grün machen, KursService nutzen

### 📋 Aufgaben

#### 1. Konstruktor implementieren (30 Minuten)
- [ ] Öffne HandelsRoboter.java
- [ ] Navigiere zum Konstruktor
- [ ] Implementiere:
  ```java
  public HandelsRoboter(String name, double startkapital) {
      this.name = name;
      this.konto = new Konto(startkapital);
      this.depot = new HashMap<>();
      log.info("[{}] wurde mit Startkapital {}€ erstellt", name, startkapital);
  }
  ```

#### 2. Einfache Getter implementieren (30 Minuten)
Implementiere die Methoden:
- [ ] `getName()` - gib `this.name` zurück
- [ ] `getKonto()` - gib `this.konto` zurück
- [ ] `getDepot()` - gib `new HashMap<>(this.depot)` zurück (Kopie für Sicherheit!)

**Tests ausführen:**
```bash
mvn test -Dtest=HandelsRoboterTest#handelsroboterHatNamen
mvn test -Dtest=HandelsRoboterTest#handelsroboterHatStartkapital
mvn test -Dtest=HandelsRoboterTest#handelsroboterDepotIstLeer
```

#### 3. Depot-Helper Methoden implementieren (30 Minuten)
- [ ] `besitztAktie(String aktienname)` → `return depot.containsKey(aktienname);`
- [ ] `gibAnzahlAktien(String aktienname)` → `return depot.getOrDefault(aktienname, 0);`

**Tests ausführen:**
```bash
mvn test -Dtest=HandelsRoboterTest#besitztAktieUnbekannt
mvn test -Dtest=HandelsRoboterTest#gibAnzahlAktienUnbekannt
```

#### 4. Die Hauptmethode `handleAnEinemTag()` SKELETON (1-2 Stunden)

Dies ist die komplexeste Methode! Sie ist MASSIV dokumentiert. Hier ist das Skelett:

```java
public void handleAnEinemTag(Aktie aktie) {
    // SCHRITT 1: Validierung
    if (aktie == null) {
        log.warn("[{}] Fehler: Aktie ist null", name);
        throw new IllegalArgumentException("Aktie darf nicht null sein");
    }

    String aktienname = aktie.getName();
    log.debug("[{}] Analysiere Aktie: {}", name, aktienname);

    // SCHRITT 2: Hole Kursinformationen
    boolean istGuenstig = kursService.istKursGuenstig(aktie);
    boolean istTeuer = kursService.istKursTeuer(aktie);
    
    double aktuellerPreis = aktie.getPreis();
    double guthaben = konto.getKontostand();

    // SCHRITT 3: KAUF-LOGIK
    if (istGuenstig && guthaben >= aktuellerPreis) {
        // Berechne wie viele ich kaufen kann
        int maxMoeglich = (int) (guthaben / aktuellerPreis);
        int maxErlaubt = 10 - gibAnzahlAktien(aktienname);
        int anzahlZuKaufen = Math.min(maxMoeglich, maxErlaubt);
        
        if (anzahlZuKaufen > 0) {
            try {
                boerseService.kaufe(konto, aktie, anzahlZuKaufen);
                depot.put(aktienname, gibAnzahlAktien(aktienname) + anzahlZuKaufen);
                log.info("[{}] kauft {}x {}", name, anzahlZuKaufen, aktienname);
            } catch (Exception e) {
                log.warn("[{}] Kaufversuch fehlgeschlagen: {}", name, e.getMessage());
            }
        }
    }
    // SCHRITT 4: VERKAUF-LOGIK
    else if (istTeuer && besitztAktie(aktienname)) {
        int anzahlZuVerkaufen = gibAnzahlAktien(aktienname);
        if (anzahlZuVerkaufen > 0) {
            try {
                boerseService.verkaufe(konto, aktie, anzahlZuVerkaufen);
                depot.remove(aktienname);
                log.info("[{}] verkauft {}x {}", name, anzahlZuVerkaufen, aktienname);
            } catch (Exception e) {
                log.warn("[{}] Verkaufsversuch fehlgeschlagen: {}", name, e.getMessage());
            }
        }
    }
    // SCHRITT 5: HALTEN
    else {
        log.debug("[{}] hält Position in {}", name, aktienname);
    }
}
```

- [ ] Tippe diesen Code ein
- [ ] Teste: `mvn test -Dtest=HandelsRoboterTest#kauftAktienWennGuenstig`

#### 5. Weitere Methoden stub out (1 Stunde)
Implementiere die Stubs für:
- [ ] `handeleSession()` - TODO für später
- [ ] `berechnetGesamtvermoegen()` - return 0.0; (TODO)
- [ ] `gibStatus()` - return ""; (TODO)

### 💡 Tipps

> **"Math.min() Funktion?"** `Math.min(5, 10)` gibt 5 zurück. Perfekt um Maxima zu begrenzen!

> **"Wie nutze ich KursService?"** 
> ```java
> kursService.istKursGuenstig(aktie)  // boolean
> kursService.istKursTeuer(aktie)     // boolean
> ```

> **"Warum try-catch bei boerseService.kaufe()?"** Weil es Exceptions werfen KANN, aber der Roboter sollte dann einfach mit der nächsten Aktie weitermachen

### ✅ Checkpoint

- [ ] Konstruktor grün
- [ ] Getter grün (getName, getKonto, getDepot)
- [ ] Depot-Helper grün (besitztAktie, gibAnzahlAktien)
- [ ] handleAnEinemTag kompiliert und erste Tests laufen
- [ ] Teste: `mvn test -Dtest=HandelsRoboterTest` → Einige Tests sollten jetzt grün sein!

---

## 🗓️ Tag 8 (Mittwoch) - HandelsRoboter implementieren (Teil 2)

**⏱️ Zeitaufwand:** 4-5 Stunden  
**🎯 Lernziele:** Komplexe Geschäftslogik, Debugging, Depot-Verwaltung

### 📋 Aufgaben

#### 1. `handeleSession()` implementieren (1,5 Stunden)

Diese Methode iteriert über mehrere Aktien und lässt den Roboter mit jeder handeln.

```java
public void handeleSession(List<Aktie> aktien) {
    log.info("[{}] Starte Handelssession mit {} Aktien", name, aktien.size());
    
    for (Aktie aktie : aktien) {
        try {
            handleAnEinemTag(aktie);
        } catch (Exception e) {
            log.warn("[{}] Fehler bei Handel von {}: {}", name, aktie.getName(), e.getMessage());
            // Weitermachen mit nächster Aktie!
        }
    }
    
    log.info("[{}] Handelssession beendet. Guthaben: {}€", name, konto.getKontostand());
}
```

- [ ] Implementiere diese Methode
- [ ] Teste: `mvn test -Dtest=HandelsRoboterTest#handelSessionMitMehrerenAktien`

#### 2. `berechnetGesamtvermoegen()` implementieren (1,5 Stunden)

Diese Methode berechnet: **Guthaben + Wert aller gehaltenen Aktien**

Die Herausforderung: Wie kriegst du die aktuellen Preise der Aktien?

**Option A (Einfach):** Speichere die Aktien-Objekte selbst
- [ ] Erstelle eine zweite Map: `Map<String, Aktie> depot_objekte`
- [ ] Speichere beim Kauf: `depot_objekte.put(aktienname, aktie)`
- [ ] Bei Berechnung: Für jede Aktie → preis * anzahl

**Option B (Ohne Änderung):** Nutze KursService
- [ ] Der KursService hat die Kurse in `gibKurshistorie()`
- [ ] Nimm den letzten Kurs: `historie.get(historie.size()-1)`

**Ich empfehle Option A - ändere HandelsRoboter wie folgt:**

```java
private Map<String, Aktie> depot_objekte = new HashMap<>();  // NEU

public void handleAnEinemTag(Aktie aktie) {
    // ... bestehender Code ...
    
    // Bei Kauf:
    depot.put(aktienname, gibAnzahlAktien(aktienname) + anzahlZuKaufen);
    depot_objekte.put(aktienname, aktie);  // NEU: Speichere die Aktie
    
    // Bei Verkauf:
    depot.remove(aktienname);
    depot_objekte.remove(aktienname);  // NEU: Entferne die Aktie
}

public double berechnetGesamtvermoegen() {
    double vermogen = konto.getKontostand();
    
    for (String aktienname : depot.keySet()) {
        int anzahl = depot.get(aktienname);
        Aktie aktie = depot_objekte.get(aktienname);
        if (aktie != null) {
            double wert = anzahl * aktie.getPreis();
            vermogen += wert;
        }
    }
    
    return vermogen;
}
```

- [ ] Implementiere diese Logik
- [ ] Teste: `mvn test -Dtest=HandelsRoboterTest#vermoegensIsMindesitensGuthaben`

#### 3. `gibStatus()` implementieren (1 Stunde)

Diese Methode gibt einen schönen Status-Bericht aus:

```java
public String gibStatus() {
    StringBuilder sb = new StringBuilder();
    sb.append("═══════════════════════════════════════════\n");
    sb.append("Handelsroboter: ").append(name).append("\n");
    sb.append("═══════════════════════════════════════════\n");
    sb.append("Guthaben: ").append(konto.getKontostand()).append("€\n");
    sb.append("Depot: ").append(depot.size()).append(" verschiedene Aktien\n");
    
    for (String aktienname : depot.keySet()) {
        int anzahl = depot.get(aktienname);
        sb.append("  - ").append(aktienname).append(": ").append(anzahl).append(" Stück\n");
    }
    
    double vermoegen = berechnetGesamtvermoegen();
    sb.append("Gesamtvermögen: ").append(vermoegen).append("€\n");
    sb.append("═══════════════════════════════════════════\n");
    
    return sb.toString();
}
```

- [ ] Implementiere diese Methode
- [ ] Teste: `mvn test -Dtest=HandelsRoboterTest#statusBerichenthaltRelevante`

#### 4. Tests debuggen (1-2 Stunden)
```bash
mvn test -Dtest=HandelsRoboterTest
```

- [ ] Wie viele Tests sind jetzt grün?
- [ ] Welche schlagen noch fehl?
- [ ] Debugge mit `log.info()` Statements

### 💡 Tipps

> **"StringBuilder?"** Das ist eine Klasse um Strings effizient zu bauen:
> ```java
> StringBuilder sb = new StringBuilder();
> sb.append("Text");
> sb.append("Mehr Text");
> return sb.toString();
> ```

> **"Wie debugge ich Vermögensberechnung?"** Mit Logging:
> ```java
> log.info("Aktie: {}, Anzahl: {}, Preis: {}", aktienname, anzahl, aktie.getPreis());
> ```

### ✅ Checkpoint

- [ ] `handeleSession()` grün
- [ ] `berechnetGesamtvermoegen()` grün
- [ ] `gibStatus()` grün
- [ ] Teste: `mvn test -Dtest=HandelsRoboterTest` → Mindestens 15+ Tests sollten grün sein

---

---

## 🗓️ Tag 9 (Donnerstag) - Code-Review & Edge Cases

**⏱️ Zeitaufwand:** 3-4 Stunden  
**🎯 Lernziele:** Systematisches Debugging, Code-Qualität, Mentor-Feedback

### 📋 Aufgaben

#### 1. Alle Tests grün machen (1-2 Stunden)
```bash
mvn test -Dtest=HandelsRoboterTest
```

- [ ] Welche Tests schlagen noch fehl?
- [ ] Verwende `mvn test -Dtest=HandelsRoboterTest#testName` für einzelne Tests
- [ ] Debugge jeden fehlgeschlagenen Test

#### 2. Code durchlesen und verstehen (1 Stunde)
- [ ] Öffne deinen HandelsRoboter.java-Code
- [ ] Lese ihn vollständig durch
- [ ] Stellen die dir **unklar** sind, markieren

#### 3. Edge Cases manuell durchspielen (1-2 Stunden)

**Szenario 1: Günstiger Kauf**
```
- Roboter hat 10.000€
- Apple kostet 100€
- Kurs ist günstig
- → Roboter sollte kaufen
- Frage: Hat er nach dem Kauf noch 9.900€? Oder weniger? Richtig?
```

**Szenario 2: Teurer Verkauf**
```
- Roboter hat 5 Apple-Aktien
- Apple-Kurs steigt auf 150€
- Kurs ist teuer
- → Roboter sollte verkaufen
- Frage: Hat er danach wieder 750€ mehr Guthaben?
```

**Szenario 3: Zu viele Aktien**
```
- Roboter versucht 15 Apple-Aktien zu kaufen
- → Sollte max. 10 kaufen!
```

- [ ] Verfolge den Code-Ablauf Schritt für Schritt
- [ ] Stimmt dein Verständnis mit der Implementierung überein?

#### 4. Mit Mentor durchsprechen (1-2 Stunden)
- [ ] Zeige deine Implementation
- [ ] Zeige fehlgeschlagene Tests
- [ ] Frage: "Wo sind Probleme?"
- [ ] Frage: "Wie könnte ich die Logik verbessern?"
- [ ] Frage: "Ist die KursService-Integration richtig?"

---

---

## 🗓️ Tag 10 (Freitag) - Zusammenfassung & Präsentation

**⏱️ Zeitaufwand:** 3-4 Stunden  
**🎯 Lernziele:** Reflection, Kommunikation, Dokumentation

### 📋 Aufgaben

#### 1. Abschließender Test-Run (30 Minuten)
```bash
mvn clean test
```

- [ ] Zähle die Tests insgesamt
- [ ] Wie viel Prozent sind grün?
- [ ] **Ziel: 100%!** 🎉

#### 2. Javadoc-Kommentare verbessern (1 Stunde)
- [ ] Schreib Javadoc-Kommentare für `HandelsRoboter`
- [ ] Erkläre die "Intelligenz" in den Kommentaren
- [ ] Beispiel (wenn noch nicht vorhanden):
```java
/**
 * Handelt mit einer Aktie an einem Tag.
 * 
 * Logik: 
 * - Kaufe wenn kursService.istKursGuenstig() = true
 * - Verkaufe wenn kursService.istKursTeuer() = true
 * - Halte sonst Position
 */
public void handleAnEinemTag(Aktie aktie) { ... }
```

#### 3. Integration Test schreiben (1 Stunde)

Schreibe einen "End-to-End" Test, der zeigt wie alles zusammenhängt:

```java
@Test
@DisplayName("Integration: Kompletter Handelstag mit allen Services")
void integrationTest() {
    // Arrange: Starte mit 10.000€
    HandelsRoboter roboter = new HandelsRoboter("Integration-Bot", 10000);
    List<Aktie> aktien = Arrays.asList(
        new Aktie("Apple", 100),
        new Aktie("BMW", 50),
        new Aktie("SAP", 80)
    );
    
    // Act: Eine komplette Handelssession
    roboter.handeleSession(aktien);
    
    // Assert: Roboter sollte jetzt Aktien haben und/oder Gewinn gemacht haben
    double vermoegen = roboter.berechnetGesamtvermoegen();
    assertTrue(vermoegen > 0);
    System.out.println(roboter.gibStatus());
}
```

- [ ] Schreib diesen Test
- [ ] Führe ihn aus und bestaune den Status-Output!

#### 4. Lerntagebuch schreiben (1-1,5 Stunden)
Schreib eine Datei `WOCHE2_ZUSAMMENFASSUNG.md`:

```markdown
# Woche 2: Börsen-Roboter & Testing

## Was habe ich gelernt?

### Services zusammenbringen
- ✅ KursService in BoerseService integriert
- ✅ HandelsRoboter nutzt BoerseService und KursService
- ✅ Services sprechen miteinander!

### Komplexe Logik schreiben
- ✅ Handelslogik (Kauf/Verkauf basierend auf Kursen)
- ✅ Depot-Verwaltung
- ✅ Gewinnberechnung

### Test-Driven Development (TDD)
- ✅ Tests ZUERST schreiben
- ✅ Dann Implementierung
- ✅ Alle Tests grün!

## Schwierigkeiten

1. Anfangs war handleAnEinemTag() sehr komplex
   → Lösung: Mit einfachen Teilen anfangen (Getter), dann komplexe

2. KursService.speichereKurs() zu vergessen
   → Hinweis: Immer die Integrationspunkte checken!

3. Edge Case: Was wenn ich max 10 Aktien kaufen kann?
   → Mit Math.min() gelöst

## Was würde ich anders machen?

- Depot_objekte Map von Anfang an verwenden
- Zuerst Szenarios aufzeichnen (Kauf/Verkauf)
- Mehr Logging zum Debuggen

## Test-Statistik

- Woche 1: 51 Tests
- Woche 2: 20+ HandelsRoboter Tests
- **Total: 70+ Tests - ALLE GRÜN!** 🎉

## Highlights

Das beste war, als der Roboter zum ersten Mal:
- Eine Aktie kaufte (Guthaben sank)
- Die Aktie verkaufte (Guthaben stieg wieder)
- Gewinn gemacht hat! 📈
```

- [ ] Schreib 3-5 Absätze (ehrlich!)
- [ ] Sei reflektiv: Was war schwierig? Was war leicht?

#### 5. Präsentation vorbereiten (1 Stunde)

Du präsentierst in 10-15 Minuten. Hier ist eine Struktur:

**Folie 1: Überblick**
- Titel: "Handelsroboter für die Mini-Börse"
- Ziel: Automatischer profitabler Handel

**Folie 2: Architektur**
```
     KursService
          ↑
          │ (analyseiert Kurse)
          │
HandelsRoboter → BoerseService → KontoService
                                     ↑
                                     │ (verwaltet Guthaben)
```

**Folie 3: Handelslogik**
```
KAUF wenn: Kurs günstig && Guthaben && < 10 pro Aktie
VERKAUF wenn: Kurs teuer && Aktie im Depot
HALTEN sonst
```

**Folie 4: Beispiel-Szenario**
```
START: 10.000€, Leer Depot

Tag 1: Apple 100€ (günstig)
  → Kaufe 50x → -5.000€

Tag 2: Apple 150€ (teuer)
  → Verkaufe 50x → +7.500€
  
GEWINN: +2.500€! 📈
```

**Folie 5: Test-Strategie**
- 20+ Unit Tests geschrieben
- TDD Ansatz
- Alle Tests grün ✅

**Folie 6: Learnings**
- Service-Architektur
- TDD praktiziert
- Komplexe Business-Logik implementiert

- [ ] Erstelle diese Folien (PowerPoint oder Google Slides)
- [ ] Übe die Präsentation! (10 min sollte passen)

---

# 🏆 Abschluss-Checkliste

## Woche 1
- [ ] KontoService: einzahlen() & auszahlen() ✅
- [ ] PreisService: ermittleAktuellenPreis() ✅
- [ ] OrderValidierungsService: validiereKauf() & validiereVerkauf() ✅
- [ ] BoerseService: kaufe() ✅
- [ ] Alle 51 Tests grün ✅

## Woche 2
- [ ] BörsenRoboter-Tests geschrieben ✅
- [ ] BörsenRoboter implementiert ✅
- [ ] Code-Review durchgeführt ✅
- [ ] Edge Cases getestet ✅
- [ ] Dokumentation vollständig ✅
- [ ] Präsentation vorbereitet ✅

---

# 📚 Ressourcen & Hilfe

### Java-Dokumentation
- https://docs.oracle.com/en/java/javase/17/

### Unit Testing
- https://junit.org/junit5/docs/current/user-guide/

### Best Practices
- Lese die Javadoc-Kommentare im Code
- Schau dir die Test-Dateien an - sie sind Dokumentation!
- Frag einen Mentor, wenn du nicht weiterkommst

### Terminal-Befehle (Cheat Sheet)
```bash
# Kompilieren
mvn clean compile

# Tests ausführen
mvn test
mvn test -Dtest=KontoServiceTest
mvn test -Dtest=KontoServiceTest#einzahlenErhoehtKontostand

# Build
mvn package

# Hilfe
mvn help:active-profiles
```

---

# 🎓 Was du nach 2 Wochen weißt

✅ Wie man professionelle Java-Services schreibt  
✅ Exception Handling  
✅ Unit Tests schreiben und debuggen  
✅ Test-Driven Development (TDD)  
✅ Service-oriented Architecture  
✅ Geschäftslogik in Code umsetzen  
✅ Debugging-Strategien  
✅ Code-Review  

---

**Viel Spaß beim Praktikum! 🚀**

*Falls du Fragen hast: Frag deinen Mentor oder schau in die Javadoc-Kommentare.*
