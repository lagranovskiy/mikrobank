
package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Aktie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderValidierungsService {

    /**
     * Validiert eine Kauforder.
     * <p>
     * Fachliche Regeln:
     * - Die Aktie darf nicht null sein
     * - Die Menge muss größer als 0 sein
     * <p>
     * Technische Regeln:
     * - Jede Prüfung wird mit DEBUG geloggt
     * - Bei ungültiger Order wird eine IllegalArgumentException geworfen
     *
     * @param aktie die zu kaufende Aktie
     * @param menge Anzahl der Aktien
     */
    public void validiereKauf(Aktie aktie, int menge) {
        // IMPLEMENTIERUNG:
        // 1. Prüfe, ob aktie == null
        // 2. Prüfe, ob menge <= 0
        // 3. Wenn eine Prüfung fehlschlägt:
        //    - logge die Ursache
        //    - wirf IllegalArgumentException
    }

    /**
     * Validiert eine Verkaufsorder.
     * <p>
     * Fachliche Regeln:
     * - Die Aktie darf nicht null sein
     * - Die Menge muss größer als 0 sein
     *
     * @param aktie die zu verkaufende Aktie
     * @param menge Anzahl der Aktien
     */
    public void validiereVerkauf(Aktie aktie, int menge) {
        // IMPLEMENTIERUNG:
        // Gleiche Logik wie bei validiereKauf
    }
}
