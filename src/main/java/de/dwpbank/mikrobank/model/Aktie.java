
package de.dwpbank.mikrobank.model;


/**
 * Repräsentiert ein Wertpapier, das an der Börse gehandelt wird.
 *
 * Fachliche Bedeutung:
 * - Eine Aktie hat einen Namen (z. B. ein Kürzel oder Firmenname)
 * - Eine Aktie besitzt einen aktuellen Preis
 *
 * Design-Entscheidungen:
 * - Diese Klasse speichert nur den Zustand
 * - Preisänderungen erfolgen ausschließlich über Services
 *
 * Beispiel:
 * Der aktuelle Marktpreis wird über den {@link de.dwpbank.mikrobank.service.PreisService}
 * neu berechnet und in dieser Klasse gespeichert.
 */
public class Aktie {

    /**
     * Name oder Kürzel der Aktie.
     *
     * Beispiel:
     * - "ABC"
     * - "BANK_AG"
     */
    private String name;

    /**
     * Aktueller Preis der Aktie.
     *
     * Fachliche Bedeutung:
     * - Der Preis ist immer größer als 0
     * - Preisänderungen simulieren Marktbewegungen
     *
     * Technischer Hinweis:
     * - Die Einhaltung fachlicher Regeln wird durch Services sichergestellt
     */
    private double preis;

    /**
     * Erstellt eine neue Aktie.
     *
     * @param name  Name oder Kürzel der Aktie
     * @param preis Startpreis der Aktie
     */
    public Aktie(String name, double preis) {
        this.name = name;
        this.preis = preis;
    }

    /**
     * Liefert den Namen der Aktie.
     *
     * @return Name der Aktie
     */
    public String getName() {
        return name;
    }

    /**
     * Liefert den aktuellen Preis der Aktie.
     *
     * @return aktueller Preis
     */
    public double getPreis() {
        return preis;
    }

    /**
     * Setzt einen neuen Preis für die Aktie.
     *
     * Technischer Hinweis:
     * - Diese Methode prüft nicht, ob der Preis gültig ist
     * - Die fachliche Validierung erfolgt im {@link boerse.PreisService}
     *
     * @param preis neuer Preis
     */
    public void setPreis(double preis) {
        this.preis = preis;
    }

}
