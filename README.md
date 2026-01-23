# 🏦 mikrobank - Mini-Börse für Aktienhandel

> Eine elegante Java-Anwendung, die die Grundprinzipien eines modernen Aktienhandelssystems demonstriert.

## 📋 Was ist mikrobank?

**mikrobank** ist eine Mini-Börse (Aktienbörse), auf der Kunden Aktien kaufen und verkaufen können. Das System verwaltet Kundenkonten, Aktienpreise und führt Transaktionen durch – genau wie eine echte Bank, nur in miniaturisiert und für Lernzwecke perfekt!

### Kernfunktionen

✅ **Kontoführung** - Einzahlungen und Auszahlungen verwalten  
✅ **Aktienhandel** - Aktien kaufen und verkaufen  
✅ **Preisermittlung** - Dynamische Aktienkurse mit realistischen Preisschwankungen  
✅ **Ordervalidierung** - Prüfung von Kaufanfragen auf Gültigkeit  
✅ **Transaktionslogik** - Sichere Abwicklung von Aktiengeschäften  

---

## 🎯 Wie funktioniert es?

### 1. Das System hat 4 Komponenten:

#### 🏪 **Model Layer** - Die Datenstrukturen
- **Aktie**: Eine Börsenaktie mit Name und aktuellem Preis
- **Konto**: Ein Bankkonto mit Kontostand

#### ⚙️ **Service Layer** - Die Geschäftslogik
- **KontoService**: Verwaltet Einzahlungen und Auszahlungen
- **PreisService**: Berechnet aktuelle Aktienkurse
- **OrderValidierungsService**: Prüft ob Kauf-/Verkaufsorder gültig sind
- **BoerseService**: Orchestriert den kompletten Aktienkauf-/Verkaufsprozess

#### 🤖 **HandelsRoboter** - Automatisierter Trader
- Intelligenter Trader, der Aktien automatisch kauft und verkauft
- Nutzt die Services um Entscheidungen zu treffen
- Verwaltet ein Depot mit gehaltenen Aktien

#### 🎮 **Boersensimulator** - Demo & Visualisierung
- Interaktives Programm zur Demonstration des gesamten Systems
- Börse simuliert Kursbewegungen
- HandelsRoboter reagiert auf die Kurse
- Ermöglicht es dem Praktikanten zu sehen, wie alles zusammenspielt

### 2. Ein typisches Aktienkauf-Szenario:

```
Kunde möchte 5 Apple-Aktien kaufen
    ↓
1. Wird die Order validiert? (Aktie gültig? Menge > 0?)
    ↓
2. Wie ist der aktuelle Aktienkurs?
    ↓
3. Berechnung: 5 Aktien × 150€ = 750€
    ↓
4. Hat der Kunde 750€ auf dem Konto?
    ↓
5. Geld vom Konto abbuchen ✓
```

### 3. Ein Beispiel in Code:

```java
// Konto mit 1000 Euro erstellen
Konto konto = new Konto(1000);

// Apple Aktie mit Kurs 150 Euro
Aktie apple = new Aktie("Apple", 150);

// 5 Aktien kaufen
BoerseService boerse = new BoerseService();
boerse.kaufe(konto, apple, 5);

// Konto hat jetzt weniger Guthaben
System.out.println(konto.getKontostand()); // ~750 Euro
```

---

## 🛠️ Projekt-Setup

### Voraussetzungen

- **Java 17** oder höher
- **Maven 3.6** oder höher

### Installation

```bash
# Repository klonen
git clone <repository-url>
cd mikrobank

# Projekt kompilieren
mvn clean compile

# Tests ausführen
mvn test

# Projekt bauen
mvn package
```

### Struktur

```
mikrobank/
├── src/
│   ├── main/java/de/dwpbank/mikrobank/
│   │   ├── model/              # Datenmodelle (Aktie, Konto)
│   │   ├── service/            # Geschäftslogik (Services)
│   │   ├── HandelsRoboter.java # Automatisierter Trader
│   │   └── Boersensimulator.java # Demo-Programm
│   └── test/java/de/dwpbank/mikrobank/
│       ├── model/              # Model Tests (AktieTest, KontoTest)
│       └── service/            # Service Tests
├── pom.xml                     # Maven Konfiguration
├── README.md                   # Diese Datei
└── PRAKTIKUM_PLAN.md          # Praktikums-Anleitung
```

---

## 🧪 Unit Tests

Das Projekt enthält über **100 umfangreiche Unit Tests**, die die gesamte Geschäftslogik validieren:

- ✅ **Model Tests** (28 Tests) - Datenmodelle validieren
  - AktieTest (18 Tests) - Aktien-Validierung
  - KontoTest (10 Tests) - Konto-Validierung
- ✅ **KontoServiceTest** (15 Tests) - Einzahlungen und Auszahlungen
- ✅ **PreisServiceTest** (7 Tests) - Preisermittlung
- ✅ **OrderValidierungsServiceTest** (14 Tests) - Validierung von Kauf/Verkauf
- ✅ **BoerseServiceTest** (22 Tests) - Kompletter Kauf- und Verkaufsprozess
- ✅ **HandelsRoboterTest** (17 Tests) - Automatisiertes Handelssystem

### Tests ausführen

```bash
# Alle Tests
mvn clean test

# Nur einen Test
mvn test -Dtest=KontoServiceTest

# Mit Detailoutput
mvn test -X
```

---

## 🎮 Demo-Programm: Boersensimulator

Das Projekt enthält ein interaktives Demo-Programm, das zeigt, wie die gesamte Mini-Börse zusammenspielt:

```bash
# Simulator starten
mvn clean compile exec:java -Dexec.mainClass="de.dwpbank.mikrobank.Boersensimulator"
```

**Was der Simulator macht:**
- 🏛️ Börse mit 5 Aktien starten
- 🤖 HandelsRoboter mit 50.000€ Startkapital
- 📊 Jede Runde: Kurse ändern → Roboter handelt → Status zeigen
- ⌨️ Enter drücken für nächste Runde, oder 'q' zum Beenden

---

## 💡 Architektur & Design-Patterns

### Service-orientierte Architektur
Jeder Service hat **eine Verantwortung**:
- KontoService: Nur Kontoverwaltung
- PreisService: Nur Preisberechnung
- OrderValidierungsService: Nur Validierung
- BoerseService: Orchestrierung (orchestriert alle anderen)

### Verwendete Technologien

- **Java 17** - Moderne Java Features
- **Lombok** - Annotations für sauberen Code (@Slf4j für Logging)
- **JUnit 5** - Professionelle Test-Infrastruktur
- **SLF4J** - Logging Framework
- **Maven** - Build-Management

### Fehlerbehandlung

Das System verwendet **Exceptions für Geschäftslogik**:

```java
// Ungültige Einzahlung
kontoService.einzahlen(konto, -50);  // wirft IllegalArgumentException

// Nicht genug Guthaben
kontoService.auszahlen(konto, 10000); // wirft IllegalArgumentException

// Ungültige Order
validierungsService.validiereKauf(null, 5); // wirft IllegalArgumentException
```

---

## 📚 Für Anfänger

Dieses Projekt ist **perfekt zum Lernen** von:

1. **Object-Oriented Programming** (OOP)
   - Klassen, Objekte, Vererbung
   
2. **Service-oriented Architecture**
   - Separation of Concerns
   - Single Responsibility Principle
   
3. **Unit Testing mit JUnit 5**
   - AAA-Pattern (Arrange, Act, Assert)
   - assertThrows, assertEquals, assertTrue
   
4. **Java Best Practices**
   - Immutable Objects (Konto, Aktie)
   - Exception Handling
   - Logging mit Lombok

5. **Maven Projektstruktur**
   - pom.xml, Dependencies
   - Build Lifecycle

---

## 🚀 Nächste Schritte

1. **Services implementieren** - Füllen Sie die Service-Methoden mit Geschäftslogik
2. **Alle Tests grün machen** - Tests schreiben ist einfach, Code schreiben ist die Kunst!
3. **Erweitert bauen** - Fügen Sie neue Features hinzu:
   - Portfolio-Verwaltung
   - Transaktionsverlauf
   - Depot/Lagerbestände für Aktien
   - REST API mit Spring Boot

---

## 📖 Dokumentation

Die Quellcode ist ausführlich kommentiert. Besonders hilfreich sind:
- Service-Methoden mit Javadoc (`@param`, `@return`, Geschäftsregeln)
- Test-Methoden mit AAA-Pattern und deutschen Kommentaren
- Inline-Kommentare zur Syntax für Anfänger

---

## 👨‍💻 Autoren & Lizenz

Gebaut für Lernzwecke. Frei verwendbar für Bildung und Entwicklung.

**Happy Coding! 🎉**