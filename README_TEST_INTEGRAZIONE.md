# 🎯 WIAM Microservizi - Pronto per il Test

## ✅ Stato del Progetto

### Correzioni Completate
1. ✅ **DTO Frontend allineati con Backend**
   - Tutti i DTO corretti per inviare dati nel formato corretto
   - Rimossi campi non necessari
   - Aggiunti campi mancanti
   - Tipi di dati corretti (Double, LocalDateTime)

2. ✅ **Servizi Frontend corretti**
   - Mappatura corretta dai DTO completi ai DTO Request
   - Ordine corretto dei parametri nelle richieste Vendita
   - Eliminazione di campi non esistenti nel backend

3. ✅ **Documentazione completa**
   - Guida di avvio e test
   - Collezione Postman
   - Script di automazione
   - Documentazione delle correzioni

## 📁 File Creati per il Test

| File | Descrizione |
|------|-------------|
| `start-and-test.sh` | Script automatico per avviare entrambi i MS e testare |
| `test-integration.sh` | Script per testare l'integrazione con cURL |
| `GUIDA_AVVIO_E_TEST.md` | Guida completa con istruzioni dettagliate |
| `WIAM_Postman_Collection.json` | Collezione Postman con tutte le API |
| `wiam-frontend/CORREZIONI_DTO.md` | Dettaglio di tutte le correzioni effettuate |

## 🚀 Come Avviare i Microservizi

### Metodo 1: Script Automatico (Raccomandato)

```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project"
chmod +x start-and-test.sh
./start-and-test.sh
```

### Metodo 2: Manuale

#### Terminale 1 - MongoDB
```bash
brew services start mongodb-community@7.0
```

#### Terminale 2 - WIAM Backend
```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project/wiam"
mvn spring-boot:run
# Oppure
mvn clean package -DskipTests && java -jar target/wiam-0.0.1-SNAPSHOT.jar
```

#### Terminale 3 - WIAM Frontend
```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project/wiam-frontend"
mvn spring-boot:run
# Oppure
mvn clean package -DskipTests && java -jar target/wiam-frontend-0.0.1-SNAPSHOT.jar
```

## 🧪 Come Testare l'Integrazione

### Test Rapido con Script
```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project"
chmod +x test-integration.sh
./test-integration.sh
```

### Test Manuale con Browser
1. Apri il browser su: **http://localhost:8080**
2. Naviga nelle sezioni:
   - Pokemon Cards: http://localhost:8080/pokemon/cards
   - Pokemon Sealed: http://localhost:8080/pokemon/sealed
   - OnePiece Cards: http://localhost:8080/onepiece/cards
   - OnePiece Sealed: http://localhost:8080/onepiece/sealed

### Test con Postman
1. Importa la collezione: `WIAM_Postman_Collection.json`
2. Esegui le richieste in sequenza:
   - Create Pokemon Card
   - Get Cards by Status
   - Add Vendita
   - Verifica cambio stato

### Test con cURL

#### Crea una Pokemon Card
```bash
curl -X POST http://localhost:8081/api/v1/pokemon/addcard \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Charizard",
    "dataInserimentoAcquisto": "2026-01-29T10:00:00",
    "prezzoAcquisto": 150.50,
    "espansione": "Base Set",
    "gradata": true,
    "casaGradazione": "PSA",
    "votoGradazione": "10",
    "codiceGradazione": "12345678"
  }'
```

#### Recupera tutte le Cards disponibili
```bash
curl http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE
```

#### Aggiungi una Vendita
```bash
# Sostituisci YOUR_CARD_ID con l'ID ottenuto dalla creazione
curl -X POST http://localhost:8081/api/v1/pokemon/addvendita \
  -H "Content-Type: application/json" \
  -d '{
    "id": "YOUR_CARD_ID",
    "vendita": {
      "dataVendita": "2026-01-29",
      "prezzoVendita": 200.00,
      "costiVendita": 15.50,
      "prezzoNetto": "184.50",
      "piattaformaVendita": "eBay"
    },
    "tipoProdotto": "CARD"
  }'
```

## 🔍 Checklist di Verifica

Dopo aver avviato i microservizi, verifica:

- [ ] MongoDB è in esecuzione
- [ ] WIAM Backend risponde su porta 8081
- [ ] WIAM Frontend risponde su porta 8080
- [ ] Il frontend carica la lista delle Pokemon Cards
- [ ] È possibile creare una nuova Pokemon Card dal frontend
- [ ] È possibile creare un nuovo Pokemon Sealed
- [ ] È possibile aggiungere una vendita
- [ ] Lo stato del prodotto passa da DISPONIBILE a VENDUTO dopo la vendita
- [ ] I dati della vendita sono salvati correttamente
- [ ] Le stesse funzionalità funzionano per OnePiece
- [ ] Non ci sono errori nei log del backend
- [ ] Non ci sono errori nei log del frontend
- [ ] Non ci sono errori nella console del browser

## 🎯 Punti Chiave dell'Integrazione

### Formato Dati Corretto

✅ **Pokemon Card Request**:
```json
{
  "nome": "Charizard",
  "dataInserimentoAcquisto": "2026-01-29T10:00:00",  // LocalDateTime
  "prezzoAcquisto": 150.50,                          // Double
  "espansione": "Base Set",
  "gradata": true,                                   // Boolean
  "casaGradazione": "PSA",
  "votoGradazione": "10",
  "codiceGradazione": "12345678"
}
```

✅ **Vendita Request**:
```json
{
  "id": "card_id_123",
  "vendita": {
    "dataVendita": "2026-01-29",      // String
    "prezzoVendita": 200.00,          // Double
    "costiVendita": 15.50,            // Double
    "prezzoNetto": "184.50",          // String
    "piattaformaVendita": "eBay"
  },
  "tipoProdotto": "CARD"              // CARD o SEALED
}
```

### Campi Rimossi (non più inviati)
❌ rarita  
❌ condizione  
❌ lingua  
❌ tipologia  
❌ costiSpedizione  
❌ commissioni  

### Campi Aggiunti
✅ gradata  
✅ casaGradazione  
✅ votoGradazione  
✅ codiceGradazione  
✅ codiceProdotto (per Sealed)  
✅ foto (byte[])  

## 📊 URL di Riferimento

| Servizio | URL | Porta |
|----------|-----|-------|
| WIAM Backend | http://localhost:8081 | 8081 |
| WIAM Frontend | http://localhost:8080 | 8080 |
| MongoDB | mongodb://localhost:27017 | 27017 |

## 🔧 Troubleshooting

### Problema: Backend non si avvia
**Soluzione**: Verifica che MongoDB sia in esecuzione
```bash
brew services start mongodb-community@7.0
```

### Problema: Frontend non si connette al backend
**Soluzione**: Verifica che il backend sia avviato su porta 8081
```bash
curl http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE
```

### Problema: Errore 400 Bad Request
**Soluzione**: Verifica il formato dei dati inviati
- Data deve essere in formato ISO-8601: `2026-01-29T10:00:00`
- Prezzi devono essere numeri Double (non stringhe)
- Tutti i campi obbligatori devono essere presenti

### Problema: Frontend mostra pagina bianca
**Soluzione**: 
1. Apri la console del browser (F12)
2. Verifica errori JavaScript
3. Controlla che le chiamate REST al backend vadano a buon fine

## 📝 Log da Monitorare

### Backend Log
```
INFO  [...] Started WiamApplication in X.XXX seconds
DEBUG [...] Creating new Pokemon card: Charizard
DEBUG [...] Card created with id: 67a1b2c3d4e5f6g7h8i9j0k1
```

### Frontend Log
```
INFO  [...] Started WiamFrontendApplication in X.XXX seconds
DEBUG [...] Fetching all Pokemon cards - DISPONIBILE
DEBUG [...] Creating new Pokemon card: Charizard
```

## ✨ Prossimi Passi

Dopo aver verificato che l'integrazione funziona:

1. ✅ Testare tutte le funzionalità CRUD (Create, Read, Update, Delete)
2. ✅ Testare le vendite per Card e Sealed
3. ✅ Verificare che lo stato cambi correttamente
4. ✅ Testare OnePiece Card e Sealed
5. ✅ Testare la reportistica (se implementata)
6. ✅ Verificare la gestione degli errori
7. ✅ Testare con dati edge case (valori null, stringhe vuote, ecc.)

## 🎉 Risultato Atteso

Se tutto funziona correttamente:
- ✅ Il frontend carica e mostra i dati dal backend
- ✅ È possibile creare nuovi prodotti (Card e Sealed)
- ✅ È possibile aggiungere vendite
- ✅ Lo stato dei prodotti si aggiorna correttamente
- ✅ I dati persistono nel database MongoDB
- ✅ Non ci sono errori nei log o nella console del browser

---

**Data ultimo aggiornamento**: 29 Gennaio 2026  
**Versione WIAM Backend**: 0.0.1-SNAPSHOT  
**Versione WIAM Frontend**: 0.0.1-SNAPSHOT  
