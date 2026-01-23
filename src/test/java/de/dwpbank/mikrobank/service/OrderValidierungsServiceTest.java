
package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Aktie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests für den OrderValidierungsService.
 *
 * Diese Tests prüfen die Validierung von Kauf- und Verkaufsorder.
 * Die Order muss eine gültige Aktie und eine positive Menge haben.
 *
 * Syntax-Erklärung:
 * - assertDoesNotThrow(aufzurufenderCode): Test erfolgreich wenn KEINE Exception
 * - assertThrows(exceptionTyp, aufzurufenderCode): Test erfolgreich wenn Exception geworfen wird
 */
@DisplayName("OrderValidierungsService Tests")
class OrderValidierungsServiceTest {

    // Der zu testende Service
    private OrderValidierungsService validierungsService;

    /**
     * @BeforeEach: Diese Methode wird VOR jedem Test aufgerufen
     * Dies stellt sicher, dass jeder Test mit einem frischen Service startet
     */
    @BeforeEach
    void setUp() {
        validierungsService = new OrderValidierungsService();
    }

    // ============ VALIDIERE KAUF TESTS ============

    @Test
    @DisplayName("Kauf validieren: Gültige Order wird akzeptiert")
    void validierekaufgueltigeOrder() {
        // Arrange
        Aktie aktie = new Aktie("Apple", 150);
        int menge = 10;

        // Act & Assert
        // Diese Methode sollte keine Exception werfen, wenn alles gültig ist
        assertDoesNotThrow(() -> validierungsService.validiereKauf(aktie, menge),
            "Eine gültige Kauforder sollte akzeptiert werden");
    }

    @Test
    @DisplayName("Kauf validieren: Menge 1")
    void validierekaufmengeSehr() {
        // Arrange
        Aktie aktie = new Aktie("SAP", 100);
        int menge = 1;

        // Act & Assert
        assertDoesNotThrow(() -> validierungsService.validiereKauf(aktie, menge),
            "Eine Kauforder mit Menge 1 sollte gültig sein");
    }

    @Test
    @DisplayName("Kauf validieren: Große Menge")
    void validierekaufgrosseMenge() {
        // Arrange
        Aktie aktie = new Aktie("Microsoft", 200);
        int menge = 10000;

        // Act & Assert
        assertDoesNotThrow(() -> validierungsService.validiereKauf(aktie, menge),
            "Eine Kauforder mit großer Menge sollte gültig sein");
    }

    @Test
    @DisplayName("Kauf validieren: Null-Aktie wirft Exception")
    void validierekaufmitNullAktie_wirftException() {
        // Arrange
        Aktie aktie = null;
        int menge = 10;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereKauf(aktie, menge),
            "Eine Kauforder ohne Aktie (null) sollte ungültig sein");
    }

    @Test
    @DisplayName("Kauf validieren: Menge 0 wirft Exception")
    void validierekaufmitMengeNull_wirftException() {
        // Arrange
        Aktie aktie = new Aktie("Tesla", 200);
        int menge = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereKauf(aktie, menge),
            "Eine Kauforder mit Menge 0 sollte ungültig sein");
    }

    @Test
    @DisplayName("Kauf validieren: Negative Menge wirft Exception")
    void validierekaufmitNegativerMenge_wirftException() {
        // Arrange
        Aktie aktie = new Aktie("Google", 140);
        int menge = -5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereKauf(aktie, menge),
            "Eine Kauforder mit negativer Menge sollte ungültig sein");
    }

    @Test
    @DisplayName("Kauf validieren: Null-Aktie UND negative Menge")
    void validierekaufmitNullAktieUndNegativerMenge_wirftException() {
        // Arrange
        Aktie aktie = null;
        int menge = -10;

        // Act & Assert
        // Es sollte eine Exception geworfen werden (wahrscheinlich für Aktie zuerst)
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereKauf(aktie, menge),
            "Eine ungültige Kauforder sollte abgelehnt werden");
    }

    // ============ VALIDIERE VERKAUF TESTS ============

    @Test
    @DisplayName("Verkauf validieren: Gültige Order wird akzeptiert")
    void validiereverkaufgueltigeOrder() {
        // Arrange
        Aktie aktie = new Aktie("Apple", 150);
        int menge = 5;

        // Act & Assert
        assertDoesNotThrow(() -> validierungsService.validiereVerkauf(aktie, menge),
            "Eine gültige Verkaufsorder sollte akzeptiert werden");
    }

    @Test
    @DisplayName("Verkauf validieren: Menge 1")
    void validiereverkaufmengeSehr() {
        // Arrange
        Aktie aktie = new Aktie("SAP", 100);
        int menge = 1;

        // Act & Assert
        assertDoesNotThrow(() -> validierungsService.validiereVerkauf(aktie, menge),
            "Eine Verkaufsorder mit Menge 1 sollte gültig sein");
    }

    @Test
    @DisplayName("Verkauf validieren: Große Menge")
    void validiereverkaufgrosseMenge() {
        // Arrange
        Aktie aktie = new Aktie("Microsoft", 200);
        int menge = 5000;

        // Act & Assert
        assertDoesNotThrow(() -> validierungsService.validiereVerkauf(aktie, menge),
            "Eine Verkaufsorder mit großer Menge sollte gültig sein");
    }

    @Test
    @DisplayName("Verkauf validieren: Null-Aktie wirft Exception")
    void validiereverkaufmitNullAktie_wirftException() {
        // Arrange
        Aktie aktie = null;
        int menge = 5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereVerkauf(aktie, menge),
            "Eine Verkaufsorder ohne Aktie (null) sollte ungültig sein");
    }

    @Test
    @DisplayName("Verkauf validieren: Menge 0 wirft Exception")
    void validiereverkaufmitMengeNull_wirftException() {
        // Arrange
        Aktie aktie = new Aktie("Tesla", 200);
        int menge = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereVerkauf(aktie, menge),
            "Eine Verkaufsorder mit Menge 0 sollte ungültig sein");
    }

    @Test
    @DisplayName("Verkauf validieren: Negative Menge wirft Exception")
    void validiereverkaufmitNegativerMenge_wirftException() {
        // Arrange
        Aktie aktie = new Aktie("Google", 140);
        int menge = -10;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereVerkauf(aktie, menge),
            "Eine Verkaufsorder mit negativer Menge sollte ungültig sein");
    }

    @Test
    @DisplayName("Verkauf validieren: Null-Aktie UND negative Menge")
    void validiereverkaufmitNullAktieUndNegativerMenge_wirftException() {
        // Arrange
        Aktie aktie = null;
        int menge = -5;

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> validierungsService.validiereVerkauf(aktie, menge),
            "Eine ungültige Verkaufsorder sollte abgelehnt werden");
    }
}
