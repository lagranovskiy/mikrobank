
package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Aktie;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreisService {


    /**
     * Ermittelt den aktuellen Preis einer Aktie.
     *
     * Fachliche Regeln:
     * - Der Basispreis ist der aktuelle Preis der Aktie
     * - Der Preis verändert sich zufällig um maximal ±5 %
     * - Der neue Preis darf niemals <= 0 sein
     *
     * Technische Regeln:
     * - Vor der Berechnung wird der alte Preis geloggt
     * - Nach der Berechnung wird der neue Preis geloggt
     * - Der neue Preis wird in der Aktie gespeichert
     *
     * @param aktie die Aktie
     * @return der neue, gültige Preis
     */
    public double ermittleAktuellenPreis(Aktie aktie) {

        // IMPLEMENTIERUNG:
        // 1. Lese den aktuellen Preis aus der Aktie
        // 2. Berechne eine zufällige Preisänderung (+/- Prozent)
        // 3. Berechne den neuen Preis
        // 4. Stelle sicher, dass der neue Preis > 0 ist
        // 5. Setze den neuen Preis in der Aktie
        // 6. Gib den neuen Preis zurück

        return 0;
    }
}
