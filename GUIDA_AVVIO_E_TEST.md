# Guida per Avviare e Testare i Microservizi

## Pre-requisiti
1. MongoDB installato e funzionante
2. Java 21
3. Maven
4. I progetti wiam e wiam-frontend compilati

## Avvio Rapido

### Opzione 1: Script Automatico

```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project"
chmod +x start-and-test.sh
./start-and-test.sh
```

Questo script:
- Avvia MongoDB se non è già in esecuzione
- Compila e avvia wiam backend (porta 8081)
- Compila e avvia wiam-frontend (porta 8080)
- Esegue test di integrazione automatici

### Opzione 2: Avvio Manuale

#### 1. Avvio MongoDB
```bash
brew services start mongodb-community@7.0
```

#### 2. Avvio wiam backend (porta 8081)
```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project/wiam"
mvn clean package -DskipTests
java -jar target/wiam-0.0.1-SNAPSHOT.jar
```

#### 3. Avvio wiam-frontend (porta 8080)
Aprire un nuovo terminale:
```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project/wiam-frontend"
mvn clean package -DskipTests
java -jar target/wiam-frontend-0.0.1-SNAPSHOT.jar
```

## Test dell'Integrazione

### Test Automatico
```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project"
chmod +x test-integration.sh
./test-integration.sh
```

### Test Manuali con cURL

#### 1. Test Creazione Pokemon Card
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

#### 2. Test Recupero Cards per Stato
```bash
curl http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE
```

#### 3. Test Creazione Pokemon Sealed
```bash
curl -X POST http://localhost:8081/api/v1/pokemon/addsealed \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Booster Box Scarlet & Violet",
    "dataInserimentoAcquisto": "2026-01-29T11:00:00",
    "prezzoAcquisto": 450.00,
    "espansione": "Scarlet & Violet",
    "codiceProdotto": "SV01-BB"
  }'
```

#### 4. Test Aggiunta Vendita
```bash
curl -X POST http://localhost:8081/api/v1/pokemon/addvendita \
  -H "Content-Type: application/json" \
  -d '{
    "id": "CARD_ID_QUI",
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

#### 5. Test OnePiece Card
```bash
curl -X POST http://localhost:8081/api/v1/onepiece/addcard \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Monkey D. Luffy",
    "dataInserimentoAcquisto": "2026-01-29T12:00:00",
    "prezzoAcquisto": 80.00,
    "espansione": "OP01",
    "gradata": false
  }'
```

### Test Frontend nel Browser

Aprire il browser e visitare:

1. **Homepage**: http://localhost:8080
2. **Pokemon Cards**: http://localhost:8080/pokemon/cards
3. **Pokemon Sealed**: http://localhost:8080/pokemon/sealed
4. **OnePiece Cards**: http://localhost:8080/onepiece/cards
5. **OnePiece Sealed**: http://localhost:8080/onepiece/sealed
6. **Reportistica**: http://localhost:8080/reportistica

## Verifica dell'Integrazione

### Checklist Integrazione Frontend → Backend

- [ ] Il frontend si carica correttamente su porta 8080
- [ ] Il backend risponde su porta 8081
- [ ] La lista delle Pokemon Cards viene visualizzata nel frontend
- [ ] È possibile creare una nuova Pokemon Card dal frontend
- [ ] È possibile creare un nuovo Pokemon Sealed dal frontend
- [ ] È possibile aggiungere una vendita ad un prodotto
- [ ] I dati di vendita vengono salvati correttamente
- [ ] Lo stato del prodotto passa da DISPONIBILE a VENDUTO
- [ ] Le stesse funzionalità funzionano per OnePiece
- [ ] La reportistica mostra i dati corretti

### Punti Critici da Verificare

1. **Formato Data**: Verificare che `LocalDateTime` sia serializzato correttamente in formato ISO-8601
2. **Tipo Prezzi**: Verificare che i prezzi siano inviati come `Double` e non `BigDecimal`
3. **Campi Vendita**: Verificare che l'oggetto `vendita` contenga: `dataVendita`, `prezzoVendita`, `costiVendita`, `prezzoNetto`, `piattaformaVendita`
4. **Ordine Parametri**: Verificare che la VenditaRequest abbia l'ordine: `id`, `vendita`, `tipoProdotto`
5. **Campi Card**: Verificare che non vengano inviati campi non esistenti come `rarita`, `condizione`, `lingua`

## Troubleshooting

### Errore: "Connection refused" dal frontend
- Verificare che il backend sia avviato su porta 8081
- Controllare i log del backend per eventuali errori

### Errore: "400 Bad Request" durante la creazione
- Verificare che il formato JSON sia corretto
- Controllare che tutti i campi obbligatori siano presenti
- Verificare il formato della data (ISO-8601: "2026-01-29T10:00:00")

### Errore: "Cannot connect to MongoDB"
- Avviare MongoDB: `brew services start mongodb-community@7.0`
- Verificare che MongoDB sia in ascolto su porta 27017

### Il frontend non mostra i dati
- Aprire la console del browser (F12) per vedere eventuali errori JavaScript
- Controllare i log del frontend per errori di integrazione
- Verificare che la chiamata REST al backend vada a buon fine

## Log e Debug

### Visualizzare log del backend
I log sono configurati con livello DEBUG per vedere le richieste:
```
2026-01-29 10:00:00 DEBUG [...] Creating new Pokemon card: Charizard
```

### Visualizzare log del frontend
Il frontend ha logging DEBUG per le chiamate WebClient:
```
2026-01-29 10:00:01 DEBUG [...] Fetching all Pokemon cards - DISPONIBILE
```

## Stop dei Servizi

### Stop MongoDB
```bash
brew services stop mongodb-community@7.0
```

### Stop Microservizi
Se avviati in terminali separati, usare `Ctrl+C` in ciascun terminale.

Se avviati in background:
```bash
# Trova i PID
ps aux | grep wiam

# Kill dei processi
kill <PID_BACKEND>
kill <PID_FRONTEND>
```

## URL di Riferimento

| Servizio | URL | Descrizione |
|----------|-----|-------------|
| Backend API | http://localhost:8081 | API REST wiam |
| Frontend | http://localhost:8080 | Interfaccia web |
| MongoDB | mongodb://localhost:27017/tsm-resell | Database |

## Endpoint Backend Principali

### Pokemon
- `POST /api/v1/pokemon/addcard` - Crea card
- `GET /api/v1/pokemon/getcard/{id}` - Recupera card per ID
- `GET /api/v1/pokemon/getcardsbystatus/{status}` - Lista card per stato
- `DELETE /api/v1/pokemon/deletecard/{id}` - Elimina card
- `POST /api/v1/pokemon/addsealed` - Crea sealed
- `GET /api/v1/pokemon/getsealed/{id}` - Recupera sealed per ID
- `GET /api/v1/pokemon/getsealedbystatus/{status}` - Lista sealed per stato
- `DELETE /api/v1/pokemon/deletesealed/{id}` - Elimina sealed
- `POST /api/v1/pokemon/addvendita` - Aggiungi vendita

### OnePiece
- `POST /api/v1/onepiece/addcard` - Crea card
- `GET /api/v1/onepiece/getcard/{id}` - Recupera card per ID
- `GET /api/v1/onepiece/getcardsbystatus/{status}` - Lista card per stato
- `DELETE /api/v1/onepiece/deletecard/{id}` - Elimina card
- `POST /api/v1/onepiece/addsealed` - Crea sealed
- `GET /api/v1/onepiece/getsealed/{id}` - Recupera sealed per ID
- `GET /api/v1/onepiece/getsealedbystatus/{status}` - Lista sealed per stato
- `DELETE /api/v1/onepiece/deletesealed/{id}` - Elimina sealed
- `POST /api/v1/onepiece/addvendita` - Aggiungi vendita
