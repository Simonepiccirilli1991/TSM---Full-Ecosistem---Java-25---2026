# WIAM Frontend - Mapping Endpoint Backend

Questo documento descrive il mapping tra le funzionalità del frontend e gli endpoint REST del backend WIAM.

## Base URL Backend
`http://localhost:8081`

---

## POKEMON

### Carte Pokemon

| Frontend Service | Backend Endpoint | Method | Descrizione |
|-----------------|------------------|---------|-------------|
| `getAllCards()` | `/api/v1/pokemon/getcardsbystatus/DISPONIBILE` | GET | Lista tutte le carte disponibili |
| `getCardById(id)` | `/api/v1/pokemon/getcard/{id}` | GET | Dettaglio carta per ID |
| `createCard(card)` | `/api/v1/pokemon/addcard` | POST | Crea nuova carta |
| `updateCard(id, card)` | `/api/v1/pokemon/addcard` | POST | Aggiorna carta esistente |
| `deleteCard(id)` | `/api/v1/pokemon/deletecard/{id}` | DELETE | Elimina carta |

### Sealed Pokemon

| Frontend Service | Backend Endpoint | Method | Descrizione |
|-----------------|------------------|---------|-------------|
| `getAllSealed()` | `/api/v1/pokemon/getsealedbystatus/DISPONIBILE` | GET | Lista tutti i sealed disponibili |
| `getSealedById(id)` | `/api/v1/pokemon/getsealed/{id}` | GET | Dettaglio sealed per ID |
| `createSealed(sealed)` | `/api/v1/pokemon/addsealed` | POST | Crea nuovo sealed |
| `updateSealed(id, sealed)` | `/api/v1/pokemon/addsealed` | POST | Aggiorna sealed esistente |
| `deleteSealed(id)` | `/api/v1/pokemon/deletesealed/{id}` | DELETE | Elimina sealed |

### Vendite Pokemon

| Frontend Service | Backend Endpoint | Method | Descrizione |
|-----------------|------------------|---------|-------------|
| `addVenditaCard(cardId, vendita)` | `/api/v1/pokemon/addvendita` | POST | Registra vendita carta |
| `addVenditaSealed(sealedId, vendita)` | `/api/v1/pokemon/addvendita` | POST | Registra vendita sealed |

**Request Body per vendita:**
```json
{
  "idProdotto": "string",
  "tipoProdotto": "CARD|SEALED",
  "vendita": {
    "prezzoVendita": 0.00,
    "dataVendita": "yyyy-MM-dd",
    "piattaformaVendita": "string",
    "costiSpedizione": 0.00,
    "commissioni": 0.00
  }
}
```

---

## ONE PIECE

### Carte One Piece

| Frontend Service | Backend Endpoint | Method | Descrizione |
|-----------------|------------------|---------|-------------|
| `getAllCards()` | `/api/v1/onepiece/getcardsbystatus/DISPONIBILE` | GET | Lista tutte le carte disponibili |
| `getCardById(id)` | `/api/v1/onepiece/getcard/{id}` | GET | Dettaglio carta per ID |
| `createCard(card)` | `/api/v1/onepiece/addcard` | POST | Crea nuova carta |
| `updateCard(id, card)` | `/api/v1/onepiece/addcard` | POST | Aggiorna carta esistente |
| `deleteCard(id)` | `/api/v1/onepiece/deletecard/{id}` | DELETE | Elimina carta |

### Sealed One Piece

| Frontend Service | Backend Endpoint | Method | Descrizione |
|-----------------|------------------|---------|-------------|
| `getAllSealed()` | `/api/v1/onepiece/getsealedbystatus/DISPONIBILE` | GET | Lista tutti i sealed disponibili |
| `getSealedById(id)` | `/api/v1/onepiece/getsealed/{id}` | GET | Dettaglio sealed per ID |
| `createSealed(sealed)` | `/api/v1/onepiece/addsealed` | POST | Crea nuovo sealed |
| `updateSealed(id, sealed)` | `/api/v1/onepiece/addsealed` | POST | Aggiorna sealed esistente |
| `deleteSealed(id)` | `/api/v1/onepiece/deletesealed/{id}` | DELETE | Elimina sealed |

### Vendite One Piece

| Frontend Service | Backend Endpoint | Method | Descrizione |
|-----------------|------------------|---------|-------------|
| `addVenditaCard(cardId, vendita)` | `/api/v1/onepiece/addvendita` | POST | Registra vendita carta |
| `addVenditaSealed(sealedId, vendita)` | `/api/v1/onepiece/addvendita` | POST | Registra vendita sealed |

---

## REPORTISTICA

Tutti gli endpoint reportistica usano **POST** con body request.

| Frontend Service | Backend Endpoint | Method | Request Body |
|-----------------|------------------|---------|--------------|
| `getRecapGenerale()` | `/api/v1/report/creareport` | POST | `{"categoria": "TUTTO", "tipoProdotto": null}` |
| `getReportPokemon()` | `/api/v1/report/creareport` | POST | `{"categoria": "POKEMON", "tipoProdotto": null}` |
| `getReportPokemonCards()` | `/api/v1/report/creareport` | POST | `{"categoria": "POKEMON", "tipoProdotto": "CARD"}` |
| `getReportPokemonSealed()` | `/api/v1/report/creareport` | POST | `{"categoria": "POKEMON", "tipoProdotto": "SEALED"}` |
| `getReportOnePiece()` | `/api/v1/report/creareport` | POST | `{"categoria": "ONEPIECE", "tipoProdotto": null}` |
| `getReportOnePieceCards()` | `/api/v1/report/creareport` | POST | `{"categoria": "ONEPIECE", "tipoProdotto": "CARD"}` |
| `getReportOnePieceSealed()` | `/api/v1/report/creareport` | POST | `{"categoria": "ONEPIECE", "tipoProdotto": "SEALED"}` |
| `getProfittiTotali()` | `/api/v1/report/creareport` | POST | `{"categoria": "TUTTO", "tipoProdotto": null}` |
| `getProfittiPokemon()` | `/api/v1/report/creareport` | POST | `{"categoria": "POKEMON", "tipoProdotto": null}` |
| `getProfittiOnePiece()` | `/api/v1/report/creareport` | POST | `{"categoria": "ONEPIECE", "tipoProdotto": null}` |

### Reportistica Mensile

| Endpoint | Method | Descrizione |
|----------|--------|-------------|
| `/api/v1/report/creareportmensile/acquisti` | POST | Report mensile acquisti |
| `/api/v1/report/creareportmensile/vendite` | POST | Report mensile vendite |

**Request Body per report mensile:**
```json
{
  "stato": "DISPONIBILE|VENDUTO",
  "dataInizio": "yyyy-MM-ddTHH:mm:ss",
  "dataFine": "yyyy-MM-ddTHH:mm:ss"
}
```

---

## Note Implementative

1. **Stati prodotti**: `DISPONIBILE`, `VENDUTO`
2. **Tipi prodotto**: `CARD`, `SEALED`
3. **Categorie**: `POKEMON`, `ONEPIECE`, `TUTTO`
4. **Piattaforme vendita**: `CARDMARKET`, `EBAY`, `VINTED`, `DIRETTA`
5. **Condizioni carte**: `MINT`, `NEAR_MINT`, `EXCELLENT`, `GOOD`, `PLAYED`
6. **Lingue**: `ITA`, `ENG`, `JAP`

## CORS

Il backend deve avere CORS abilitato per permettere le chiamate dal frontend sulla porta 8080.
