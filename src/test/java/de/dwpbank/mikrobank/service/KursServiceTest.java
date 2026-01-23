package de.dwpbank.mikrobank.service;

import de.dwpbank.mikrobank.model.Aktie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Tests für KursService
 *
 * 🎓 PRAKTIKANTEN-AUFGABE: TESTS SELBST SCHREIBEN!
 *
 * Diese Test-Klasse ist ein RUMPF (Skeleton). Deine Aufgabe ist es,
 * die Test-Methoden zu implementieren.
 *
 * ANLEITUNG:
 * -----------
 * Jede Test-Methode hat einen Javadoc-Kommentar mit folgenden Teilen:
 *
 * 1. WAS soll getestet werden?
 *    Beschreibung des Vertrags (Contract) der Methode
 *
 * 2. BEDINGUNGEN (Preconditions):
 *    Was muss vorher der Fall sein?
 *
 * 3. ERWARTETES ERGEBNIS (Expected Result):
 *    Was sollte nach dem Test passiert sein?
 *
 * 4. BEISPIEL:
 *    Ein konkretes Code-Beispiel zum Nachahmen
 *
 * DEINE AUFGABE:
 * - Lese den Javadoc genau
 * - Implementiere die Methode nach dem AAA-Pattern:
 *   A) Arrange (Vorbereitung)
 *   B) Act (Ausführung)
 *   C) Assert (Überprüfung)
 * - Nutze assertEquals, assertTrue, assertFalse, assertThrows
 *
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * @author Praktikant
 * @version 1.0
 */
@DisplayName("KursService Tests")
class KursServiceTest {

    private KursService kursService;
    private Aktie apple;

    @BeforeEach
    void setUp() {
        // Diese Methode wird vor JEDEM Test ausgeführt
        // Sie initialisiert einen neuen KursService und Test-Aktien
        kursService = new KursService();
        apple = new Aktie("Apple", 100.00);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEIL 1: KURS SPEICHERN
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Test: speichereKurs() speichert einen gültigen Kurs
     *
     * VERTRAG (Contract):
     * - Die Methode speichereKurs(Aktie) speichert den aktuellen Preis der Aktie
     * - Nach dem Speichern sollte die Kurshistorie ein Element haben
     *
     * BEDINGUNGEN:
     * - Eine Aktie mit Preis 100€ existiert
     * - KursService ist neu (leere Historie)
     *
     * ERWARTET:
     * - Die Methode sollte KEINE Exception werfen
     * - Die Kurshistorie sollte 1 Eintrag enthalten
     * - Der gespeicherte Kurs sollte 100.00 sein
     *
     * BEISPIEL-IMPLEMENTIERUNG:
     * ```java
     * // Arrange
     * Aktie apple = new Aktie("Apple", 100);
     * KursService service = new KursService();
     *
     * // Act
     * service.speichereKurs(apple);
     *
     * // Assert
     * // Prüfe: gibKursHistorie("Apple").size() == 1
     * // Prüfe: gibKursHistorie("Apple").get(0) == 100.0
     * ```
     *
     * TIPP: Nutze die Methode `gibKursHistorie(String aktieName)` um die
     *       gespeicherten Kurse abzurufen und zu prüfen!
     */
    @Test
    @DisplayName("KursService: speichereKurs() speichert einen gültigen Kurs")
    void speichereKurs_validerKurs_wirdGespeichert() {
        // TODO: Implementiere diesen Test nach dem Vertrag oben
        // Schritt 1: Arrange - Aktie vorbereiten
        // Schritt 2: Act - speichereKurs() aufrufen
        // Schritt 3: Assert - mit gibKursHistorie() prüfen
    }

    /**
     * Test: speichereKurs() wirft Exception bei null
     *
     * VERTRAG:
     * - Wenn die Aktie null ist, soll IllegalArgumentException geworfen werden
     *
     * BEDINGUNGEN:
     * - Null wird als Parameter übergeben
     *
     * ERWARTET:
     * - IllegalArgumentException wird geworfen
     *
     * BEISPIEL:
     * ```java
     * // Act & Assert
     * assertThrows(IllegalArgumentException.class, () -> {
     *     kursService.speichereKurs(null);
     * });
     * ```
     */
    @Test
    @DisplayName("KursService: speichereKurs() wirft Exception bei null")
    void speichereKurs_nullAktie_werfException() {
        // TODO: Implementiere diesen Test
        // Nutze assertThrows() um zu prüfen ob Exception geworfen wird
    }

    /**
     * Test: speichereKurs() wirft Exception bei negativem Preis
     *
     * VERTRAG:
     * - Wenn der Kurse ≤ 0 ist, soll IllegalArgumentException geworfen werden
     *
     * BEDINGUNGEN:
     * - Eine Aktie mit Preis -50 oder 0
     *
     * ERWARTET:
     * - IllegalArgumentException wird geworfen
     */
    @Test
    @DisplayName("KursService: speichereKurs() wirft Exception bei Preis ≤ 0")
    void speichereKurs_negativerPreis_werfException() {
        // TODO: Implementiere diesen Test
        // Teste mit Preis -50 und mit Preis 0
    }

    /**
     * Test: speichereKurs() speichert mehrere Kurse nacheinander
     *
     * VERTRAG:
     * - Wenn speichereKurs() mehrfach aufgerufen wird, sollten alle Kurse
     *   in chronologischer Reihenfolge gespeichert sein
     *
     * BEDINGUNGEN:
     * - Mehrere Kurse hintereinander speichern (100, 105, 110)
     *
     * ERWARTET:
     * - Die Kurshistorie hat 3 Einträge
     * - Erstes Element: 100.0
     * - Zweites Element: 105.0
     * - Drittes Element: 110.0
     *
     * BEISPIEL:
     * ```java
     * apple.setPreis(100);
     * kursService.speichereKurs(apple);
     * apple.setPreis(105);
     * kursService.speichereKurs(apple);
     * apple.setPreis(110);
     * kursService.speichereKurs(apple);
     * // Dann prüfen ob alle 3 Kurse in der richtig Reihenfolge sind
     * ```
     */
    @Test
    @DisplayName("KursService: speichereKurs() speichert mehrere Kurse")
    void speichereKurs_mehrerereKurse_werdenGespeichert() {
        // TODO: Implementiere diesen Test
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEIL 2: DURCHSCHNITTSKURS BERECHNEN
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Test: berechnetDurchschnittskurs() berechnet korrekt
     *
     * VERTRAG:
     * - Formel: Durchschnitt = (Kurs1 + Kurs2 + Kurs3) / 3
     *
     * BEDINGUNGEN:
     * - 3 Kurse speichern: 100, 120, 140
     * - Durchschnitt sollte (100+120+140)/3 = 120 sein
     *
     * ERWARTET:
     * - berechnetDurchschnittskurs("Apple") gibt 120.0 zurück
     *
     * BEISPIEL:
     * ```java
     * // Speichere 3 Kurse
     * apple.setPreis(100);
     * kursService.speichereKurs(apple);
     * apple.setPreis(120);
     * kursService.speichereKurs(apple);
     * apple.setPreis(140);
     * kursService.speichereKurs(apple);
     *
     * // Act & Assert
     * double durchschnitt = kursService.berechnetDurchschnittskurs("Apple");
     * assertEquals(120.0, durchschnitt);
     * ```
     */
    @Test
    @DisplayName("KursService: berechnetDurchschnittskurs() berechnet korrekt")
    void berechnetDurchschnittskurs_drehKurse_ergebnis() {
        // TODO: Implementiere diesen Test
        // Speichere 3 Kurse, berechne Durchschnitt, prüfe ob er korrekt ist
    }

    /**
     * Test: berechnetDurchschnittskurs() gibt 0 zurück für unbekannte Aktie
     *
     * VERTRAG:
     * - Wenn eine Aktie keine Kurshistorie hat, soll 0.0 zurückgegeben werden
     *
     * BEDINGUNGEN:
     * - Nie speichereKurs() für diese Aktie aufgerufen
     *
     * ERWARTET:
     * - berechnetDurchschnittskurs("Unknown") gibt 0.0 zurück
     */
    @Test
    @DisplayName("KursService: berechnetDurchschnittskurs() gibt 0 für unbekannte Aktie")
    void berechnetDurchschnittskurs_unbekannteAktie_gibt0() {
        // TODO: Implementiere diesen Test
    }

    /**
     * Test: berechnetDurchschnittskurs() mit nur einem Kurs
     *
     * VERTRAG:
     * - Wenn nur ein Kurs gespeichert ist, sollte dieser als Durchschnitt zurückgegeben werden
     *
     * BEDINGUNGEN:
     * - Nur einen Kurs speichern (150.0)
     *
     * ERWARTET:
     * - berechnetDurchschnittskurs() gibt 150.0 zurück
     */
    @Test
    @DisplayName("KursService: berechnetDurchschnittskurs() mit einem Kurs")
    void berechnetDurchschnittskurs_einKurs_gibtDiesenKursZurueck() {
        // TODO: Implementiere diesen Test
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEIL 3: TREND BESTIMMEN
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Test: bestimmeTrend() gibt "STEIGEND" zurück bei steigendem Trend
     *
     * VERTRAG:
     * - STEIGEND: Durchschnitt der letzten 5 Kurse > Durchschnitt der 5 davor
     *
     * BEDINGUNGEN:
     * - 10 Kurse speichern
     * - Erste 5: 100, 100, 100, 100, 100 (Durchschnitt = 100)
     * - Nächste 5: 120, 120, 120, 120, 120 (Durchschnitt = 120)
     *
     * ERWARTET:
     * - bestimmeTrend("Apple") gibt "STEIGEND" zurück
     *
     * ANLEITUNG:
     * Loop zum Speichern von 10 Kursen:
     * ```java
     * double[] kurse = {100,100,100,100,100, 120,120,120,120,120};
     * for (double kurs : kurse) {
     *     apple.setPreis(kurs);
     *     kursService.speichereKurs(apple);
     * }
     * ```
     */
    @Test
    @DisplayName("KursService: bestimmeTrend() erkennt STEIGEND")
    void bestimmeTrend_steigerndeTrend_gibtSTEIGENDZurueck() {
        // TODO: Implementiere diesen Test
    }

    /**
     * Test: bestimmeTrend() gibt "FALLEND" zurück bei fallendem Trend
     *
     * VERTRAG:
     * - FALLEND: Durchschnitt der letzten 5 Kurse < Durchschnitt der 5 davor
     *
     * BEDINGUNGEN:
     * - 10 Kurse speichern
     * - Erste 5: 120, 120, 120, 120, 120 (Durchschnitt = 120)
     * - Nächste 5: 100, 100, 100, 100, 100 (Durchschnitt = 100)
     *
     * ERWARTET:
     * - bestimmeTrend("Apple") gibt "FALLEND" zurück
     */
    @Test
    @DisplayName("KursService: bestimmeTrend() erkennt FALLEND")
    void bestimmeTrend_fallendenTrend_gibtFALLENDZurueck() {
        // TODO: Implementiere diesen Test
    }

    /**
     * Test: bestimmeTrend() gibt "STABIL" zurück bei stabilen Kursen
     *
     * VERTRAG:
     * - STABIL: Kurse unterscheiden sich um weniger als 2%
     *
     * BEDINGUNGEN:
     * - 10 Kurse speichern, die alle zwischen 100-101 liegen
     *
     * ERWARTET:
     * - bestimmeTrend("Apple") gibt "STABIL" zurück
     */
    @Test
    @DisplayName("KursService: bestimmeTrend() erkennt STABIL")
    void bestimmeTrend_stabile_kurse_gibtSTABILZurueck() {
        // TODO: Implementiere diesen Test
    }

    /**
     * Test: bestimmeTrend() gibt "UNBEKANNT" zurück bei zu wenigen Kursen
     *
     * VERTRAG:
     * - Wenn weniger als 10 Kurse vorhanden sind, kann kein Trend berechnet werden
     * - In diesem Fall: "UNBEKANNT" zurückgeben
     *
     * BEDINGUNGEN:
     * - Nur 5 Kurse speichern
     *
     * ERWARTET:
     * - bestimmeTrend("Apple") gibt "UNBEKANNT" zurück
     */
    @Test
    @DisplayName("KursService: bestimmeTrend() gibt UNBEKANNT bei zu wenigen Kursen")
    void bestimmeTrend_wenigKurse_gibtUNBEKANNTZurueck() {
        // TODO: Implementiere diesen Test
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // TEIL 4: KURSHISTORIE ABRUFEN
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Test: gibKursHistorie() gibt gespeicherte Kurse zurück
     *
     * VERTRAG:
     * - gibKursHistorie() gibt eine Liste aller gespeicherten Kurse zurück
     *
     * BEDINGUNGEN:
     * - 3 Kurse speichern: 100, 110, 120
     *
     * ERWARTET:
     * - gibKursHistorie("Apple").size() == 3
     * - Reihenfolge: [100, 110, 120]
     */
    @Test
    @DisplayName("KursService: gibKursHistorie() gibt gespeicherte Kurse zurück")
    void gibKursHistorie_gespeichertKurse_werden() {
        // TODO: Implementiere diesen Test
    }

    /**
     * Test: gibKursHistorie() gibt leere Liste für unbekannte Aktie zurück
     *
     * VERTRAG:
     * - Wenn eine Aktie noch nicht gespeichert wurde, soll eine leere Liste zurückgegeben werden
     *
     * ERWARTET:
     * - gibKursHistorie("Unknown").isEmpty() == true
     */
    @Test
    @DisplayName("KursService: gibKursHistorie() gibt leere Liste für unbekannte Aktie")
    void gibKursHistorie_unbekannteAktie_leereListe() {
        // TODO: Implementiere diesen Test
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // BONUS: EIGENE TESTS (Optional)
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * TODO: Schreibe einen eigenen Test!
     *
     * Ideen:
     * - Was passiert wenn man 100 Kurse speichert? (Speicherbegrenzung testen)
     * - Unterschiedliche Aktien gleichzeitig? (Apple und BMW)
     * - Dezimal-Kurse wie 99.99?
     */

}
