
package de.dwpbank.mikrobank.model;

/**
 * Repräsentiert ein Bankkonto eines Kunden.
 *
 * Fachliche Bedeutung:
 * - Ein Konto besitzt einen aktuellen Kontostand
 * - Der Kontostand beschreibt das verfügbare Guthaben in Euro
 *
 * Wichtige Design-Entscheidungen:
 * - Diese Klasse enthält KEINE Geschäftslogik
 * - Es gibt hier keine Prüfungen auf negative Beträge
 * - Fachliche Regeln werden ausschließlich in Services umgesetzt
 *
 * Beispiel:
 * Ein- und Auszahlungen werden über den {@link boerse.KontoService} durchgeführt.
 */
public class Konto {

    /**
     * Der aktuelle Kontostand des Kontos.
     *
     * Fachliche Bedeutung:
     * - Der Wert kann sich im Laufe der Zeit ändern
     * - Ein negativer Kontostand ist technisch möglich,
     *   wird aber fachlich durch Services verhindert
     */
    private double kontostand;

    /**
     * Erstellt ein neues Konto mit einem Startguthaben.
     *
     * @param startGuthaben Anfangsguthaben des Kontos
     */
    public Konto(double startGuthaben) {
        this.kontostand = startGuthaben;
    }

    /**
     * Liefert den aktuellen Kontostand.
     *
     * @return aktueller Kontostand
     */
    public double getKontostand() {
        return kontostand;
    }

    /**
     * Setzt den Kontostand auf einen neuen Wert.
     *
     * Technischer Hinweis:
     * - Diese Methode führt keine Validierung durch
     * - Die Verantwortung für korrekte Werte liegt beim aufrufenden Service
     *
     * @param kontostand neuer Kontostand
     */
    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }
}
