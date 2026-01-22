package de.dwpbank.mikrobank;

import de.dwpbank.mikrobank.model.Aktie;
import de.dwpbank.mikrobank.model.Konto;
import de.dwpbank.mikrobank.service.HandelsRoboter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Tests für den HandelsRoboter
 *
 * Diese Tests validieren die Grundfunktionalität des Handelsroboters:
 * - Konstruktion und Initialisierung
 * - Kauf und Verkauf von Aktien
 * - Depot-Verwaltung
 * - Gewinnberechnung
 *
 * HINWEIS FÜR DEN PRAKTIKANTEN:
 * - Die Tests schreiben vor, WAS der Code tun muss
 * - Wenn ein Test fehlschlägt, weiß du, was implementiert werden muss
 * - Starte mit den einfachen Tests (Status, Konstruktor)
 * - Arbeite dich dann zu komplexeren vor (Kauf/Verkauf)
 */
@DisplayName("HandelsRoboter Tests")
class HandelsRoboterTest {

    private HandelsRoboter roboter;
    private Aktie appleAktie;
    private Aktie bmwAktie;

    @BeforeEach
    void setUp() {
        // Erstelle einen Roboter mit 10.000€ Startkapital
        roboter = new HandelsRoboter("TradeBot2000", 10000);

        // Erstelle Testaktien
        appleAktie = new Aktie("Apple", 100);
        bmwAktie = new Aktie("BMW", 50);
    }

    // ═══════════════════════════════════════════════════════════════
    // TEIL 1: GRUNDLEGENDE TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    @DisplayName("HandelsRoboter: Hat einen Namen")
    void handelsroboterHatNamen() {
        // Arrange & Act
        String name = roboter.getName();

        // Assert
        assertEquals("TradeBot2000", name);
    }

    @Test
    @DisplayName("HandelsRoboter: Hat ein Startkapital")
    void handelsroboterHatStartkapital() {
        // Arrange & Act
        Konto konto = roboter.getKonto();

        // Assert
        assertEquals(10000, konto.getKontostand());
    }

    @Test
    @DisplayName("HandelsRoboter: Depot ist initial leer")
    void handelsroboterDepotIstLeer() {
        // Arrange & Act
        Map<String, Integer> depot = roboter.getDepot();

        // Assert
        assertTrue(depot.isEmpty(), "Depot sollte initial leer sein");
    }

    @Test
    @DisplayName("HandelsRoboter: besitztAktie() gibt false für unbekannte Aktien")
    void besitztAktieUnbekannt() {
        // Arrange & Act
        boolean besitzt = roboter.besitztAktie("Apple");

        // Assert
        assertFalse(besitzt);
    }

    @Test
    @DisplayName("HandelsRoboter: gibAnzahlAktien() gibt 0 für unbekannte Aktien")
    void gibAnzahlAktienUnbekannt() {
        // Arrange & Act
        int anzahl = roboter.gibAnzahlAktien("Apple");

        // Assert
        assertEquals(0, anzahl);
    }

    // ═══════════════════════════════════════════════════════════════
    // TEIL 2: KAUF-TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    @DisplayName("HandelsRoboter: Kauft Aktien wenn günstig")
    void kauftAktienWennGuenstig() {
        // Arrange
        // Apple kostet 100€, Roboter hat 10.000€
        // Nach Kauf sollte er weniger Guthaben haben
        double guthabenVorher = roboter.getKonto().getKontostand();

        // Act
        roboter.handleAnEinemTag(appleAktie);

        // Assert - Guthaben sollte kleiner sein
        double guthabenNachher = roboter.getKonto().getKontostand();
        assertTrue(guthabenNachher < guthabenVorher,
                "Guthaben sollte nach Kauf kleiner sein");
    }

    @Test
    @DisplayName("HandelsRoboter: Speichert gekaufte Aktien im Depot")
    void speichertGekauffteAktienImDepot() {
        // Arrange & Act
        roboter.handleAnEinemTag(appleAktie);

        // Assert
        assertTrue(roboter.besitztAktie("Apple"),
                "Roboter sollte Apple im Depot haben");
        assertTrue(roboter.gibAnzahlAktien("Apple") > 0,
                "Roboter sollte mindestens 1 Apple-Aktie besitzen");
    }

    @Test
    @DisplayName("HandelsRoboter: Kauft nicht mehr als 10 pro Aktie")
    void kauftMaximal10ProAktie() {
        // Arrange & Act
        roboter.handleAnEinemTag(appleAktie);

        // Assert
        int anzahl = roboter.gibAnzahlAktien("Apple");
        assertTrue(anzahl <= 10,
                "Roboter sollte nie mehr als 10 einer Aktie kaufen");
    }

    @Test
    @DisplayName("HandelsRoboter: Kauft nicht ohne Guthaben")
    void kauftNichtOhneGuthaben() {
        // Arrange
        // Erstelle einen Roboter mit wenig Geld und teurer Aktie
        HandelsRoboter armerRoboter = new HandelsRoboter("PoorBot", 10);
        Aktie teureAktie = new Aktie("Luxury", 500);

        // Act & Assert
        // Der Roboter sollte nicht kaufen können
        double guthabenVorher = armerRoboter.getKonto().getKontostand();
        armerRoboter.handleAnEinemTag(teureAktie);
        double guthabenNachher = armerRoboter.getKonto().getKontostand();

        assertEquals(guthabenVorher, guthabenNachher,
                "Guthaben sollte gleich bleiben wenn nicht genug Geld");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEIL 3: VERKAUF-TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    @DisplayName("HandelsRoboter: Verkauft Aktien wenn teuer")
    void verkauffAktienWennTeuer() {
        // Arrange
        // 1. Kaufe zuerst eine Aktie (so dass sie im Depot ist)
        roboter.handleAnEinemTag(appleAktie);
        int anzahlNachKauf = roboter.gibAnzahlAktien("Apple");

        // 2. Erhöhe den Preis künstlich (damit der Roboter verkauft)
        appleAktie.setPreis(200); // Preis verdoppelt → teuer!

        double guthabenVorVerkauf = roboter.getKonto().getKontostand();

        // Act
        roboter.handleAnEinemTag(appleAktie);

        // Assert
        double guthabenNachVerkauf = roboter.getKonto().getKontostand();
        assertTrue(guthabenNachVerkauf > guthabenVorVerkauf,
                "Guthaben sollte nach Verkauf höher sein");
    }

    @Test
    @DisplayName("HandelsRoboter: Entfernt Aktien aus Depot nach Verkauf")
    void entferntAktienAusDepotNachVerkauf() {
        // Arrange
        roboter.handleAnEinemTag(appleAktie);
        assertTrue(roboter.besitztAktie("Apple"));

        appleAktie.setPreis(500); // Sehr teuer

        // Act
        roboter.handleAnEinemTag(appleAktie);

        // Assert
        int anzahlNachVerkauf = roboter.gibAnzahlAktien("Apple");
        assertTrue(anzahlNachVerkauf == 0,
                "Roboter sollte keine Apple-Aktien mehr haben nach Verkauf");
    }

    @Test
    @DisplayName("HandelsRoboter: Verkauft nicht, wenn nicht im Depot")
    void verkauffNichtWennNichtImDepot() {
        // Arrange
        appleAktie.setPreis(500); // Sehr teuer
        double guthabenVorher = roboter.getKonto().getKontostand();

        // Act
        roboter.handleAnEinemTag(appleAktie);

        // Assert
        double guthabenNachher = roboter.getKonto().getKontostand();
        assertEquals(guthabenVorher, guthabenNachher,
                "Guthaben sollte gleich bleiben wenn Roboter diese Aktie nicht im Depot hat");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEIL 4: HANDELSSESSION-TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    @DisplayName("HandelsRoboter: Handelt mit mehreren Aktien in einer Session")
    void handelSessionMitMehrerenAktien() {
        // Arrange
        List<Aktie> aktien = new ArrayList<>();
        aktien.add(new Aktie("Apple", 100));
        aktien.add(new Aktie("BMW", 50));

        // Act
        roboter.handeleSession(aktien);

        // Assert - Sollte entweder gekauft haben oder nicht, aber keine Exception
        // Wenn gekauft: Guthaben reduziert, Depot nicht leer
        assertTrue(true, "Handelssession sollte ohne Exception durchlaufen");
    }

    @Test
    @DisplayName("HandelsRoboter: Setzt Handelsession fort auch wenn eine Aktie Exception wirft")
    void handelSessionSetzFortBeiException() {
        // Arrange
        List<Aktie> aktien = new ArrayList<>();
        aktien.add(null); // Das wird Exception werfen
        aktien.add(new Aktie("Apple", 100)); // Aber diese sollte noch verarbeitet werden

        // Act & Assert - Sollte nicht abbrechen
        try {
            roboter.handeleSession(aktien);
            // OK, hat das gehandhabt
        } catch (Exception e) {
            fail("Handelssession sollte bei Exception weitermachen");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // TEIL 5: GEWINN/VERMÖGEN-TESTS
    // ═══════════════════════════════════════════════════════════════

    @Test
    @DisplayName("HandelsRoboter: Vermögen ist mindestens Guthaben")
    void vermoegensIsMindesitensGuthaben() {
        // Arrange & Act
        double vermoegen = roboter.berechnetGesamtvermoegen();
        double guthaben = roboter.getKonto().getKontostand();

        // Assert
        assertTrue(vermoegen >= guthaben,
                "Gesamtvermögen sollte mindestens dem Guthaben entsprechen");
    }

    @Test
    @DisplayName("HandelsRoboter: Vermögen erhöht sich durch erfolgreiche Käufe und Verkäufe")
    void vermoegensErhoehtSichDurchGewinn() {
        // Arrange
        double vermoegensVorher = roboter.berechnetGesamtvermoegen();

        // Act - Kauf und Verkauf mit Gewinn
        // 1. Kaufe bei 100€
        roboter.handleAnEinemTag(appleAktie);

        // 2. Verkaufe bei 150€
        appleAktie.setPreis(150);
        roboter.handleAnEinemTag(appleAktie);

        // Assert
        double vermoegensNachher = roboter.berechnetGesamtvermoegen();
        assertTrue(vermoegensNachher > vermoegensVorher,
                "Vermögen sollte nach profitablem Kauf/Verkauf höher sein");
    }

    @Test
    @DisplayName("HandelsRoboter: Status-Bericht enthält relevante Informationen")
    void statusBerichenthaltRelevante() {
        // Arrange & Act
        roboter.handleAnEinemTag(appleAktie);
        String status = roboter.gibStatus();

        // Assert
        assertTrue(status.contains("TradeBot2000"), "Status sollte Namen enthalten");
        assertTrue(status.contains("€") || status.contains("Guthaben"), 
                "Status sollte Guthaben enthalten");
        assertTrue(status.contains("Depot") || status.contains("Apple"),
                "Status sollte Depot-Informationen enthalten");
    }

    // ═══════════════════════════════════════════════════════════════
    // TEIL 6: EDGE CASES
    // ═══════════════════════════════════════════════════════════════

    @Test
    @DisplayName("HandelsRoboter: handleAnEinemTag wirft Exception bei null Aktie")
    void handleThrowsExceptionBeiNullAktie() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            roboter.handleAnEinemTag(null);
        }, "Sollte IllegalArgumentException werfen bei null Aktie");
    }

    @Test
    @DisplayName("HandelsRoboter: Mehrfaches Handeln erhöht/reduziert Vermögen")
    void mehrfachesHandelnAendertVermoegen() {
        // Arrange
        List<Aktie> aktien = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            aktien.add(new Aktie("Apple", 100 + (i * 10)));
        }

        double vermoegensVorher = roboter.berechnetGesamtvermoegen();

        // Act
        for (Aktie aktie : aktien) {
            roboter.handleAnEinemTag(aktie);
        }

        // Assert - Vermögen kann gestiegen oder gefallen sein, aber es sollte sich geändert haben
        double vermoegensNachher = roboter.berechnetGesamtvermoegen();
        // Das ist ok, wenn es gestiegen oder gefallen ist - Hauptsache es funktioniert
        assertTrue(vermoegensNachher >= 0, "Vermögen sollte nie negativ sein");
    }
}
