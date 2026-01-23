package de.dwpbank.mikrobank.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Referenztests für die Konto-Klasse
 *
 * Diese Tests demonstrieren, wie die Konto-Klasse funktioniert
 * und dienen als Beispiel für das Testen von Model-Klassen.
 */
class KontoTest {

    @Test
    void kontoConstructorInitialisesKontostand() {
        Konto konto = new Konto(100);
        assertEquals(100, konto.getKontostand());
    }

    @Test
    void kontoStartsWithZero() {
        Konto konto = new Konto(0);
        assertEquals(0, konto.getKontostand());
    }

    @Test
    void kontoAcceptsLargeAmounts() {
        Konto konto = new Konto(1000000);
        assertEquals(1000000, konto.getKontostand());
    }

    @Test
    void kontoAcceptsDecimalAmounts() {
        Konto konto = new Konto(99.99);
        assertEquals(99.99, konto.getKontostand());
    }

    @Test
    void setKontostandUpdatesBalance() {
        Konto konto = new Konto(100);
        konto.setKontostand(200);
        assertEquals(200, konto.getKontostand());
    }

    @Test
    void setKontostandCanSetToZero() {
        Konto konto = new Konto(100);
        konto.setKontostand(0);
        assertEquals(0, konto.getKontostand());
    }

    @Test
    void setKontostandCanIncrease() {
        Konto konto = new Konto(50);
        konto.setKontostand(150);
        assertTrue(konto.getKontostand() > 50);
        assertEquals(150, konto.getKontostand());
    }

    @Test
    void setKontostandCanDecrease() {
        Konto konto = new Konto(150);
        konto.setKontostand(50);
        assertTrue(konto.getKontostand() < 150);
        assertEquals(50, konto.getKontostand());
    }

    @Test
    void multipleKontosAreIndependent() {
        Konto konto1 = new Konto(100);
        Konto konto2 = new Konto(200);

        konto1.setKontostand(150);

        assertEquals(150, konto1.getKontostand());
        assertEquals(200, konto2.getKontostand());
    }

    @Test
    void kontoHandlesNegativeBalance() {
        Konto konto = new Konto(100);
        konto.setKontostand(-50);
        assertEquals(-50, konto.getKontostand());
    }
}
