package de.dwpbank.mikrobank;

import de.dwpbank.mikrobank.model.Aktie;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Boersensimulator - Integratives Demo-Programm für den HandelsRoboter
 *
 * Dieses Programm simuliert einen Börsentag mit dynamischen Aktienkursen.
 * Der HandelsRoboter handelt automatisch basierend auf seinen Kaufs- und Verkaufsregeln.
 *
 * VERWENDUNG:
 * ```
 * mvn exec:java -Dexec.mainClass="de.dwpbank.mikrobank.Boersensimulator"
 * ```
 *
 * Die Simulation läuft kontinuierlich:
 * - Jede "Runde" repräsentiert einen Handelsmoment
 * - Aktienkurse ändern sich zufällig (realistisch)
 * - Der Roboter macht Kauf-/Verkaufsentscheidungen
 * - Geben Sie "q" ein, um zu beenden
 *
 * AUSGABE:
 * - Zeitstempel: Wann der Handel stattfand
 * - Aktienkurse: Aktuelle Preise am Markt
 * - Roboter-Aktionen: Was der Roboter kauft/verkauft
 * - Status: Guthaben, Depot, Vermögen
 *
 * @author Praktikant
 * @version 1.0
 */
@Slf4j
public class Boersensimulator {

    private static final int SIMULATION_DELAY_MS = 3000; // 3 Sekunden zwischen Runden
    private static final double PREIS_SCHWANKUNG_PROZENT = 5.0; // ±5% Kursschwankung
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private HandelsRoboter roboter;
    private List<Aktie> aktien;
    private Random random;
    private int rundenZaehler;

    /**
     * Haupteinstiegspunkt der Simulation
     */
    public static void main(String[] args) {
        Boersensimulator simulator = new Boersensimulator();
        simulator.run();
    }

    /**
     * Initialisiert die Simulation
     */
    private void setup() {
        log.info("═══════════════════════════════════════════════════════════════");
        log.info("     BÖRSEN-SIMULATOR: Integratives Demo-System");
        log.info("═══════════════════════════════════════════════════════════════");
        log.info("");

        // Erstelle den Roboter mit 50.000€ Startkapital
        this.roboter = new HandelsRoboter("MegaTrader3000", 50000);
        this.random = new Random();
        this.rundenZaehler = 0;

        // Erstelle Aktien mit Startkursen
        this.aktien = new ArrayList<>();
        aktien.add(new Aktie("Apple", 150.00));
        aktien.add(new Aktie("BMW", 85.50));
        aktien.add(new Aktie("SAP", 110.00));
        aktien.add(new Aktie("Siemens", 95.75));
        aktien.add(new Aktie("Deutsche Telekom", 28.30));

        log.info("✅ Roboter erstellt: {}", roboter.getName());
        log.info("✅ {} Aktien hinzugefügt", aktien.size());
        log.info("✅ Startkapital: 50.000€");
        log.info("");
        log.info("Starten Sie die Simulation mit Enter...");
        log.info("Zum Beenden geben Sie 'q' ein und drücken Enter.");
        log.info("");

        // Warte auf Benutzer-Input zum Starten
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        log.info("");
        log.info("▶ SIMULATION GESTARTET!");
        log.info("");
    }

    /**
     * Hauptschleife der Simulation
     */
    private void run() {
        setup();

        Scanner scanner = new Scanner(System.in);
        Thread inputThread = new Thread(() -> {
            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    log.info("");
                    log.info("⏹ Simulation wird beendet...");
                    System.exit(0);
                }
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();

        // Hauptschleife
        while (true) {
            rundenZaehler++;

            try {
                // ──────────────────────────────────────────
                // SCHRITT 1: Simuliere Kursschwankungen
                // ──────────────────────────────────────────
                simulierKursschwankungen();

                // ──────────────────────────────────────────
                // SCHRITT 2: Zeige aktuelle Kurse
                // ──────────────────────────────────────────
                zeigeAktuelleKurse();

                // ──────────────────────────────────────────
                // SCHRITT 3: Roboter handelt
                // ──────────────────────────────────────────
                log.info("");
                log.info("🤖 HANDEL-RUNDE {}:", rundenZaehler);
                roboter.handeleSession(aktien);

                // ──────────────────────────────────────────
                // SCHRITT 4: Zeige Roboter-Status
                // ──────────────────────────────────────────
                log.info("");
                log.info(roboter.gibStatus());

                // ──────────────────────────────────────────
                // SCHRITT 5: Warte vor nächster Runde
                // ──────────────────────────────────────────
                log.info("");
                log.info("⏳ Nächste Runde in {} Sekunden... (q + Enter zum Beenden)",
                        SIMULATION_DELAY_MS / 1000);
                log.info("═══════════════════════════════════════════════════════════════");
                log.info("");

                Thread.sleep(SIMULATION_DELAY_MS);

            } catch (InterruptedException e) {
                log.error("Simulation unterbrochen: {}", e.getMessage());
                break;
            } catch (Exception e) {
                log.error("Fehler in Simulation: {}", e.getMessage(), e);
                Thread.yield();
            }
        }
    }

    /**
     * Simuliert realistische Kursschwankungen
     * Die Kurse ändern sich um ±5% basierend auf Zufälligkeit
     */
    private void simulierKursschwankungen() {
        for (Aktie aktie : aktien) {
            double alterPreis = aktie.getPreis();
            double schwankung = (random.nextDouble() - 0.5) * 2; // -1.0 bis 1.0
            double prozentChange = (PREIS_SCHWANKUNG_PROZENT / 100) * schwankung; // ±5%
            double neuerPreis = alterPreis * (1 + prozentChange);

            // Preis sollte nicht negativ werden
            neuerPreis = Math.max(neuerPreis, 0.01);

            aktie.setPreis(neuerPreis);
        }
    }

    /**
     * Zeigt die aktuellen Aktienkurse in einer Tabelle
     */
    private void zeigeAktuelleKurse() {
        String zeitstempel = LocalDateTime.now().format(FORMATTER);

        log.info("");
        log.info("📊 AKTUELLE KURSE - {}", zeitstempel);
        log.info("┌──────────────────┬──────────┬──────────────┐");
        log.info("│ Aktie            │ Preis    │ Trend        │");
        log.info("├──────────────────┼──────────┼──────────────┤");

        for (Aktie aktie : aktien) {
            String trend = getTrendIcon(aktie.getPreis());
            log.info("│ {:<16} │ €{:>7.2f} │ {:<12} │",
                    aktie.getName(),
                    aktie.getPreis(),
                    trend);
        }

        log.info("└──────────────────┴──────────┴──────────────┘");
    }

    /**
     * Gibt ein visuelles Trend-Symbol basierend auf zufälligen Wertanpassungen
     * Diese einfache Implementierung könnte durch echte Trendberechnung ersetzt werden
     */
    private String getTrendIcon(double preis) {
        // Vereinfachte Darstellung: zufällig auf/ab/seitwärts
        double zufall = Math.random();
        if (zufall < 0.33) return "📈 steigend";
        if (zufall < 0.66) return "📉 fallend";
        return "➡️  seitwärts";
    }

    /**
     * Gibt Statistiken zur Simulation aus
     */
    private void zeigeStatistiken() {
        log.info("");
        log.info("📈 SIMULATIONS-STATISTIKEN:");
        log.info("  • Runden durchgeführt: {}", rundenZaehler);
        log.info("  • Roboter-Name: {}", roboter.getName());
        log.info("  • Aktuelles Vermögen: €{}", roboter.berechnetGesamtvermoegen());
    }
}
