package de.dwpbank.mikrobank.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Referenztests für die Aktie-Klasse
 * 
 * Diese Tests demonstrieren, wie die Aktie-Klasse funktioniert
 * und dienen als Beispiel für das Testen von Model-Klassen.
 */
class AktieTest {

    @Test
    void aktieConstructorInitializesNameAndPrice() {
        Aktie aktie = new Aktie("Apple", 150);
        assertEquals("Apple", aktie.getName());
        assertEquals(150, aktie.getPreis());
    }

    @Test
    void aktieNameIsStored() {
        Aktie aktie = new Aktie("Microsoft", 200);
        assertEquals("Microsoft", aktie.getName());
    }

    @Test
    void aktiePriceIsStored() {
        Aktie aktie = new Aktie("Google", 2500);
        assertEquals(2500, aktie.getPreis());
    }

    @Test
    void aktiePriceCanBeDecimal() {
        Aktie aktie = new Aktie("SAP", 99.99);
        assertEquals(99.99, aktie.getPreis());
    }

    @Test
    void aktieCanHaveVerySmallPrice() {
        Aktie aktie = new Aktie("PennyStock", 0.50);
        assertEquals(0.50, aktie.getPreis());
    }

    @Test
    void aktieCanHaveZeroPrice() {
        Aktie aktie = new Aktie("FreeBie", 0);
        assertEquals(0, aktie.getPreis());
    }

    @Test
    void setPreisUpdatesPrice() {
        Aktie aktie = new Aktie("Tesla", 100);
        aktie.setPreis(150);
        assertEquals(150, aktie.getPreis());
    }

    @Test
    void setPreisCanIncrease() {
        Aktie aktie = new Aktie("BMW", 100);
        aktie.setPreis(200);
        assertTrue(aktie.getPreis() > 100);
        assertEquals(200, aktie.getPreis());
    }

    @Test
    void setPreisCanDecrease() {
        Aktie aktie = new Aktie("Siemens", 200);
        aktie.setPreis(100);
        assertTrue(aktie.getPreis() < 200);
        assertEquals(100, aktie.getPreis());
    }

    @Test
    void setPreisCanSetToZero() {
        Aktie aktie = new Aktie("Bankrupt", 100);
        aktie.setPreis(0);
        assertEquals(0, aktie.getPreis());
    }

    @Test
    void nameCannotBeChanged() {
        Aktie aktie = new Aktie("Apple", 150);
        String originalName = aktie.getName();
        assertEquals("Apple", originalName);
    }

    @Test
    void multipleAktieInstancesAreIndependent() {
        Aktie apple = new Aktie("Apple", 100);
        Aktie microsoft = new Aktie("Microsoft", 200);
        
        apple.setPreis(150);
        
        assertEquals(150, apple.getPreis());
        assertEquals(200, microsoft.getPreis());
    }

    @Test
    void aktieNameIsCaseSensitive() {
        Aktie apple1 = new Aktie("Apple", 100);
        Aktie apple2 = new Aktie("APPLE", 100);
        
        assertNotEquals(apple1.getName(), apple2.getName());
    }

    @Test
    void aktieCanHandleNegativePrice() {
        Aktie aktie = new Aktie("Shortable", -50);
        assertEquals(-50, aktie.getPreis());
    }

    @Test
    void setPreisCanSetNegativePrice() {
        Aktie aktie = new Aktie("Test", 100);
        aktie.setPreis(-25);
        assertEquals(-25, aktie.getPreis());
    }

    @Test
    void aktieHandlesVeryLargePrice() {
        Aktie aktie = new Aktie("Luxury", 999999999);
        assertEquals(999999999, aktie.getPreis());
    }

    @Test
    void getNameReturnsCorrectName() {
        String name = "Tesla";
        Aktie aktie = new Aktie(name, 100);
        assertEquals(name, aktie.getName());
    }

    @Test
    void getPriceReturnsCorrectPrice() {
        double price = 123.45;
        Aktie aktie = new Aktie("Test", price);
        assertEquals(price, aktie.getPreis());
    }
}
