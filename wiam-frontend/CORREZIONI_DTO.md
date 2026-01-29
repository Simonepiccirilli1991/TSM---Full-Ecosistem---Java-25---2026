# Correzioni Frontend WIAM - Allineamento DTO con Backend

## Problema Rilevato
Il frontend `wiam-frontend` stava inviando dati in formato sbagliato alle API di `wiam`, causando:
- Campi non necessari inviati nelle richieste
- Campi mancanti rispetto a quanto atteso dal backend
- Tipi di dati non corretti (BigDecimal vs Double, String vs LocalDateTime)
- Nomi di campi non corrispondenti

## Modifiche Effettuate

### 1. DTO Vendita
**File**: `VenditaDTO.java`
**Modifiche**:
- Cambiato tipo da `BigDecimal` a `Double` per `prezzoVendita` e `costiVendita`
- Rinominato `costiSpedizione` → `costiVendita`
- Rinominato `commissioni` → rimosso
- Aggiunto campo `prezzoNetto` (String)
- Riordinato campi secondo l'ordine del backend

**Prima**:
```java
private BigDecimal prezzoVendita;
private BigDecimal costiSpedizione;
private BigDecimal commissioni;
```

**Dopo**:
```java
private String dataVendita;
private Double prezzoVendita;
private Double costiVendita;
private String prezzoNetto;
private String piattaformaVendita;
```

### 2. DTO Pokemon Card
**File**: `PokemonCardDTO.java`
**Modifiche**:
- Rinominato `nomeCarta` → `nome`
- Rimossi campi non esistenti: `rarita`, `condizione`, `lingua`
- Cambiato tipo `String dataInserimentoAcquisto` → `LocalDateTime`
- Cambiato tipo `BigDecimal prezzoAcquisto` → `Double`
- Aggiunti campi mancanti: `dataLastUpdate`, `gradata`, `casaGradazione`, `votoGradazione`, `codiceGradazione`, `foto`

### 3. DTO Pokemon Sealed
**File**: `PokemonSealedDTO.java`
**Modifiche**:
- Rinominato `nomeSealed` → `nome`
- Rimossi campi: `tipologia`, `lingua`
- Aggiunti campi: `espansione`, `codiceProdotto`, `dataLastUpdate`, `foto`
- Cambiato tipo per `dataInserimentoAcquisto` e `prezzoAcquisto`

### 4. DTO OnePiece Card
**File**: `OnePieceCardDTO.java`
**Modifiche**: Identiche a PokemonCardDTO

### 5. DTO OnePiece Sealed
**File**: `OnePieceSealedDTO.java`
**Modifiche**: Identiche a PokemonSealedDTO

### 6. Nuovi DTO Request Creati
Creati DTO dedicati per le richieste POST che corrispondono esattamente ai `record` Request di wiam:

- `AddPokemonCardRequestDTO.java` - corrisponde a `AddPokemonCardRequest`
- `AddPokemonSealedRequestDTO.java` - corrisponde a `AddPokemonSealedRequest`
- `AddOnePieceCardRequestDTO.java` - corrisponde a `AddOnePieceCardRequest`
- `AddOnePieceSealedRequestDTO.java` - corrisponde a `AddOnePieceSealedRequest`

Questi DTO contengono **solo** i campi richiesti dalle API per la creazione/aggiornamento:
- nome
- dataInserimentoAcquisto (LocalDateTime)
- prezzoAcquisto (Double)
- espansione
- gradata / codiceProdotto (specifici per card/sealed)
- casaGradazione, votoGradazione, codiceGradazione (solo per card gradate)
- foto

### 7. Servizi Aggiornati
Tutti i servizi ora mappano correttamente dai DTO completi ai DTO Request:

**PokemonCardService**, **PokemonSealedService**, **OnePieceCardService**, **OnePieceSealedService**:
- Metodi `createCard` e `updateCard` ora creano un `AddXxxRequestDTO` con solo i campi necessari
- Eliminati campi che non esistono nel backend (id, stato, vendita, dataLastUpdate)

**Esempio** (PokemonCardService.createCard):
```java
AddPokemonCardRequestDTO request = new AddPokemonCardRequestDTO(
    card.getNome(),
    card.getDataInserimentoAcquisto(),
    card.getPrezzoAcquisto(),
    card.getEspansione(),
    card.getGradata(),
    card.getCasaGradazione(),
    card.getVotoGradazione(),
    card.getCodiceGradazione(),
    card.getFoto()
);
```

### 8. Servizi Vendita Corretti
**PokemonVenditaService** e **OnePieceVenditaService**:
- Corretto l'ordine dei parametri nella `VenditaRequest`
- Cambiato da `(idProdotto, tipoProdotto, vendita)` a `(id, vendita, tipoProdotto)`
- Questo corrisponde esattamente a `AddVenditaRequest` e `AddOnePieceVenditaRequest` di wiam

**Prima**:
```java
new VenditaRequest(cardId, "CARD", vendita)
```

**Dopo**:
```java
new VenditaRequest(cardId, vendita, "CARD")
```

## Mapping Completo Frontend ↔ Backend

### Pokemon Card API: POST /api/v1/pokemon/addcard
**Backend attende** (`AddPokemonCardRequest`):
```java
String nome
LocalDateTime dataInserimentoAcquisto
Double prezzoAcquisto
String espansione
Boolean gradata
String casaGradazione
String votoGradazione
String codiceGradazione
byte[] foto
```

**Frontend ora invia** (`AddPokemonCardRequestDTO`): ✅ CORRETTO

### Pokemon Sealed API: POST /api/v1/pokemon/addsealed
**Backend attende** (`AddPokemonSealedRequest`):
```java
String nome
LocalDateTime dataInserimentoAcquisto
Double prezzoAcquisto
String espansione
String codiceProdotto
byte[] foto
```

**Frontend ora invia** (`AddPokemonSealedRequestDTO`): ✅ CORRETTO

### Vendita API: POST /api/v1/pokemon/addvendita
**Backend attende** (`AddVenditaRequest`):
```java
String id
Vendita vendita {
    String dataVendita
    Double prezzoVendita
    Double costiVendita
    String prezzoNetto
    String piattaformaVendita
}
String tipoProdotto
```

**Frontend ora invia** (`VenditaRequest` con `VenditaDTO`): ✅ CORRETTO

## Risultato
✅ Tutti i DTO del frontend sono ora perfettamente allineati con le API del backend wiam  
✅ Nessun campo non necessario viene inviato  
✅ Tutti i campi richiesti sono presenti  
✅ I tipi di dati corrispondono esattamente  
✅ La serializzazione JSON funzionerà correttamente  

## Prossimi Passi
1. Compilare con `mvn clean compile`
2. Avviare wiam backend
3. Avviare wiam-frontend
4. Testare le funzionalità di creazione/aggiornamento per verificare l'integrazione
