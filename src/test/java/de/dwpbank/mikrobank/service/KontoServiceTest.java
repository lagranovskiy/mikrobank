
package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Konto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests für den KontoService.
 *
 * Diese Tests prüfen die Funktionalität von Einzahlungen und Auszahlungen.
 * Jeder Test ist nach dem AAA-Pattern (Arrange, Act, Assert) strukturiert:
 *
 * Arrange (Vorbereitung): Testdaten erstellen
 * Act (Ausführung): Die zu testende Methode aufrufen
 * Assert (Überprüfung): Das Ergebnis mit assertEquals(), assertThrows() etc. prüfen
 */
@DisplayName("KontoService Tests")
class KontoServiceTest {

    // Der zu testende Service wird hier als Feld deklariert
    private KontoService kontoService;

    /**
     * @BeforeEach: Diese Methode wird VOR jedem einzelnen Test ausgeführt.
     * Dadurch ist jeder Test unabhängig und hat einen sauberen Zustand.
     */
    @BeforeEach
    void setUp() {
        // Initialisiere den KontoService neu für jeden Test
        kontoService = new KontoService();
    }

    // ============ EINZAHLEN-TESTS ============

    @Test
    @DisplayName("Einzahlen: Positiver Betrag erhöht den Kontostand")
    void einzahlenErhoehtKontostand() {
        // === ARRANGE (Vorbereitung) ===
        // Erstelle ein neues Konto mit 100 Euro Startguthaben
        Konto konto = new Konto(100);

        // === ACT (Ausführung) ===
        // Rufe die zu testende Methode auf: zahle 50 Euro ein
        kontoService.einzahlen(konto, 50);

        // === ASSERT (Überprüfung) ===
        // assertEquals(erwartet, tatsächlich) - prüft ob beide Werte gleich sind
        // Erwartet: 150 Euro (100 + 50)
        assertEquals(150, konto.getKontostand());
    }

    @Test
    @DisplayName("Einzahlen: Mehrere Einzahlungen addieren sich")
    void einzahlenMehrereEinzahlungen() {
        // Arrange: Konto mit 100 Euro
        Konto konto = new Konto(100);

        // Act: Zwei Einzahlungen hintereinander
        kontoService.einzahlen(konto, 50);  // 100 + 50 = 150
        kontoService.einzahlen(konto, 25);  // 150 + 25 = 175

        // Assert: Das Endergebnis sollte 175 Euro sein
        assertEquals(175, konto.getKontostand());
    }

    @Test
    @DisplayName("Einzahlen: Kleiner positiver Betrag (1 Cent)")
    void einzahlenSehrKleinerBetrag() {
        // Arrange
        Konto konto = new Konto(100);

        // Act: Zahle 1 Cent ein
        kontoService.einzahlen(konto, 0.01);

        // Assert: 100 + 0.01 = 100.01
        assertEquals(100.01, konto.getKontostand());
    }

    @Test
    @DisplayName("Einzahlen: Großer Betrag")
    void einzahlenGrosserBetrag() {
        // Arrange
        Konto konto = new Konto(1000);

        // Act: Zahle 1 Million Euro ein (unrealistisch, aber gültig)
        kontoService.einzahlen(konto, 1000000);

        // Assert: 1000 + 1000000 = 1001000
        assertEquals(1001000, konto.getKontostand());
    }

    @Test
    @DisplayName("Einzahlen: Negativer Betrag wirft Exception")
    void einzahlenMitNegativemBetragWirftException() {
        // Arrange
        Konto konto = new Konto(100);

        // === ASSERT + ACT kombiniert ===
        // assertThrows(erwarteteException, aufzurufenderCode)
        // Dies bedeutet: Rufe einzahlen(-10) auf und prüfe, ob IllegalArgumentException geworfen wird
        assertThrows(
            IllegalArgumentException.class,
            () -> kontoService.einzahlen(konto, -10)
        );
        // Wenn KEINE Exception geworfen wird, schlägt dieser Test FEHL!
    }

    @Test
    @DisplayName("Einzahlen: Betrag von 0 wirft Exception")
    void einzahlenMitNullBetragWirftException() {
        // Arrange
        Konto konto = new Konto(100);

        // Act & Assert: assertThrows prüft ob Exception geworfen wird
        // Die Lambda-Expression () -> {...} ist eine anonyme Funktion
        // Sie wird von assertThrows aufgerufen und muss eine Exception werfen
        assertThrows(IllegalArgumentException.class,
                () -> kontoService.einzahlen(konto, 0));
    }

    @Test
    @DisplayName("Einzahlen: Sehr großer negativer Betrag wirft Exception")
    void einzahlenMitSehrGrossemNegativemBetragWirftException() {
        // Arrange
        Konto konto = new Konto(100);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> kontoService.einzahlen(konto, -999999));
    }

    // ============ AUSZAHLEN-TESTS ============

    @Test
    @DisplayName("Auszahlen: Positiver Betrag reduziert den Kontostand")
    void auszahlenVerringertKontostand() {
        // Arrange
        Konto konto = new Konto(100);

        // Act: Zahle 30 Euro aus
        kontoService.auszahlen(konto, 30);

        // Assert: 100 - 30 = 70
        assertEquals(70, konto.getKontostand());
    }

    @Test
    @DisplayName("Auszahlen: Konto wird komplett geleert")
    void auszahlenGanzesBetrag() {
        // Arrange
        Konto konto = new Konto(100);

        // Act: Zahle den kompletten Betrag aus
        kontoService.auszahlen(konto, 100);

        // Assert: Kontostand sollte 0 sein
        assertEquals(0, konto.getKontostand());
    }

    @Test
    @DisplayName("Auszahlen: Mehrere Auszahlungen subtrahieren sich")
    void auszahlenMehrereAuszahlungen() {
        // Arrange
        Konto konto = new Konto(100);

        // Act: Zwei Auszahlungen nacheinander
        kontoService.auszahlen(konto, 30);  // 100 - 30 = 70
        kontoService.auszahlen(konto, 20);  // 70 - 20 = 50

        // Assert: Endergebnis = 50
        assertEquals(50, konto.getKontostand());
    }

    @Test
    @DisplayName("Auszahlen: Nicht genug Guthaben wirft Exception")
    void auszahlenMitNichtGenugGuthabenWirftException() {
        // Arrange: Nur 100 Euro auf dem Konto
        Konto konto = new Konto(100);

        // Act & Assert:
        // Versuche 150 Euro auszuzahlen (unmöglich!)
        // assertThrows erwartet, dass IllegalArgumentException geworfen wird
        assertThrows(IllegalArgumentException.class,
                () -> kontoService.auszahlen(konto, 150));
        // Der Test schlägt FEHL, wenn KEINE Exception geworfen wird
    }

    @Test
    @DisplayName("Auszahlen: Konto hat 0 Euro, Auszahlung wirft Exception")
    void auszahlenMitLeeremKontoWirftException() {
        // Arrange: Kontostand ist 0
        Konto konto = new Konto(0);

        // Act & Assert: Kann nicht von 0 Euro etwas abheben
        assertThrows(IllegalArgumentException.class,
                () -> kontoService.auszahlen(konto, 1));
    }

    @Test
    @DisplayName("Auszahlen: Negativer Betrag wirft Exception")
    void auszahlenMitNegativemBetragWirftException() {
        // Arrange
        Konto konto = new Konto(100);

        // Act & Assert: Man kann keinen negativen Betrag abheben
        assertThrows(IllegalArgumentException.class,
                () -> kontoService.auszahlen(konto, -50));
    }

    @Test
    @DisplayName("Auszahlen: Betrag von 0 wirft Exception")
    void auszahlenMitNullBetragWirftException() {
        // Arrange
        Konto konto = new Konto(100);

        // Act & Assert: Null Euro abheben macht keinen Sinn
        assertThrows(IllegalArgumentException.class,
                () -> kontoService.auszahlen(konto, 0));
    }

    @Test
    @DisplayName("Auszahlen: Würde zu negativem Guthaben führen wirft Exception")
    void auszahlenWuerdeNegativenGuthabenFuehrenWirftException() {
        // Arrange: 50 Euro auf dem Konto
        Konto konto = new Konto(50);

        // Act & Assert:
        // Versuche 50.01 Euro abzuheben (würde -0.01 Euro ergeben)
        // Das ist nicht erlaubt!
        assertThrows(IllegalArgumentException.class,
                () -> kontoService.auszahlen(konto, 50.01));
    }
}
