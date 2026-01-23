
package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Konto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KontoService {

    /**
     * Zahlt einen Betrag auf das Konto ein.
     * <p>
     * Fachliche Regeln:
     * - Der Betrag muss größer als 0 sein
     * - Bei einem ungültigen Betrag wird eine IllegalArgumentException geworfen
     * - Bei erfolgreicher Einzahlung erhöht sich der Kontostand um den Betrag
     * <p>
     * Technische Regeln:
     * - Vor der Einzahlung wird ein INFO-Log geschrieben
     * - Nach der Einzahlung wird der neue Kontostand geloggt
     *
     * @param konto  das Konto, auf das eingezahlt wird
     * @param betrag der einzuzahlende Betrag
     */
    public void einzahlen(Konto konto, double betrag) {
        // IMPLEMENTIERUNG:
        // 1. Prüfe, ob betrag <= 0
        // 2. Wenn ja, logge einen Fehler und wirf IllegalArgumentException
        // 3. Wenn nein, addiere den Betrag zum Kontostand
        // 4. Logge den neuen Kontostand
    }

    /**
     * Zahlt einen Betrag vom Konto aus.
     * <p>
     * Fachliche Regeln:
     * - Der Betrag muss größer als 0 sein
     * - Der Kontostand darf nach der Auszahlung nicht negativ sein
     * <p>
     * Technische Regeln:
     * - Vor der Auszahlung wird der aktuelle Kontostand geloggt
     * - Nach der Auszahlung wird der neue Kontostand geloggt
     *
     * @param konto  das Konto
     * @param betrag der auszuzahlende Betrag
     */
    public void auszahlen(Konto konto, double betrag) {
        // IMPLEMENTIERUNG:
        // 1. Prüfe, ob betrag <= 0
        // 2. Prüfe, ob konto.getKontostand() - betrag < 0
        // 3. Falls eine Prüfung fehlschlägt, logge einen Fehler und wirf Exception
        // 4. Ziehe den Betrag vom Kontostand ab
        // 5. Logge den neuen Kontostand
    }
}
