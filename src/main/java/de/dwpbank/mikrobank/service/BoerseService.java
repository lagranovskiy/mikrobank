
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
        // 8. Logge erfolgreiche Kauforder
    }
}
