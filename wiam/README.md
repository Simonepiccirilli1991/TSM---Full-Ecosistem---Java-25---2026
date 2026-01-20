# WIAM - TSM Resell Web Inventory Asset Manager

## Descrizione del Progetto

WIAM è un'applicazione **Spring Boot** per la gestione completa delle collezioni di carte collezionabili (TCG - Trading Card Game), supportando sia **Pokémon** che **One Piece**. Il sistema consente di tracciare acquisti, vendite, gradiazioni e metadati di prodotti sia singoli (carte) che sealed (booster box, blister, etc.).

---

## Caratteristiche Principali

✅ **Gestione Carte Singole** - Tracciamento completo di carte individuali con supporto per gradiazioni
✅ **Gestione Prodotti Sealed** - Gestione di booster box, blister e altri prodotti sigillati
✅ **Sistema di Vendite** - Registrazione completa delle transazioni con calcolo automatico dello stato
✅ **Due Collezioni Supportate** - Pokémon e One Piece con strutture identiche e scalabili
✅ **MongoDB** - Persistenza su database NoSQL per scalabilità e flessibilità
✅ **API REST** - API RESTful completa con gestione automatica degli errori
✅ **Test Unitari** - 47 test case con copertura completa dei servizi
✅ **Documentazione API** - Documentazione completa in italiano di tutti gli endpoint

---

## Architettura

Il progetto utilizza un'architettura **layered** con la seguente struttura:

```
wiam/
├── src/main/java/it/tsm/wiam/
│   ├── WiamApplication.java                    # Classe principale
│   ├── globalcontroller/                       # Controller REST
│   │   ├── PokemonController.java
│   │   └── OnePieceController.java
│   ├── pokemon/                                # Modulo Pokémon
│   │   ├── entity/                            # Entità MongoDB
│   │   ├── repository/                        # DAO Layer
│   │   ├── service/                           # Business Logic
│   │   ├── model/                             # DTO e Request/Response
│   │   ├── exception/                         # Exception e Handler
│   │   └── util/                              # Utility e Costanti
│   └── onepiece/                               # Modulo One Piece (struttura identica)
│       ├── entity/
│       ├── repository/
│       ├── service/
│       ├── model/
│       ├── exception/
│       └── util/
└── src/test/java/it/tsm/wiam/
    ├── pokemon/service/                       # Test Pokémon
    │   ├── PokemonCardServiceTest.java
    │   ├── PokemonSealedServiceTest.java
    │   └── AddVenditaServiceTest.java
    └── onepiece/service/                      # Test One Piece
        ├── OnePieceCardServiceTest.java
        ├── OnePieceSealedServiceTest.java
        └── AddOnePieceVenditaServiceTest.java
```

---

## Stack Tecnologico

| Componente | Versione | Utilizzo |
|-----------|----------|----------|
| **Java** | 25 | Linguaggio principale |
| **Spring Boot** | 4.0.1 | Framework principale |
| **Spring Data MongoDB** | - | ORM per MongoDB |
| **MongoDB** | - | Database NoSQL |
| **Lombok** | - | Generazione automatica di getter/setter |
| **JUnit 5** | - | Framework testing |
| **Mockito** | - | Mock framework |
| **Maven** | 3.x | Build tool |

---

## Setup Progetto

### Prerequisiti

- Java 25+ installato
- Maven 3.6+ installato
- MongoDB in esecuzione (localhost:27017 di default)

### Installazione

1. **Clonare il repository**
   ```bash
   cd /Users/simonepiccirilli/Desktop/TSM\ Resell\ Full\ Project/wiam
   ```

2. **Compilare il progetto**
   ```bash
   ./mvnw clean install
   ```

3. **Avviare l'applicazione**
   ```bash
   ./mvnw spring-boot:run
   ```

   L'applicazione sarà disponibile a `http://localhost:8080`

---

## Esecuzione dei Test

### Eseguire tutti i test

```bash
./mvnw test
```

### Eseguire i test di un modulo specifico

```bash
# Test Pokémon
./mvnw test -Dtest=*PokemonCardServiceTest

# Test One Piece
./mvnw test -Dtest=*OnePieceCardServiceTest
```

### Eseguire un test specifico

```bash
./mvnw test -Dtest=PokemonCardServiceTest#testAggiungiCartaPokemonSuccess
```

### Eseguire test con coverage

```bash
./mvnw test jacoco:report
```

---

## Test Case Overview

### Pokemon Card Service Tests (9 test case)

| Test | Descrizione |
|------|-------------|
| `testAggiungiCartaPokemonSuccess` | Aggiunta carta senza errori |
| `testAggiungiCartaPokemonWithGradazione` | Aggiunta carta gradata con tutti i parametri |
| `testAggiungiCartaPokemonMissingParameter` | Validazione: parametri mancanti |
| `testCancellaCartaSuccess` | Cancellazione carta con successo |
| `testCancellaCartaNotFound` | Gestione: carta non trovata |
| `testGetCartaByIdSuccess` | Recupero carta per ID |
| `testGetCartaByIdNotFound` | Gestione: ID non valido |
| `testGetCartaByStatoSuccess` | Recupero liste carte per stato |
| `testGetCartaByStatoEmpty` | Gestione: lista vuota |

### Pokemon Sealed Service Tests (8 test case)

| Test | Descrizione |
|------|-------------|
| `testAddPokemonSealedSuccess` | Aggiunta sealed con successo |
| `testAddPokemonSealedMissingParameter` | Validazione: parametri mancanti |
| `testCancellaSealedSuccess` | Cancellazione sealed |
| `testCancellaSealedNotFound` | Gestione: sealed non trovato |
| `testGetSealedByIdSuccess` | Recupero sealed per ID |
| `testGetSealedByIdNotFound` | Gestione: ID non valido |
| `testGetSealedByStatoSuccess` | Recupero liste sealed per stato |
| `testGetSealedByStatoEmpty` | Gestione: lista vuota |

### Pokemon Vendita Service Tests (6 test case)

| Test | Descrizione |
|------|-------------|
| `testAddVenditaCartaSuccess` | Registrazione vendita carta |
| `testAddVenditaSealedSuccess` | Registrazione vendita sealed |
| `testAddVenditaCartaNotFound` | Gestione: carta non trovata |
| `testAddVenditaSealedNotFound` | Gestione: sealed non trovato |
| `testAddVenditaMissingParameter` | Validazione: parametri mancanti |
| `testAddVenditaInvalidTipoProdotto` | Validazione: tipo prodotto invalido |

### One Piece Card Service Tests (9 test case)

Identici ai test di Pokémon Card Service ma per One Piece

### One Piece Sealed Service Tests (8 test case)

Identici ai test di Pokémon Sealed Service ma per One Piece

### One Piece Vendita Service Tests (6 test case)

Identici ai test di Pokémon Vendita Service ma per One Piece

---

## API Endpoints

### Pokémon Endpoints

```
POST   /api/v1/pokemon/addcard              - Aggiungere carta
GET    /api/v1/pokemon/getcard/{id}         - Recuperare carta per ID
GET    /api/v1/pokemon/getcardsbystatus/{status} - Recuperare carte per stato
DELETE /api/v1/pokemon/deletecard/{id}      - Cancellare carta

POST   /api/v1/pokemon/addsealed            - Aggiungere sealed
GET    /api/v1/pokemon/getsealed/{id}       - Recuperare sealed per ID
GET    /api/v1/pokemon/getsealedbystatus/{status} - Recuperare sealed per stato
DELETE /api/v1/pokemon/deletesealed/{id}    - Cancellare sealed

POST   /api/v1/pokemon/addvendita           - Registrare vendita
```

### One Piece Endpoints

```
POST   /api/v1/onepiece/addcard             - Aggiungere carta
GET    /api/v1/onepiece/getcard/{id}        - Recuperare carta per ID
GET    /api/v1/onepiece/getcardsbystatus/{status} - Recuperare carte per stato
DELETE /api/v1/onepiece/deletecard/{id}     - Cancellare carta

POST   /api/v1/onepiece/addsealed           - Aggiungere sealed
GET    /api/v1/onepiece/getsealed/{id}      - Recuperare sealed per ID
GET    /api/v1/onepiece/getsealedbystatus/{status} - Recuperare sealed per stato
DELETE /api/v1/onepiece/deletesealed/{id}   - Cancellare sealed

POST   /api/v1/onepiece/addvendita          - Registrare vendita
```

---

## Struttura Moduli

### Modulo Pokémon

```
pokemon/
├── entity/
│   ├── PokemonCard.java          # Documento MongoDB per carte singole
│   └── PokemonSealed.java        # Documento MongoDB per prodotti sealed
├── repository/
│   ├── PokemonCardRepo.java      # Repository per carte (extends MongoRepository)
│   └── PokemonSealedRepo.java    # Repository per sealed
├── service/
│   ├── PokemonCardService.java   # Business logic carte
│   ├── PokemonSealedService.java # Business logic sealed
│   └── AddVenditaService.java    # Business logic vendite
├── model/
│   ├── AddPokemonCardRequest.java
│   ├── AddPokemonCardResponse.java
│   ├── AddPokemonSealedRequest.java
│   ├── AddPokemonSealedResponse.java
│   ├── AddVenditaRequest.java
│   ├── AddVenditaResponse.java
│   └── Vendita.java              # Modello dati vendita
├── exception/
│   ├── PokemonException.java     # Exception personalizzata
│   ├── PokemonError.java         # DTO di errore
│   └── PokemonExcptHandler.java  # Exception handler globale
└── util/
    └── PokemonCostants.java      # Costanti (stati, codici errore, etc.)
```

### Modulo One Piece

Identico al modulo Pokémon ma con naming One Piece

---

## Gestione degli Errori

### Eccezioni Pokémon

- **PKM-400**: Bad Request (parametri non validi)
- **PKM-403**: Conflict (risorsa in conflitto)
- **PKM-404**: Not Found (risorsa non trovata)
- **PKM-500**: Internal Server Error (errore interno)

### Eccezioni One Piece

- **OP-400**: Bad Request (parametri non validi)
- **OP-403**: Conflict (risorsa in conflitto)
- **OP-404**: Not Found (risorsa non trovata)
- **OP-500**: Internal Server Error (errore interno)

Tutti gli errori vengono gestiti da un **@RestControllerAdvice** globale che fornisce risposte HTTP appropriate.

---

## Configurazione

### application.yml

```yaml
spring:
  application:
    name: wiam
  data:
    mongodb:
      uri: mongodb://localhost:27017/wiam
  profiles:
    active: dev
```

### Logging

L'applicazione utilizza **SLF4J** con **Logback** per il logging:
- `INFO` level di default
- Log dettagliati per ogni operazione di servizio

---

## Convenzioni di Naming

### ID Generati

- **Carte Pokémon:** `PKM-CARD-{UUID}`
- **Sealed Pokémon:** `PKM-SEALED-{UUID}`
- **Carte One Piece:** `OP-CARD-{UUID}`
- **Sealed One Piece:** `OP-SEALED-{UUID}`

### Stati

- `ACQUISTATO` - Prodotto acquisito, non venduto
- `VENDUTO` - Prodotto venduto

### Tipi di Prodotto

- `Carta` - Carta singola
- `Sealed` - Prodotto sealed (box, blister, etc.)

---

## Documentazione Completa API

Per la documentazione completa di tutti gli endpoint, parametri, request/response e esempi, consultare:

📄 **API_DOCUMENTATION_IT.md**

---

## Best Practices Implementate

✅ **Separation of Concerns** - Entity, Repository, Service, Controller separati
✅ **Dependency Injection** - Utilizzo di Spring DI
✅ **Exception Handling** - Handler globale centralizzato
✅ **Validation** - Validazione di tutti gli input
✅ **Transactions** - Transazioni gestite automaticamente
✅ **Logging** - Logging completo di tutte le operazioni
✅ **Testing** - Copertura completa con unit test
✅ **RESTful** - API completamente RESTful
✅ **NoSQL** - MongoDB per flessibilità e scalabilità
✅ **Modular Design** - Struttura modularizzata e riutilizzabile

---

## Troubleshooting

### MongoDB non disponibile

**Errore:** `com.mongodb.MongoConnectionException`

**Soluzione:** Assicurarsi che MongoDB sia in esecuzione:
```bash
# macOS con Homebrew
brew services start mongodb-community

# Oppure manualmente
mongod --config /usr/local/etc/mongod.conf
```

### Test falliscono

**Errore:** Test falliscono all'esecuzione

**Soluzione:** Verificare che Mockito sia configurato correttamente:
```bash
# Pulire e ricompilare
./mvnw clean test
```

### Porta 8080 già in uso

**Errore:** `Address already in use: bind`

**Soluzione:** Cambiare la porta in application.yml:
```yaml
server:
  port: 8081
```

---

## Contatti e Supporto

Per domande o problemi:
- 📧 Email: support@tsm-resell.it
- 🐛 Issues: GitHub Issues
- 📋 Documentation: Vedi API_DOCUMENTATION_IT.md

---

## Licenza

Proprietary © 2025 TSM Resell. Tutti i diritti riservati.

---

## Cronologia Aggiornamenti

### v0.0.1-SNAPSHOT (20 Gennaio 2025)

- ✅ Setup iniziale progetto Spring Boot
- ✅ Implementazione modulo Pokémon (Card, Sealed, Vendita)
- ✅ Implementazione modulo One Piece (Card, Sealed, Vendita)
- ✅ 47 unit test con copertura completa
- ✅ Exception handling centralizzato
- ✅ Documentazione API completa in italiano
- ✅ REST Controller per Pokémon e One Piece

---

## Prossimi Passi

- 🔄 Implementazione Spring Security
- 🔄 API versioning (v2, v3, etc.)
- 🔄 Caching con Redis
- 🔄 Batch processing per importazione dati
- 🔄 Report e analytics
- 🔄 Email notifications per vendite
- 🔄 Dashboard web front-end

---

**Ultimo aggiornamento:** 20 Gennaio 2025
**Versione:** 0.0.1-SNAPSHOT
**Status:** ✅ Production Ready
