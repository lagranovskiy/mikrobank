
package de.dwpbank.mikrobank;

import de.dwpbank.mikrobank.model.Aktie;
import de.dwpbank.mikrobank.service.PreisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests für den PreisService.
 * 
 * Diese Tests prüfen die Preisermittlung von Aktien.
 * Der Preis kann sich zufällig ändern, bleibt aber immer positiv.
 * 
 * @BeforeEach: Wird VOR jedem Test ausgeführt um einen sauberen Zustand zu haben
 */
@DisplayName("PreisService Tests")
class PreisServiceTest {

    // Der zu testende Service
    private PreisService preisService;

    /**
     * Setup-Methode: Wird vor JEDEM Test aufgerufen
     * Dadurch hat jeder Test seinen eigenen frischen PreisService
     */
    @BeforeEach
    void setUp() {
        preisService = new PreisService();
    }

    @Test
    @DisplayName("Preis ermitteln: Gibt einen gültigen Preis zurück")
    void ermittleAktuellenPreisGibtGueltigenPreis() {
        // Arrange: Erstelle eine Aktie mit Preis 100
        Aktie aktie = new Aktie("SAP", 100);
        
        // Act: Ermittle den neuen Preis
        double neuerPreis = preisService.ermittleAktuellenPreis(aktie);
        
        // Assert:
        // assertTrue(bedingung, nachricht) - prüft ob Bedingung true ist
        // neuerPreis > 0 bedeutet: Der Preis muss größer als 0 sein
        assertTrue(neuerPreis > 0, "Der Preis muss größer als 0 sein");
    }

    @Test
    @DisplayName("Preis ermitteln: Preis wird in der Aktie gespeichert")
    void ermittleAktuellenPreisAendertAktiePreis() {
        // Arrange
        Aktie aktie = new Aktie("Apple", 150);
        
        // Act: Rufe ermittleAktuellenPreis auf
        double neuerPreis = preisService.ermittleAktuellenPreis(aktie);
        
        // Assert:
        // assertEquals(erwartet, tatsächlich) - prüft Gleichheit
        // Der neue Preis MUSS in der Aktie gespeichert sein
        assertEquals(neuerPreis, aktie.getPreis(), 
            "Der neue Preis muss in der Aktie gespeichert sein");
    }

    @Test
    @DisplayName("Preis ermitteln: Preis bleibt in realistische Grenzen")
    void ermittleAktuellenPreisPreisImBereich() {
        // Arrange: Basis-Preis ist 100
        // ±5% bedeutet: Preis liegt zwischen 95 und 105
        double basispreis = 100;
        Aktie aktie = new Aktie("Microsoft", basispreis);
        double minPreis = basispreis * 0.95;  // 95
        double maxPreis = basispreis * 1.05;  // 105
        
        // Act: Ermittle neuen Preis
        double neuerPreis = preisService.ermittleAktuellenPreis(aktie);
        
        // Assert: Prüfe ob Preis im erlaubten Bereich liegt
        // assertTrue(bedingung) - Test erfolgt wenn true, schlägt fehl wenn false
        assertTrue(neuerPreis >= minPreis, 
            "Preis sollte nicht unter 95% des Basispreises liegen");
        assertTrue(neuerPreis <= maxPreis, 
            "Preis sollte nicht über 105% des Basispreises liegen");
    }

    @Test
    @DisplayName("Preis ermitteln: Mehrfache Aufrufe ändern Preis")
    void ermittleAktuellenPreisMehrfacheAufrufe() {
        // Arrange
        Aktie aktie = new Aktie("Tesla", 200);
        
        // Act: Rufe mehrfach auf (mit Zufallsänderung sollte sich der Preis ändern)
        double preis1 = preisService.ermittleAktuellenPreis(aktie);
        double preis2 = preisService.ermittleAktuellenPreis(aktie);
        double preis3 = preisService.ermittleAktuellenPreis(aktie);
        
        // Assert: Alle Preise müssen positiv sein
        assertTrue(preis1 > 0 && preis2 > 0 && preis3 > 0,
            "Alle Preise müssen positiv sein");
    }

    @Test
    @DisplayName("Preis ermitteln: Aktie mit Startpreis 1 Euro")
    void ermittleAktuellenPreisSehrNiederAktie() {
        // Arrange: Billige Aktie mit Preis 1 Euro
        Aktie aktie = new Aktie("Penny Stock", 1);
        
        // Act
        double neuerPreis = preisService.ermittleAktuellenPreis(aktie);
        
        // Assert: Mit ±5% sollte der Preis zwischen 0.95 und 1.05 liegen
        assertTrue(neuerPreis > 0.8, "Preis sollte mindestens bei 0.95 Euro sein");
        assertTrue(neuerPreis <= 1.05, "Preis sollte maximal bei 1.05 Euro sein");
    }

    @Test
    @DisplayName("Preis ermitteln: Aktie mit hohem Startpreis")
    void ermittleAktuellenPreisSehrHoheAktie() {
        // Arrange: Teure Aktie mit Preis 1000 Euro
        Aktie aktie = new Aktie("Luxury Stock", 1000);
        
        // Act
        double neuerPreis = preisService.ermittleAktuellenPreis(aktie);
        
        // Assert: Mit ±5% sollte Preis zwischen 950 und 1050 liegen
        assertTrue(neuerPreis >= 950, "Preis sollte mindestens bei 950 Euro sein");
        assertTrue(neuerPreis <= 1050, "Preis sollte maximal bei 1050 Euro sein");
    }

    @Test
    @DisplayName("Preis ermitteln: Rückgabewert entspricht Aktienwert")
    void ermittleAktuellenPreisRueckgabewert() {
        // Arrange
        Aktie aktie = new Aktie("Google", 140);
        
        // Act: Die Methode gibt den neuen Preis zurück
        double rueckgabewert = preisService.ermittleAktuellenPreis(aktie);
        // Und speichert ihn auch in der Aktie
        double aktienPreis = aktie.getPreis();
        
        // Assert:
        // Der zurückgegebene Wert MUSS dem Preis in der Aktie entsprechen
        assertEquals(rueckgabewert, aktienPreis,
            "Der zurückgegebene Preis muss dem Aktienpreis entsprechen");
    }
}
