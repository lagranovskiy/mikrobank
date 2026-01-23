package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Aktie;

import java.util.*;

/**
 * Service zur Verwaltung und Analyse von Aktienkursen.
 * <p>
 * Der KursService verwaltet die Kurshistorie von Aktien und stellt dem Handelsroboter
 * (BörsenRoboter) Analysefunktionen zur Verfügung, um intelligente Kauf- und Verkaufsentscheidungen
 * zu treffen.
 * <p>
 * Fachliche Aufgaben:
 * 1. Speichert Kurshistorie für jede Aktie
 * 2. Berechnet Durchschnittskurse und Trends
 * 3. Erkennt Hochs und Tiefs
 * 4. Hilft dem Roboter bei der Entscheidungsfindung
 * <p>
 * Beispiel:
 * - Wenn der aktuelle Kurs deutlich unter dem Durchschnitt liegt → KAUFSIGNAL
 * - Wenn der aktuelle Kurs deutlich über dem Durchschnitt liegt → VERKAUFSSIGNAL
 *
 * @author Praktikant
 * @version 1.0
 */
public class KursService {

    // Map speichert für jede Aktie eine Liste ihrer historischen Kurse
    // Key: Aktien-Name (z.B. "Apple")
    // Value: Liste der Kurse in chronologischer Reihenfolge
    private final Map<String, List<Double>> kursHistorie = new HashMap<>();

    // Maximale Anzahl der gespeicherten Kurse pro Aktie (um Speicher zu sparen)
    private static final int MAX_KURSHISTORIE = 100;

    /**
     * Registriert einen neuen Kurs für eine Aktie in der Historie.
     * <p>
     * Diese Methode wird nach jedem Kauf/Verkauf aufgerufen, um den neuen Preis
     * in der Kurshistorie zu speichern.
     * <p>
     * Geschäftsregeln:
     * - Der Kurs muss > 0 sein
     * - Die Historie wird begrenzt auf MAX_KURSHISTORIE Einträge
     * - Bei Überschreitung wird der älteste Kurs entfernt (FIFO)
     *
     * @param aktie die Aktie, deren Kurs gespeichert werden soll
     * @throws IllegalArgumentException wenn der Kurse negativ oder 0 ist
     */
    public void speichereKurs(Aktie aktie) {
        if (aktie == null) {
            System.out.println("Warnung: Null-Wert bei speichereKurs: Aktie ist null");
            throw new IllegalArgumentException("Aktie darf nicht null sein");
        }

        double neuerKurs = aktie.getPreis();
        if (neuerKurs <= 0) {
            System.out.println("Warnung: Ungültiger Kurs für Aktie '" + aktie.getName() + "': " + neuerKurs);
            throw new IllegalArgumentException("Kurs muss größer als 0 sein");
        }

        // Hole oder erstelle die Historien-Liste für diese Aktie
        kursHistorie.computeIfAbsent(aktie.getName(), k -> new ArrayList<>()).add(neuerKurs);

        // Begrenze die Größe der Historie
        List<Double> historie = kursHistorie.get(aktie.getName());
        if (historie.size() > MAX_KURSHISTORIE) {
            historie.remove(0); // Entferne den ältesten Kurs
        }
    }

    /**
     * Berechnet den Durchschnittskurs einer Aktie basierend auf der Historie.
     * <p>
     * Formel: Durchschnitt = Summe aller Kurse / Anzahl der Kurse
     * <p>
     * Verwendungsfall: Der Roboter kann damit erkennen, ob ein aktueller Kurs
     * günstig (unter Durchschnitt) oder teuer (über Durchschnitt) ist.
     *
     * @param aktieName der Name der Aktie (z.B. "Apple")
     * @return der Durchschnittskurs, oder 0.0 wenn keine Historie existiert
     */
    public double berechnetDurchschnittskurs(String aktieName) {
        List<Double> historie = kursHistorie.get(aktieName);

        if (historie == null || historie.isEmpty()) {
            return 0.0;
        }

        double summe = 0;
        for (double kurs : historie) {
            summe += kurs;
        }

        double durchschnitt = summe / historie.size();
        return durchschnitt;
    }

    /**
     * Bestimmt den Trend einer Aktie.
     * <p>
     * Logik:
     * - STEIGEND: Durchschnitt der letzten 5 Kurse > Durchschnitt der 5 davor
     * - FALLEND: Durchschnitt der letzten 5 Kurse < Durchschnitt der 5 davor
     * - STABIL: Kein signifikanter Unterschied
     *
     * @param aktieName der Name der Aktie
     * @return "STEIGEND", "FALLEND" oder "STABIL"
     */
    public String bestimmeTrend(String aktieName) {
        List<Double> historie = kursHistorie.get(aktieName);

        if (historie == null || historie.size() < 10) {
            return "UNBEKANNT";
        }

        // Berechne Durchschnitt der letzten 5 Kurse
        double durchschnittNeu = 0;
        for (int i = historie.size() - 5; i < historie.size(); i++) {
            durchschnittNeu += historie.get(i);
        }
        durchschnittNeu /= 5;

        // Berechne Durchschnitt der 5 Kurse davor
        double durchschnittAlt = 0;
        for (int i = historie.size() - 10; i < historie.size() - 5; i++) {
            durchschnittAlt += historie.get(i);
        }
        durchschnittAlt /= 5;

        String trend;
        if (durchschnittNeu > durchschnittAlt * 1.02) { // 2% Schwellenwert
            trend = "STEIGEND";
        } else if (durchschnittNeu < durchschnittAlt * 0.98) {
            trend = "FALLEND";
        } else {
            trend = "STABIL";
        }

        return trend;
    }

    /**
     * Gibt die bisherige Kurshistorie einer Aktie zurück.
     * <p>
     * Diese Methode ist nützlich für Debugging und Analyse.
     *
     * @param aktieName der Name der Aktie
     * @return eine Liste der Kurse, oder eine leere Liste wenn keine Daten existieren
     */
    public List<Double> gibKurshistorie(String aktieName) {
        List<Double> historie = kursHistorie.get(aktieName);
        return historie != null ? new ArrayList<>(historie) : new ArrayList<>();
    }

    /**
     * Berechnet die prozentuale Abweichung des aktuellen Kurses vom Durchschnitt.
     * <p>
     * Formel: ((aktueller Kurs - Durchschnitt) / Durchschnitt) * 100
     * <p>
     * Interpretation:
     * - +10% bedeutet: Kurs ist 10% über dem Durchschnitt (teuer)
     * - -10% bedeutet: Kurs ist 10% unter dem Durchschnitt (günstig)
     *
     * @param aktie die Aktie
     * @return prozentuale Abweichung (kann negativ sein)
     */
    public double berechnetAbweichungVomDurchschnitt(Aktie aktie) {
        if (aktie == null) {
            throw new IllegalArgumentException("Aktie darf nicht null sein");
        }

        double durchschnitt = berechnetDurchschnittskurs(aktie.getName());
        if (durchschnitt == 0) {
            return 0.0;
        }

        double abweichung = ((aktie.getPreis() - durchschnitt) / durchschnitt) * 100;
        return abweichung;
    }

    /**
     * Prüft, ob eine Aktie gerade günstig ist (unter Durchschnitt - Toleranz).
     * <p>
     * Ein Kurs ist "günstig", wenn:
     * - Er unter dem Durchschnitt liegt
     * - UND mindestens 5% unter dem Durchschnitt ist
     * <p>
     * Diese Methode wird vom BörsenRoboter genutzt, um Kaufsignale zu erkennen.
     *
     * @param aktie die zu prüfende Aktie
     * @return true wenn Kurs günstig ist, sonst false
     */
    public boolean istKursGuenstig(Aktie aktie) {
        if (aktie == null) {
            throw new IllegalArgumentException("Aktie darf nicht null sein");
        }

        double abweichung = berechnetAbweichungVomDurchschnitt(aktie);
        boolean guenstig = abweichung < -5.0; // Mindestens 5% unter Durchschnitt
        return guenstig;
    }

    /**
     * Prüft, ob eine Aktie gerade teuer ist (über Durchschnitt + Toleranz).
     * <p>
     * Ein Kurs ist "teuer", wenn:
     * - Er über dem Durchschnitt liegt
     * - UND mindestens 5% über dem Durchschnitt ist
     * <p>
     * Diese Methode wird vom BörsenRoboter genutzt, um Verkaufssignale zu erkennen.
     *
     * @param aktie die zu prüfende Aktie
     * @return true wenn Kurs teuer ist, sonst false
     */
    public boolean istKursTeuer(Aktie aktie) {
        if (aktie == null) {
            throw new IllegalArgumentException("Aktie darf nicht null sein");
        }

        double abweichung = berechnetAbweichungVomDurchschnitt(aktie);
        boolean teuer = abweichung > 5.0; // Mindestens 5% über Durchschnitt
        return teuer;
    }

    /**
     * Gibt den aktuellen Status des KursService für Debugging aus.
     *
     * @return ein String mit der Anzahl der verwalteten Aktien und deren Datenvolumen
     */
    public String gibStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("KursService Status:\n");
        sb.append("- Verwaltete Aktien: ").append(kursHistorie.size()).append("\n");
        for (String aktieName : kursHistorie.keySet()) {
            int anzahlKurse = kursHistorie.get(aktieName).size();
            double durchschnitt = berechnetDurchschnittskurs(aktieName);
            sb.append("  - ").append(aktieName).append(": ")
                    .append(anzahlKurse).append(" Kurse, Ø=").append(durchschnitt).append("\n");
        }
        return sb.toString();
    }
}
