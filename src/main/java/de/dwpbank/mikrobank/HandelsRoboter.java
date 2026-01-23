package de.dwpbank.mikrobank;

import de.dwpbank.mikrobank.model.Aktie;
import de.dwpbank.mikrobank.model.Konto;
import de.dwpbank.mikrobank.service.BoerseService;
import de.dwpbank.mikrobank.service.KursService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * HandelsRoboter - Ein automatisierter Tradingsystem für die Mini-Börse
 * <p>
 * Der HandelsRoboter führt automatisch Kauf- und Verkaufsentscheidungen durch,
 * um Gewinn zu erzielen. Dazu nutzt er:
 * <p>
 * 1. **BoerseService**: Zum Kaufen und Verkaufen von Aktien
 * 2. **KursService**: Zur Analyse von Kurstrends und Entscheidungsfindung
 * 3. **Eigenes Depot**: Verwaltung der gehaltenen Aktien
 * <p>
 * ZIEL: Maximiere den Gewinn durch intelligentes Kaufen und Verkaufen!
 * <p>
 * ═══════════════════════════════════════════════════════════════════════════
 * <p>
 * HANDELSLOGIK (Algorithmus):
 * <p>
 * Der Roboter folgt dieser Entscheidungslogik pro Handelstag:
 * <p>
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ SCHRITT 1: ANALYSIERE DEN MARKT                                         │
 * │                                                                         │
 * │ Für jede Aktie:                                                         │
 * │   - Preis ermitteln (PreisService wird von BoerseService genutzt)       │
 * │   - Trend analysieren (über KursService)                                │
 * │   - Mit Durchschnittskurs vergleichen                                   │
 * │   - Entscheidung treffen: KAUFEN? oder VERKAUFEN? oder HALTEN?          │
 * └─────────────────────────────────────────────────────────────────────────┘
 * <p>
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ SCHRITT 2: KAUFENTSCHEIDUNG                                             │
 * │                                                                         │
 * │ KAUFEN WENN:                                                            │
 * │   ✓ Kurs ist günstig (KursService.istKursGuenstig())                    │
 * │   ✓ Roboter hat genug Guthaben                                          │
 * │   ✓ Roboter hat noch nicht zu viele dieser Aktie                        │
 * │                                                                         │
 * │ KAUF-LOGIK:                                                             │
 * │   1. Hole aktuellenPreis via BoerseService → PreisService               │
 * │   2. Berechne: Wieviele Aktien kann ich kaufen?                         │
 * │      → Anzahl = guthaben / aktuellPrice (aber min. 1, max. 10)          │
 * │   3. Rufe boerseService.kaufe(konto, aktie, anzahl) auf                 │
 * │   4. Speichere die Aktien im internen Depot: depot.put(aktie, anzahl)   │
 * │   5. KursService wird automatisch von BoerseService aktualisiert        │
 * └─────────────────────────────────────────────────────────────────────────┘
 * <p>
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ SCHRITT 3: VERKAUFSENTSCHEIDUNG                                         │
 * │                                                                         │
 * │ VERKAUFEN WENN:                                                         │
 * │   ✓ Roboter besitzt diese Aktie (depot.containsKey(aktie))              │
 * │   ✓ Kurs ist teuer (KursService.istKursTeuer())                         │
 * │   ✓ Roboter hat mindestens 1 Aktie davon                                │
 * │                                                                         │
 * │ VERKAUFS-LOGIK:                                                         │
 * │   1. Hole Anzahl der Aktien aus Depot                                   │
 * │   2. Rufe boerseService.verkaufe(...) auf                               │
 * │   3. Entferne Aktien aus Depot oder reduziere Anzahl                    │
 * │   4. Guthaben wird automatisch vom BoerseService aktualisiert           │
 * │   5. KursService wird automatisch von BoerseService aktualisiert        │
 * └─────────────────────────────────────────────────────────────────────────┘
 * <p>
 * ═══════════════════════════════════════════════════════════════════════════
 * <p>
 * DATENSTRUKTUREN:
 * <p>
 * - name: String
 *     Der Name des Roboters (z.B. "RoboTrader", "AlgoBot")
 * <p>
 * - konto: Konto
 *     Das Bankkonto mit dem Startkapital
 * <p>
 * - depot: Map<String, Integer>
 *     Speichert welche Aktien der Roboter besitzt
 *     Key: Aktienname (z.B. "Apple", "BMW")
 *     Value: Anzahl der Aktien
 *     Beispiel: {"Apple": 5, "BMW": 3} → Roboter hat 5 Apple + 3 BMW
 * <p>
 * - boerseService: BoerseService
 *     Für Kauf und Verkauf von Aktien
 * <p>
 * - kursService: KursService
 *     Für Marktanalyse und Trendinformationen
 * <p>
 * ═══════════════════════════════════════════════════════════════════════════
 * <p>
 * METHODEN ZUM IMPLEMENTIEREN:
 *
 * @author Praktikant
 * @version 1.0
 */
@Slf4j
public class HandelsRoboter {

    private String name;
    private Konto konto;
    private Map<String, Integer> depot; // Name → Anzahl der Aktien

    private final BoerseService boerseService = new BoerseService();
    private final KursService kursService = new KursService();

    /**
     * Konstruktor: Erstellt einen neuen Handelsroboter
     *
     * AUFGABE:
     * Initialisiere:
     * 1. Den Namen des Roboters (speichern)
     * 2. Ein neues Konto mit dem Startkapital
     * 3. Ein leeres Depot (HashMap)
     * 4. Logge: "[name] wurde mit Startkapital [betrag]€ erstellt"
     *
     * @param name Der Name des Roboters (z.B. "TradeBot2000")
     * @param startkapital Das verfügbare Budget zum Starten (z.B. 100000)
     */
    public HandelsRoboter(String name, double startkapital) {
        // TODO: Implementiere den Konstruktor
        // HINWEIS: Nutze new Konto(startkapital) für das Konto
        // HINWEIS: Nutze new HashMap<>() für das Depot
        // HINWEIS: Nutze log.info(...) zum Loggen
    }

    /**
     * Gibt den Namen des Roboters zurück
     *
     * @return der Name (z.B. "TradeBot2000")
     */
    public String getName() {
        // TODO: Gib this.name zurück
        return null;
    }

    /**
     * Gibt das aktuelle Konto des Roboters zurück
     *
     * @return das Konto mit aktuellem Guthaben
     */
    public Konto getKonto() {
        // TODO: Gib this.konto zurück
        return null;
    }

    /**
     * Gibt das Depot des Roboters zurück (welche Aktien er besitzt)
     *
     * @return die Map mit Aktienname → Anzahl
     */
    public Map<String, Integer> getDepot() {
        // TODO: Gib this.depot zurück (besser: neue HashMap(this.depot) zur Sicherheit)
        return null;
    }

    /**
     * Prüft ob der Roboter eine bestimmte Aktie besitzt
     *
     * @param aktienname z.B. "Apple"
     * @return true wenn depot.containsKey(aktienname), sonst false
     */
    public boolean besitztAktie(String aktienname) {
        // TODO: Prüfe mit depot.containsKey(aktienname)
        return false;
    }

    /**
     * Gibt die Anzahl der Aktien einer bestimmten Art zurück
     *
     * @param aktienname z.B. "Apple"
     * @return die Anzahl, oder 0 wenn Roboter nicht besitzt
     */
    public int gibAnzahlAktien(String aktienname) {
        // TODO: Nutze depot.getOrDefault(aktienname, 0)
        return 0;
    }

    /**
     * HAUPTMETHODE: Der Roboter handelt an einem Tag
     * <p>
     * Diese Methode wird täglich aufgerufen. Der Roboter soll:
     * <p>
     * 1. KAUFE günstige Aktien:
     *    - Wenn kursService.istKursGuenstig(aktie) == true
     *    - UND Roboter hat genug Guthaben
     *    - UND Roboter besitzt nicht schon > 10 dieser Aktie
     *    - Dann: boerseService.kaufe(...) aufrufen
     * <p>
     * 2. VERKAUFE teure Aktien:
     *    - Wenn kursService.istKursTeuer(aktie) == true
     *    - UND Roboter besitzt mindestens 1 dieser Aktie
     *    - Dann: boerseService.verkaufe(...) aufrufen
     * <p>
     * 3. LOGGE die Aktion:
     *    - Kaufen: log.info("[name] kauft [anzahl]x [aktie.name()]")
     *    - Verkaufen: log.info("[name] verkauft [anzahl]x [aktie.name()]")
     *    - Halten: log.info("[name] hält Position in [aktie.name()]")
     * <p>
     * 4. AKTUALISIERE das Depot:
     *    - Nach Kauf: depot.put(aktienname, neue_anzahl)
     *    - Nach Verkauf: entferne oder reduziere Anzahl
     * <p>
     * HINT: Der Algorithmus sieht ungefähr so aus:
     * <p>
     * ```
     * FUNCTION handleAnEinemTag(aktie):
     *     aktuellerPreis = hole preis von aktie
     *     depot.put(aktienname, alte_anzahl + gekaufte_anzahl)
     * <p>
     *     IF kursService.istKursGuenstig(aktie) AND habe genug guthaben:
     *         DANN kaufen
     *         anzahl = berechne wieviel ich kaufen kann
     *         boerseService.kaufe(konto, aktie, anzahl)
     *         depot.put(aktie.getName(), bisherige_anzahl + anzahl)
     *         log.info("gekauft")
     * <p>
     *     ELSE IF kursService.istKursTeuer(aktie) AND ich besitze diese aktie:
     *         DANN verkaufen
     *         anzahl = depot.get(aktie.getName())
     *         boerseService.verkaufe(konto, aktie, anzahl)
     *         depot.remove(aktie.getName())  // oder depot.put(..., 0)
     *         log.info("verkauft")
     * <p>
     *     ELSE:
     *         log.info("halten")
     * ```
     * <p>
     * HERAUSFORDERUNG:
     * - Mehrere Aktien handeln? Schreib eine Loop über eine Liste von Aktien
     * - Nur teilweise verkaufen? Dann depot.put(name, verbleibende_anzahl)
     * - Edge case: Was wenn BoerseService eine Exception wirft? (Try-Catch?)
     *
     * @param aktie die zu handelende Aktie
     * @throws IllegalArgumentException wenn Aktie null ist
     */
    public void handleAnEinemTag(Aktie aktie) {
        // TODO: Implementiere die Handelslogik
        // SCHRITT 1: Validierung
        if (aktie == null) {
            throw new IllegalArgumentException("Aktie darf nicht null sein");
        }

        // SCHRITT 2: Analysiere den Kurs
        // - kursService.istKursGuenstig(aktie) → boolean für KAUF
        // - kursService.istKursTeuer(aktie) → boolean für VERKAUF
        // - depot.containsKey(aktie.getName()) → habe ich diese Aktie?
        // - konto.getKontostand() → wie viel Guthaben?

        // SCHRITT 3: KAUF wenn Kurs günstig
        // Bedingungen:
        //   - kursService.istKursGuenstig(aktie)
        //   - konto.getKontostand() >= aktie.getPreis()
        //   - gibAnzahlAktien(aktie.getName()) < 10  (max 10 pro Aktie)
        //
        // Wenn ja:
        //   - double preis = aktie.getPreis()
        //   - int anzahlKannKaufen = (int) (konto.getKontostand() / preis)
        //   - aber max 10, also: anzahlZuKaufen = Math.min(anzahlKannKaufen, 10 - schonBesitzt)
        //   - boerseService.kaufe(konto, aktie, anzahlZuKaufen)
        //   - depot.put(aktie.getName(), gibAnzahlAktien(aktie.getName()) + anzahlZuKaufen)
        //   - log.info("[{}] kauft {}x {}", name, anzahlZuKaufen, aktie.getName())

        // SCHRITT 4: VERKAUF wenn Kurs teuer
        // Bedingungen:
        //   - kursService.istKursTeuer(aktie)
        //   - depot.containsKey(aktie.getName())
        //   - gibAnzahlAktien(aktie.getName()) > 0
        //
        // Wenn ja:
        //   - int anzahlZuVerkaufen = depot.get(aktie.getName())
        //   - boerseService.verkaufe(konto, aktie, anzahlZuVerkaufen)
        //   - depot.remove(aktie.getName())  oder depot.put(aktie.getName(), 0)
        //   - log.info("[{}] verkauft {}x {}", name, anzahlZuVerkaufen, aktie.getName())

        // SCHRITT 5: HALTEN wenn keine Aktion
        // log.info("[{}] hält Position in {}", name, aktie.getName())
    }

    /**
     * Handelt mit mehreren Aktien in einer Handelssession
     * <p>
     * Diese Methode ruft handleAnEinemTag(...) für jede Aktie auf.
     * <p>
     * AUFGABE:
     * 1. Nimm eine Liste von Aktien als Parameter
     * 2. Iteriere über alle Aktien
     * 3. Rufe handleAnEinemTag() für jede auf
     * 4. Bei Exception: logge den Fehler, aber mache mit nächster Aktie weiter
     * 5. Logge am Ende: "[name] Handelssession beendet. Guthaben: [betrag]€"
     *
     * @param aktien die zu handelenden Aktien
     */
    public void handeleSession(List<Aktie> aktien) {
        // TODO: Implementiere die Handelssession
        // HINWEIS: for (Aktie aktie : aktien) { ... }
        // HINWEIS: try { handleAnEinemTag(aktie); } catch (Exception e) { log.warn(...); }
    }

    /**
     * Berechnet den aktuellen Vermögenswert des Roboters
     * <p>
     * FORMEL:
     * GesamtVermögen = Guthaben + (Wert aller Aktien im Depot)
     * <p>
     * Um Aktienwert zu berechnen:
     * - Für jede Aktie im Depot: anzahl * aktuelle_preis
     * - Die aktuelle_preis kannst du über KursService ermitteln
     *   (Oder: Speichere die Aktien-Objekte selbst, nicht nur Namen)
     * <p>
     * AUFGABE:
     * 1. Starte mit guthaben = konto.getKontostand()
     * 2. Für jedes Aktien-Paar im Depot:
     *    - Finde die Aktie (Hinweis: Du brauchst eine Map<String, Aktie> damit das funktioniert)
     *    - Berechne Wert = anzahl * aktuelle_preis
     *    - Addiere zu guthaben
     * 3. Gib gesamtVermögen zurück
     * <p>
     * HERAUSFORDERUNG: Wie speicherst du die Aktien selbst?
     * Option 1: Zweite Map<String, Aktie> depot_objekte
     * Option 2: Aktienliste als Parameter übergeben
     * Option 3: KursService hat die Aktien (aber nur Preise...)
     *
     * @return das Gesamtvermögen in Euro
     */
    public double berechnetGesamtvermoegen() {
        // TODO: Implementiere die Vermögensberechnung
        return 0.0;
    }

    /**
     * Gibt einen Status-Bericht des Roboters aus
     * <p>
     * Format:
     * ```
     * ═══════════════════════════════════════════
     * Handelsroboter: [Name]
     * ═══════════════════════════════════════════
     * Guthaben: [X]€
     * Depot: [Anzahl verschiedener Aktien]
     *   - Apple: 5 Stück
     *   - BMW: 3 Stück
     *   - SAP: 0 Stück
     * Gesamtvermögen: [Y]€
     * Gewinn/Verlust: [Y - Startkapital]€
     * ═══════════════════════════════════════════
     * ```
     *
     * @return der Status als String
     */
    public String gibStatus() {
        // TODO: Implementiere die Status-Ausgabe
        // HINWEIS: Nutze StringBuilder für komplexe Strings
        return "";
    }
}
