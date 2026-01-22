# Copilot Instructions for mikrobank

## Project Overview
**mikrobank** is a mini stock exchange (Börse) system written in Java. It manages stock trading operations with accounts and price calculations. The project follows a domain-driven design with service-oriented architecture.

## Architecture & Core Components

### Domain Model Layer
- **Aktie** (Stock): Immutable value object holding `name` and `preis` (price). Use getters/setters for price updates during market operations.
- **Konto** (Account): Simple value object managing `kontostand` (account balance). No business logic—purely data holder.

### Service Layer (Business Logic)
The system uses a **facade pattern** with `BoerseService` as the main entry point:

- **BoerseService**: Orchestrates all trading operations (`kaufe()` method for buying stocks). Depends on three collaborating services. Logs via Lombok's `@Slf4j` annotation.
- **KontoService**: Handles fund transfers (`einzahlen()` deposits, `auszahlen()` withdrawals). Validates non-negative amounts (throws `IllegalArgumentException`).
- **PreisService**: Returns current stock prices via `ermittleAktuellenPreis()`.
- **OrderValidierungsService**: Validates buy/sell orders. Separate methods for `validiereKauf()` (buy) and `validiereVerkauf()` (sell) to maintain single responsibility.

### Data Flow for Stock Purchase
```
BoerseService.kaufe(konto, aktie, menge)
  → OrderValidierungsService.validiereKauf()  [validate order]
  → PreisService.ermittleAktuellenPreis()     [get price]
  → KontoService.auszahlen()                  [deduct funds]
  → [update Aktie price/quantity as needed]
```

## Development Patterns

### Dependencies & Stack
- **Java 17** (compiler source/target in Maven)
- **Lombok**: Use `@Slf4j` for logging in service classes (e.g., `log.info("Purchase: ...")`)
- **JUnit 5**: All tests go in `src/test/java/boerse/` matching main package structure
- **SLF4J Simple**: Basic logging implementation

### Testing Conventions
- **Naming**: `MethodName_Condition_Expected` (e.g., `einzahlen_erhoehtKontostand()`)
- **AAA Pattern**: Arrange (create test data) → Act (call method) → Assert (verify result)
- **Service Instantiation**: Create service as field to share across test methods
- **Exception Testing**: Use `assertThrows()` for validation checks (e.g., negative amounts)

### German Naming Convention
The entire codebase uses German identifiers (method/class names, variables):
- Services: `-Service` suffix (e.g., `KontoService`)
- Methods: `einzahlen`, `auszahlen`, `validiereKauf`, `kaufe`
- Classes: `Aktie`, `Konto`

**Keep all new code in German** to maintain consistency.

## Build & Test Commands
```bash
# Compile
mvn clean compile

# Run tests (maven-surefire-plugin v3.2.5)
mvn test

# Build package
mvn package
```

## Key Decision Patterns

1. **Service Methods Are Incomplete**: Comments reference task specifications (`// siehe Spezifikation in Aufgabe`). Implement based on method signatures and test expectations.
2. **Direct Service Instantiation**: Services are created directly in constructors (not injected). Keep this pattern for simplicity.
3. **Validation Exceptions**: Use `IllegalArgumentException` for invalid inputs (established in KontoServiceTest).
4. **Separation of Concerns**: Each service handles one responsibility—never mix Konto and Aktie logic, split validation from execution.

## Next Development Areas
- Complete `KontoService` methods (einzahlen/auszahlen) with validation
- Implement `PreisService.ermittleAktuellenPreis()` price calculation logic
- Implement `OrderValidierungsService` buy/sell validation rules
- Implement `BoerseService.kaufe()` orchestration and Aktie quantity tracking
- Expand test coverage for all services
