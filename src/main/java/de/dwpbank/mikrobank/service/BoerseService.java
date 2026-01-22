
package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Aktie;
import de.dwpbank.mikrobank.model.Konto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoerseService {

    private final KontoService kontoService = new KontoService();
    private final PreisService preisService = new PreisService();
    private final OrderValidierungsService validierungsService = new OrderValidierungsService();

     /**
     * Der KursService verwaltet die Kurshistorie aller Aktien und stellt Analysefunktionen
     * zur Verfügung. Der BörsenRoboter nutzt diesen Service später, um Kauf- und Verkaufsentscheidungen
     * zu treffen basierend auf historischen Kursdaten.
     */
    private final KursService kursService = new KursService();



    /**
     * Führt einen Aktienkauf aus.
     *
     * Fachlicher Ablauf:
     * 1. Die Kauforder wird validiert
     * 2. Der aktuelle Aktienpreis wird ermittelt
     * 3. Der Gesamtpreis (Preis * Menge) wird berechnet
     * 4. Es wird geprüft, ob das Konto ausreichend gedeckt ist
     * 5. Der Betrag wird vom Konto abgebucht
     *
     * Fehlerfälle:
     * - Ungültige Order → IllegalArgumentException
     * - Nicht genug Guthaben → IllegalStateException
     *
     * Technische Regeln:
     * - Jeder Schritt wird geloggt
     * - Bei Fehlern wird ein ERROR-Log geschrieben
     *
     * @param konto das Konto des Kunden
     * @param aktie die zu kaufende Aktie
     * @param menge Anzahl der Aktien
     */
    public void kaufe(Konto konto, Aktie aktie, int menge) {
        // IMPLEMENTIERUNG:
        // 1. Logge Start der Kauforder
        // 2. Rufe validierungsService.validiereKauf(...) auf
        // 3. Ermittle den aktuellen Preis über preisService
        // 4. Berechne den Gesamtpreis
        // 5. Prüfe, ob konto.getKontostand() >= Gesamtpreis
        // 6. Falls nein: logge Fehler und wirf IllegalStateException
        // 7. Buche den Betrag über kontoService.auszahlen(...) ab
        // 8. Speichere den neuen Kurs im KursService
        // 9. Logge erfolgreiche Kauforder
    }

    /**
     * Führt einen Aktienverkauf aus.
     *
     * Fachlicher Ablauf:
     * 1. Die Verkaufsorder wird validiert
     * 2. Der aktuelle Aktienpreis wird ermittelt (mit Marktfluktuation)
     * 3. Der Gesamterlös (Preis * Menge) wird berechnet
     * 4. Der Erlös wird auf das Konto eingezahlt
     * 5. Der neue Preis wird im KursService gespeichert
     *
     * Fehlerfälle:
     * - Ungültige Order → IllegalArgumentException
     * - Kein Depot/Aktie vorhanden → wird später vom Roboter gehandhabt
     *
     * Technische Regeln:
     * - Jeder Schritt wird geloggt
     * - Der KursService wird aktualisiert
     *
     * @param konto das Konto des Kunden
     * @param aktie die zu verkaufende Aktie
     * @param menge Anzahl der Aktien
     */
    public void verkaufe(Konto konto, Aktie aktie, int menge) {
        // IMPLEMENTIERUNG:
        // 1. Logge Start der Verkaufsorder
        // 2. Rufe validierungsService.validiereVerkauf(...) auf
        // 3. Ermittle den aktuellen Preis über preisService
        // 4. Berechne den Gesamterlös
        // 5. Zahle den Erlös auf das Konto ein
        // 6. Speichere den neuen Kurs im KursService
        // 7. Logge erfolgreiche Verkaufsorder
    }
}
