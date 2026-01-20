# Documentazione API - TSM Resell WIAM

## Introduzione

Questo documento descrive tutti i servizi REST esposti dall'applicazione **WIAM (Web Inventory Asset Manager)** per la gestione delle collezioni di carte Pokémon e One Piece, compresi gli acquisti e le vendite.

---

## Indice

1. [Servizi Pokémon](#servizi-pokémon)
   - [Card Service](#pokémon-card-service)
   - [Sealed Service](#pokémon-sealed-service)
   - [Vendita Service](#pokémon-vendita-service)

2. [Servizi One Piece](#servizi-one-piece)
   - [Card Service](#onepiece-card-service)
   - [Sealed Service](#onepiece-sealed-service)
   - [Vendita Service](#onepiece-vendita-service)

3. [Modelli di Errore](#modelli-di-errore)

---

## Servizi Pokémon

### Base URL
```
http://localhost:8080/api/v1/pokemon
```

---

### Pokémon Card Service

Gestisce le carte singole Pokémon individuali.

#### 1. Aggiungere una nuova carta Pokémon

**Endpoint:** `POST /addcard`

**Descrizione:** Aggiunge una nuova carta Pokémon al database.

**Request Body:**
```json
{
  "nome": "Charizard",
  "dataInserimentoAcquisto": "2025-01-20T10:30:00",
  "prezzoAcquisto": 250.50,
  "espansione": "Base Set",
  "gradata": true,
  "casaGradazione": "PSA",
  "votoGradazione": "9",
  "codiceGradazione": "12345678",
  "foto": null
}
```

**Parametri:**
- `nome` (String, obbligatorio): Nome della carta
- `dataInserimentoAcquisto` (LocalDateTime, obbligatorio): Data e ora dell'acquisto
- `prezzoAcquisto` (Double, obbligatorio): Prezzo di acquisto in euro
- `espansione` (String, obbligatorio): Nome dell'espansione (es. "Base Set", "Fossil", etc.)
- `gradata` (Boolean, opzionale): Indica se la carta è gradata
- `casaGradazione` (String, condizionato): Agenzia di gradazione (PSA, BGS, etc.) - obbligatorio se gradata=true
- `votoGradazione` (String, condizionato): Voto di gradazione - obbligatorio se gradata=true
- `codiceGradazione` (String, condizionato): Codice univoco di gradazione - obbligatorio se gradata=true
- `foto` (byte[], opzionale): Foto della carta

**Response (201 Created):**
```json
{
  "messaggio": "Salvato pokemon card successfully",
  "carta": {
    "id": "PKM-CARD-550e8400-e29b-41d4-a716-446655440000",
    "nome": "Charizard",
    "dataInserimentoAcquisto": "2025-01-20T10:30:00",
    "dataLastUpdate": null,
    "stato": "ACQUISTATO",
    "prezzoAcquisto": 250.50,
    "espansione": "Base Set",
    "gradata": true,
    "casaGradazione": "PSA",
    "votoGradazione": "9",
    "codiceGradazione": "12345678",
    "foto": null,
    "vendita": null
  }
}
```

---

#### 2. Recuperare una carta per ID

**Endpoint:** `GET /getcard/{id}`

**Descrizione:** Recupera i dettagli di una carta Pokémon specifica.

**Parametri URL:**
- `id` (String, obbligatorio): ID della carta (es. "PKM-CARD-550e8400-e29b-41d4-a716-446655440000")

**Response (200 OK):**
```json
{
  "id": "PKM-CARD-550e8400-e29b-41d4-a716-446655440000",
  "nome": "Charizard",
  "dataInserimentoAcquisto": "2025-01-20T10:30:00",
  "dataLastUpdate": null,
  "stato": "ACQUISTATO",
  "prezzoAcquisto": 250.50,
  "espansione": "Base Set",
  "gradata": true,
  "casaGradazione": "PSA",
  "votoGradazione": "9",
  "codiceGradazione": "12345678",
  "foto": null,
  "vendita": null
}
```

**Errori:**
- `404 Not Found`: Carta non trovata

---

#### 3. Recuperare carte per stato

**Endpoint:** `GET /getcardsbystatus/{status}`

**Descrizione:** Recupera tutte le carte Pokémon in un determinato stato.

**Parametri URL:**
- `status` (String, obbligatorio): Stato della carta ("ACQUISTATO" o "VENDUTO")

**Response (200 OK):**
```json
[
  {
    "id": "PKM-CARD-550e8400-e29b-41d4-a716-446655440000",
    "nome": "Charizard",
    "dataInserimentoAcquisto": "2025-01-20T10:30:00",
    "dataLastUpdate": null,
    "stato": "ACQUISTATO",
    "prezzoAcquisto": 250.50,
    "espansione": "Base Set",
    "gradata": true,
    "casaGradazione": "PSA",
    "votoGradazione": "9",
    "codiceGradazione": "12345678",
    "foto": null,
    "vendita": null
  }
]
```

---

#### 4. Cancellare una carta

**Endpoint:** `DELETE /deletecard/{id}`

**Descrizione:** Elimina una carta Pokémon dal database.

**Parametri URL:**
- `id` (String, obbligatorio): ID della carta

**Response (200 OK):** Nessun contenuto

**Errori:**
- `404 Not Found`: Carta non trovata

---

### Pokémon Sealed Service

Gestisce i prodotti Sealed Pokémon (booster box, blister, etc.).

#### 1. Aggiungere un nuovo prodotto Sealed

**Endpoint:** `POST /addsealed`

**Descrizione:** Aggiunge un nuovo prodotto Sealed Pokémon al database.

**Request Body:**
```json
{
  "nome": "Booster Box Base Set",
  "dataInserimentoAcquisto": "2025-01-20T10:30:00",
  "prezzoAcquisto": 500.00,
  "espansione": "Base Set",
  "codiceProdotto": "PKM-BOX-001",
  "foto": null
}
```

**Parametri:**
- `nome` (String, obbligatorio): Nome del prodotto Sealed
- `dataInserimentoAcquisto` (LocalDateTime, obbligatorio): Data e ora dell'acquisto
- `prezzoAcquisto` (Double, obbligatorio): Prezzo di acquisto in euro
- `espansione` (String, obbligatorio): Nome dell'espansione
- `codiceProdotto` (String, obbligatorio): Codice univoco del prodotto
- `foto` (byte[], opzionale): Foto del prodotto

**Response (201 Created):**
```json
{
  "messaggio": "Acquisto salvato con successo",
  "carta": {
    "id": "PKM-SEALED-550e8400-e29b-41d4-a716-446655440000",
    "nome": "Booster Box Base Set",
    "dataInserimentoAcquisto": "2025-01-20T10:30:00",
    "dataLastUpdate": null,
    "stato": "ACQUISTATO",
    "prezzoAcquisto": 500.00,
    "espansione": "Base Set",
    "codiceProdotto": "PKM-BOX-001",
    "foto": null,
    "vendita": null
  }
}
```

---

#### 2. Recuperare un prodotto Sealed per ID

**Endpoint:** `GET /getsealed/{id}`

**Descrizione:** Recupera i dettagli di un prodotto Sealed Pokémon specifico.

**Parametri URL:**
- `id` (String, obbligatorio): ID del prodotto Sealed

**Response (200 OK):** Oggetto OnePieceSealed

---

#### 3. Recuperare prodotti Sealed per stato

**Endpoint:** `GET /getsealedbystatus/{status}`

**Descrizione:** Recupera tutti i prodotti Sealed Pokémon in un determinato stato.

**Parametri URL:**
- `status` (String, obbligatorio): Stato del prodotto ("ACQUISTATO" o "VENDUTO")

**Response (200 OK):** Array di oggetti Sealed

---

#### 4. Cancellare un prodotto Sealed

**Endpoint:** `DELETE /deletesealed/{id}`

**Descrizione:** Elimina un prodotto Sealed Pokémon dal database.

**Parametri URL:**
- `id` (String, obbligatorio): ID del prodotto Sealed

**Response (200 OK):** Nessun contenuto

---

### Pokémon Vendita Service

Gestisce le vendite di carte e prodotti Sealed Pokémon.

#### 1. Aggiungere una vendita Pokémon

**Endpoint:** `POST /addvendita`

**Descrizione:** Registra la vendita di una carta o di un prodotto Sealed Pokémon, aggiornando lo stato a "VENDUTO".

**Request Body:**
```json
{
  "id": "PKM-CARD-550e8400-e29b-41d4-a716-446655440000",
  "tipoProdotto": "Carta",
  "vendita": {
    "dataVendita": "2025-01-20",
    "prezzoVendita": 300.00,
    "costiVendita": 15.00,
    "prezzoNetto": "285.00",
    "piattaformaVendita": "eBay"
  }
}
```

**Parametri:**
- `id` (String, obbligatorio): ID della carta o del prodotto Sealed
- `tipoProdotto` (String, obbligatorio): Tipo di prodotto ("Carta" o "Sealed")
- `vendita` (Vendita, obbligatorio): Dettagli della vendita
  - `dataVendita` (String): Data della vendita
  - `prezzoVendita` (Double): Prezzo di vendita
  - `costiVendita` (Double): Costi della vendita (spedizione, commissioni, etc.)
  - `prezzoNetto` (String): Prezzo netto dopo costi
  - `piattaformaVendita` (String): Piattaforma di vendita (eBay, TCGPlayer, etc.)

**Response (200 OK):**
```json
{
  "messaggio": "Vendita aggiunta con successo",
  "cartaPokemon": {
    "id": "PKM-CARD-550e8400-e29b-41d4-a716-446655440000",
    "nome": "Charizard",
    "dataInserimentoAcquisto": "2025-01-20T10:30:00",
    "dataLastUpdate": "2025-01-20T15:45:00",
    "stato": "VENDUTO",
    "prezzoAcquisto": 250.50,
    "espansione": "Base Set",
    "gradata": true,
    "casaGradazione": "PSA",
    "votoGradazione": "9",
    "codiceGradazione": "12345678",
    "foto": null,
    "vendita": {
      "dataVendita": "2025-01-20",
      "prezzoVendita": 300.00,
      "costiVendita": 15.00,
      "prezzoNetto": "285.00",
      "piattaformaVendita": "eBay"
    }
  },
  "pokemonSealed": null
}
```

**Errori:**
- `400 Bad Request`: Parametri mancanti o invalidi
- `404 Not Found`: Prodotto non trovato
- `500 Internal Server Error`: Tipo di prodotto non valido

---

## Servizi One Piece

### Base URL
```
http://localhost:8080/api/v1/onepiece
```

### One Piece Card Service

Gestisce le carte singole One Piece individuali.

#### 1. Aggiungere una nuova carta One Piece

**Endpoint:** `POST /addcard`

**Descrizione:** Aggiunge una nuova carta One Piece al database.

**Request Body:**
```json
{
  "nome": "Luffy Gear 5",
  "dataInserimentoAcquisto": "2025-01-20T10:30:00",
  "prezzoAcquisto": 150.00,
  "espansione": "Starter Deck",
  "gradata": false,
  "casaGradazione": null,
  "votoGradazione": null,
  "codiceGradazione": null,
  "foto": null
}
```

**Parametri:** Identici al servizio Pokémon Card

**Response (201 Created):** Oggetto AddOnePieceCardResponse

---

#### 2. Recuperare una carta per ID

**Endpoint:** `GET /getcard/{id}`

**Descrizione:** Recupera i dettagli di una carta One Piece specifica.

**Parametri URL:**
- `id` (String, obbligatorio): ID della carta (es. "OP-CARD-...")

---

#### 3. Recuperare carte per stato

**Endpoint:** `GET /getcardsbystatus/{status}`

**Descrizione:** Recupera tutte le carte One Piece in un determinato stato.

**Parametri URL:**
- `status` (String, obbligatorio): Stato della carta ("ACQUISTATO" o "VENDUTO")

---

#### 4. Cancellare una carta

**Endpoint:** `DELETE /deletecard/{id}`

**Descrizione:** Elimina una carta One Piece dal database.

---

### One Piece Sealed Service

Gestisce i prodotti Sealed One Piece.

#### 1. Aggiungere un nuovo prodotto Sealed

**Endpoint:** `POST /addsealed`

**Descrizione:** Aggiunge un nuovo prodotto Sealed One Piece al database.

**Request Body:**
```json
{
  "nome": "Starter Deck One Piece",
  "dataInserimentoAcquisto": "2025-01-20T10:30:00",
  "prezzoAcquisto": 50.00,
  "espansione": "Starter Deck",
  "codiceProdotto": "OP-START-001",
  "foto": null
}
```

---

#### 2. Recuperare un prodotto Sealed per ID

**Endpoint:** `GET /getsealed/{id}`

**Descrizione:** Recupera i dettagli di un prodotto Sealed One Piece specifico.

---

#### 3. Recuperare prodotti Sealed per stato

**Endpoint:** `GET /getsealedbystatus/{status}`

**Descrizione:** Recupera tutti i prodotti Sealed One Piece in un determinato stato.

---

#### 4. Cancellare un prodotto Sealed

**Endpoint:** `DELETE /deletesealed/{id}`

**Descrizione:** Elimina un prodotto Sealed One Piece dal database.

---

### One Piece Vendita Service

Gestisce le vendite di carte e prodotti Sealed One Piece.

#### 1. Aggiungere una vendita One Piece

**Endpoint:** `POST /addvendita`

**Descrizione:** Registra la vendita di una carta o di un prodotto Sealed One Piece, aggiornando lo stato a "VENDUTO".

**Request Body:**
```json
{
  "id": "OP-CARD-550e8400-e29b-41d4-a716-446655440000",
  "tipoProdotto": "Carta",
  "vendita": {
    "dataVendita": "2025-01-20",
    "prezzoVendita": 180.00,
    "costiVendita": 12.00,
    "prezzoNetto": "168.00",
    "piattaformaVendita": "TCGPlayer"
  }
}
```

---

## Modelli di Errore

### Errore Pokémon

**Codice di Stato:** Dipende dal tipo di errore

**Response Body:**
```json
{
  "messaggio": "Descrizione dell'errore",
  "errore": "Dettaglio tecnico dell'errore"
}
```

**Codici di Errore Pokémon:**
- `PKM-400`: Bad Request - Parametri non validi o mancanti
- `PKM-403`: Conflict - Risorsa in conflitto
- `PKM-404`: Not Found - Risorsa non trovata
- `PKM-500`: Internal Server Error - Errore interno del server

---

### Errore One Piece

**Codice di Stato:** Dipende dal tipo di errore

**Response Body:**
```json
{
  "messaggio": "Descrizione dell'errore",
  "errore": "Dettaglio tecnico dell'errore"
}
```

**Codici di Errore One Piece:**
- `OP-400`: Bad Request - Parametri non validi o mancanti
- `OP-403`: Conflict - Risorsa in conflitto
- `OP-404`: Not Found - Risorsa non trovata
- `OP-500`: Internal Server Error - Errore interno del server

---

## Modelli di Dati

### Carta Pokémon / One Piece

```json
{
  "id": "string (generato automaticamente)",
  "nome": "string",
  "dataInserimentoAcquisto": "LocalDateTime (ISO 8601)",
  "dataLastUpdate": "LocalDateTime (ISO 8601) - null se non aggiornato",
  "stato": "string - ACQUISTATO o VENDUTO",
  "prezzoAcquisto": "number (Double)",
  "espansione": "string",
  "gradata": "boolean",
  "casaGradazione": "string - null se non gradata",
  "votoGradazione": "string - null se non gradata",
  "codiceGradazione": "string - null se non gradata",
  "foto": "byte[] - null se non presente",
  "vendita": "Vendita - null se non venduto"
}
```

### Sealed Pokémon / One Piece

```json
{
  "id": "string (generato automaticamente)",
  "nome": "string",
  "dataInserimentoAcquisto": "LocalDateTime (ISO 8601)",
  "dataLastUpdate": "LocalDateTime (ISO 8601) - null se non aggiornato",
  "stato": "string - ACQUISTATO o VENDUTO",
  "prezzoAcquisto": "number (Double)",
  "espansione": "string",
  "codiceProdotto": "string",
  "foto": "byte[] - null se non presente",
  "vendita": "Vendita - null se non venduto"
}
```

### Vendita

```json
{
  "dataVendita": "string (YYYY-MM-DD)",
  "prezzoVendita": "number (Double)",
  "costiVendita": "number (Double)",
  "prezzoNetto": "string",
  "piattaformaVendita": "string"
}
```

---

## Formati e Convenzioni

### ID generati automaticamente

- **Carte Pokémon:** `PKM-CARD-{UUID}`
- **Sealed Pokémon:** `PKM-SEALED-{UUID}`
- **Carte One Piece:** `OP-CARD-{UUID}`
- **Sealed One Piece:** `OP-SEALED-{UUID}`

### Stati

- `ACQUISTATO`: Prodotto acquistato, non venduto
- `VENDUTO`: Prodotto venduto

### Formati Data/Ora

- **Request:** ISO 8601 con ora (es. "2025-01-20T10:30:00")
- **Response:** ISO 8601 con ora (es. "2025-01-20T10:30:00")
- **Vendita data:** YYYY-MM-DD (es. "2025-01-20")

---

## Esempi di Utilizzo

### Esempio 1: Aggiungere una carta Pokémon e venderla

1. **POST** `/api/v1/pokemon/addcard`
   - Aggiunge una nuova carta Pokémon

2. **GET** `/api/v1/pokemon/getcard/{id}`
   - Verifica i dettagli della carta

3. **POST** `/api/v1/pokemon/addvendita`
   - Registra la vendita della carta

### Esempio 2: Gestire prodotti Sealed One Piece

1. **POST** `/api/v1/onepiece/addsealed`
   - Aggiunge un nuovo prodotto Sealed

2. **GET** `/api/v1/onepiece/getsealedbystatus/ACQUISTATO`
   - Visualizza tutti i Sealed disponibili

3. **POST** `/api/v1/onepiece/addvendita`
   - Registra la vendita del Sealed

---

## Note Importanti

### Validazione

Tutti gli endpoint validano i parametri ricevuti:
- I campi obbligatori non possono essere nulli o vuoti
- I valori numerici devono essere positivi
- Il tipo di prodotto deve essere "Carta" o "Sealed"

### Gestione degli errori

L'applicazione fornisce dettagli chiari su ogni errore:
- Codice HTTP appropriato (400, 404, 500, etc.)
- Corpo della risposta con messaggio e dettagli dell'errore
- Handler globale per la gestione centralizzata delle eccezioni

### Transazioni

- Le operazioni di aggiunta di carte sono transazionali
- Le vendite aggiornano atomicamente stato e dati di vendita
- In caso di errore, il database rimane in uno stato coerente

---

## Conclusione

L'API WIAM fornisce un'interfaccia completa e robusta per la gestione delle collezioni di carte Pokémon e One Piece, con supporto per acquisti, vendite e tracciamento dello stato. Tutti i servizi seguono best practice REST e implementano una gestione completa degli errori.
