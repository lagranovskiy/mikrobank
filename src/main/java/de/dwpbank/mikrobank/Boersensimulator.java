package de.dwpbank.mikrobank;

import de.dwpbank.mikrobank.model.Aktie;
import org.slf4j.LoggerFactory;

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
 * mvn clean compile exec:java -Dexec.mainClass="de.dwpbank.mikrobank.Boersensimulator"
 * ```
 *
 * Die Simulation läuft kontinuierlich:
 * - Jede "Runde" repräsentiert einen Handelsmoment
 * - Aktienkurse ändern sich zufällig (realistisch)
 * - Der Roboter macht automatisch Kauf-/Verkaufsentscheidungen
 * - Drücken Sie Enter nach jeder Runde, um fortzufahren
 * - Geben Sie "q" ein und Enter zum Beenden
 *
 * @author Praktikant
 * @version 1.0
 */
public class Boersensimulator {

    private static final double PREIS_SCHWANKUNG_PROZENT = 5.0; // ±5% Kursschwankung
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Boersensimulator.class);

    private HandelsRoboter roboter;
    private List<Aktie> aktien;
    private Random random;
    private int rundenZaehler;

    public static void main(String[] args) {
        new Boersensimulator().run();
    }

    private void run() {
        setup();
        simuliere();
    }

    /**
     * Initialisiert die Simulation: Roboter und Aktien erstellen
     */
    private void setup() {
        logger.info("═══════════════════════════════════════════════════════════════");
        logger.info("     BÖRSEN-SIMULATOR: Integratives Demo-System");
        logger.info("═══════════════════════════════════════════════════════════════");
        logger.info("");

        this.roboter = new HandelsRoboter("MegaTrader3000", 50000);
        this.random = new Random();
        this.rundenZaehler = 0;

        this.aktien = new ArrayList<>();
        aktien.add(new Aktie("Apple", 150.00));
        aktien.add(new Aktie("BMW", 85.50));
        aktien.add(new Aktie("SAP", 110.00));
        aktien.add(new Aktie("Siemens", 95.75));
        aktien.add(new Aktie("Deutsche Telekom", 28.30));

        logger.info("✅ Roboter erstellt: {}", roboter.getName());
        logger.info("✅ {} Aktien hinzugefügt", aktien.size());
        logger.info("✅ Startkapital: 50.000€");
        logger.info("");
    }

    /**
     * Führt die Handelssimulation durch
     */
    private void simuliere() {
        Scanner scanner = new Scanner(System.in);

        logger.info("▶ SIMULATION GESTARTET!");
        logger.info("Drücken Sie Enter nach jeder Runde zum Fortfahren.");
        logger.info("Geben Sie 'q' ein und Enter zum Beenden.");
        logger.info("");

        while (true) {
            rundenZaehler++;

            try {
                // SCHRITT 1: Kurse ändern
                simulierKursschwankungen();

                // SCHRITT 2: Zeige Kurse
                zeigeAktuelleKurse();

                // SCHRITT 3: Roboter handelt
                logger.info("");
                logger.info("🤖 HANDEL-RUNDE {}:", rundenZaehler);
                roboter.handeleSession(aktien);

                // SCHRITT 4: Status anzeigen
                logger.info("");
                logger.info(roboter.gibStatus());

                // SCHRITT 5: Warte auf Benutzer-Input
                logger.info("");
                logger.info("═══════════════════════════════════════════════════════════════");
                logger.info("Drücken Sie Enter für nächste Runde (oder 'q' zum Beenden):");

                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("q")) {
                    logger.info("");
                    logger.info("⏹ Simulation beendet.");
                    logger.info("");
                    zeigeFinaleStatistiken();
                    break;
                }

                logger.info("");

            } catch (Exception e) {
                logger.error("Fehler in Runde {}: {}", rundenZaehler, e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Ändert die Aktienkurse um ±5%
     */
    private void simulierKursschwankungen() {
        for (Aktie aktie : aktien) {
            double alterPreis = aktie.getPreis();
            double schwankung = (random.nextDouble() - 0.5) * 2;
            double neuerPreis = alterPreis * (1 + (PREIS_SCHWANKUNG_PROZENT / 100) * schwankung);
            aktie.setPreis(Math.max(neuerPreis, 0.01));
        }
    }

    /**
     * Zeigt die aktuellen Kurse in einer Tabelle
     */
    private void zeigeAktuelleKurse() {
        String zeit = LocalDateTime.now().format(FORMATTER);

        logger.info("");
        logger.info("📊 AKTUELLE KURSE - {}", zeit);
        logger.info("┌──────────────────┬──────────┬──────────────┐");
        logger.info("│ Aktie            │ Preis    │ Trend        │");
        logger.info("├──────────────────┼──────────┼──────────────┤");

        for (Aktie aktie : aktien) {
            logger.info("│ {:<16} │ €{:>7.2f} │ {:<12} │",
                    aktie.getName(),
                    aktie.getPreis(),
                    getTrendIcon());
        }

        logger.info("└──────────────────┴──────────┴──────────────┘");
    }

    /**
     * Gibt ein zufälliges Trend-Symbol zurück
     */
    private String getTrendIcon() {
        double zufall = Math.random();
        if (zufall < 0.33) return "📈 steigend";
        if (zufall < 0.66) return "📉 fallend";
        return "➡️  seitwärts";
    }

    /**
     * Zeigt die finalen Statistiken am Ende
     */
    private void zeigeFinaleStatistiken() {
        logger.info("═══════════════════════════════════════════════════════════════");
        logger.info("📈 FINALE STATISTIKEN:");
        logger.info("  • Runden simuliert: {}", rundenZaehler);
        logger.info("  • Roboter: {}", roboter.getName());
        logger.info("  • Finales Vermögen: €{}", roboter.berechnetGesamtvermoegen());
        logger.info("═══════════════════════════════════════════════════════════════");
    }
}
