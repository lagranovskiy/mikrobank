
package de.dwpbank.mikrobank;

import de.dwpbank.mikrobank.model.Aktie;
import de.dwpbank.mikrobank.model.Konto;
import de.dwpbank.mikrobank.service.BoerseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests für den BoerseService.
 * 
 * Diese Tests prüfen den kompletten Aktienkauf-Prozess.
 * Der Kauf ist erfolgreich, wenn:
 * 1. Die Order gültig ist
 * 2. Der Preis ermittelt wird
 * 3. Genug Guthaben vorhanden ist
 * 4. Das Geld vom Konto abgebucht wird
 */
@DisplayName("BoerseService Tests")
class BoerseServiceTest {

    // Der zu testende Service
    private BoerseService boerseService;

    /**
     * Wird vor jedem Test ausgeführt.
     */
    @BeforeEach
    void setUp() {
        boerseService = new BoerseService();
    }

    // ============ ERFOLGREICHE KÄUFE ============

    @Test
    @DisplayName("Kauf: Einfacher erfolgreicher Kauf")
    void kaufeerfolgreicherKauf() {
        // Arrange
        Konto konto = new Konto(1000);  // 1000 Euro auf dem Konto
        Aktie aktie = new Aktie("Apple", 100);  // Preis 100 Euro pro Aktie
        int menge = 5;  // Wir wollen 5 Aktien kaufen = 500 Euro
        
        // Act
        // Der Kauf sollte ohne Exception erfolgreich sein
        assertDoesNotThrow(() -> boerseService.kaufe(konto, aktie, menge),
            "Ein gültiger Kauf mit ausreichend Guthaben sollte erfolgreich sein");
    }

    @Test
    @DisplayName("Kauf: Guthaben wird reduziert")
    void kaufeguthaben_wird_reduziert() {
        // Arrange
        Konto konto = new Konto(500);
        Aktie aktie = new Aktie("SAP", 100);
        int menge = 2;  // 2 Aktien * 100 Euro = 200 Euro

        double guthabenVorher = konto.getKontostand();
        
        // Act
        boerseService.kaufe(konto, aktie, menge);
        
        // Assert
        assertTrue(konto.getKontostand() < guthabenVorher,
            "Das Guthaben sollte nach dem Kauf reduziert sein");
    }

    @Test
    @DisplayName("Kauf: Mit exakt benötigtem Guthaben")
    void kaufemitExaktBenoetigtemGuthaben() {
        // Arrange
        Aktie aktie = new Aktie("Microsoft", 100);
        int menge = 5;  // 5 Aktien * 100 Euro = 500 Euro
        Konto konto = new Konto(500);  // Exakt 500 Euro
        
        // Act & Assert
        assertDoesNotThrow(() -> boerseService.kaufe(konto, aktie, menge),
            "Ein Kauf mit exakt benötigtem Guthaben sollte erfolgreich sein");
    }

    @Test
    @DisplayName("Kauf: Eine Aktie kaufen")
    void kaufeeineAktie() {
        // Arrange
        Konto konto = new Konto(200);
        Aktie aktie = new Aktie("Tesla", 100);
        int menge = 1;
        
        // Act & Assert
        assertDoesNotThrow(() -> boerseService.kaufe(konto, aktie, menge),
            "Der Kauf einer einzelnen Aktie sollte erfolgreich sein");
    }

    @Test
    @DisplayName("Kauf: Viele Aktien kaufen")
    void kaufevieleAktien() {
        // Arrange
        Konto konto = new Konto(100000);  // 100.000 Euro
        Aktie aktie = new Aktie("Google", 100);
        int menge = 500;  // 500 Aktien
        
        // Act & Assert
        assertDoesNotThrow(() -> boerseService.kaufe(konto, aktie, menge),
            "Der Kauf vieler Aktien sollte möglich sein");
    }

    // ============ FEHLGESCHLAGENE KÄUFE - UNGÜLTIGE ORDNUNG ============

    @Test
    @DisplayName("Kauf: Ungültig - null Aktie")
    void kaufemitNullAktie_wirftException() {
        // Arrange
        Konto konto = new Konto(1000);
        Aktie aktie = null;
        int menge = 5;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> boerseService.kaufe(konto, aktie, menge),
            "Ein Kauf ohne Aktie sollte abgelehnt werden");
    }

    @Test
    @DisplayName("Kauf: Ungültig - Menge 0")
    void kaufemitMengeNull_wirftException() {
        // Arrange
        Konto konto = new Konto(1000);
        Aktie aktie = new Aktie("Apple", 100);
        int menge = 0;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> boerseService.kaufe(konto, aktie, menge),
            "Ein Kauf mit Menge 0 sollte abgelehnt werden");
    }

    @Test
    @DisplayName("Kauf: Ungültig - Negative Menge")
    void kaufemitNegativerMenge_wirftException() {
        // Arrange
        Konto konto = new Konto(1000);
        Aktie aktie = new Aktie("SAP", 100);
        int menge = -5;
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> boerseService.kaufe(konto, aktie, menge),
            "Ein Kauf mit negativer Menge sollte abgelehnt werden");
    }

    // ============ FEHLGESCHLAGENE KÄUFE - NICHT GENUG GUTHABEN ============

    @Test
    @DisplayName("Kauf: Nicht genug Guthaben")
    void kaufenichtGenugGuthaben_wirftException() {
        // Arrange
        Konto konto = new Konto(200);  // 200 Euro
        Aktie aktie = new Aktie("Apple", 100);  // 100 Euro pro Aktie
        int menge = 5;  // Würde 500 Euro kosten
        
        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> boerseService.kaufe(konto, aktie, menge),
            "Ein Kauf ohne ausreichend Guthaben sollte abgelehnt werden");
    }

    @Test
    @DisplayName("Kauf: Guthaben 0, aber Kauf > 0")
    void kaufemitLeeremKonto_wirftException() {
        // Arrange
        Konto konto = new Konto(0);
        Aktie aktie = new Aktie("Microsoft", 100);
        int menge = 1;
        
        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> boerseService.kaufe(konto, aktie, menge),
            "Ein Kauf mit leerem Konto sollte abgelehnt werden");
    }

    @Test
    @DisplayName("Kauf: Guthaben um 1 Euro zu klein")
    void kaufeguthaben_um_1_euro_zu_klein() {
        // Arrange
        Konto konto = new Konto(99);  // 99 Euro
        Aktie aktie = new Aktie("Google", 100);  // 100 Euro pro Aktie
        int menge = 1;  // Würde 100 Euro kosten
        
        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> boerseService.kaufe(konto, aktie, menge),
            "Ein Kauf mit zu wenig Guthaben sollte abgelehnt werden");
    }

    // ============ GRENZFÄLLE ============

    @Test
    @DisplayName("Kauf: Sehr kleine Aktie, sehr kleine Menge")
    void kaufesehrKleineAktie() {
        // Arrange
        Konto konto = new Konto(10);
        Aktie aktie = new Aktie("Penny", 1);  // 1 Euro pro Aktie
        int menge = 5;  // 5 Euro
        
        // Act & Assert
        assertDoesNotThrow(() -> boerseService.kaufe(konto, aktie, menge),
            "Der Kauf von günstigen Aktien sollte möglich sein");
    }

    @Test
    @DisplayName("Kauf: Teure Aktie, kleine Menge")
    void kaufeteureAktie() {
        // Arrange
        Konto konto = new Konto(5000);
        Aktie aktie = new Aktie("Luxury", 1000);  // 1000 Euro pro Aktie
        int menge = 3;  // 3000 Euro
        
        // Act & Assert
        assertDoesNotThrow(() -> boerseService.kaufe(konto, aktie, menge),
            "Der Kauf teurer Aktien sollte möglich sein");
    }
}
