# 🚀 AVVIO RAPIDO - Test Integrazione WIAM

## ⚡ AVVIO IN 3 PASSI

### 1️⃣ Prepara gli Script
```bash
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project"
chmod +x prepare.sh
./prepare.sh
```

### 2️⃣ Avvia MongoDB
```bash
brew services start mongodb-community@7.0
```

### 3️⃣ Avvia i Microservizi

#### Opzione A: Avvio Automatico (Raccomandato)
```bash
./start-and-test.sh
```

#### Opzione B: Avvio Manuale

**Terminale 1 - Backend:**
```bash
cd wiam
mvn spring-boot:run
```

**Terminale 2 - Frontend:**
```bash
cd wiam-frontend
mvn spring-boot:run
```

---

## 🧪 TEST IMMEDIATI

### Metodo 1: Script di Test (CLI)
```bash
./test-integration.sh
```

### Metodo 2: Interfaccia Web
1. Apri nel browser: `test-integration.html`
2. Clicca sui pulsanti per testare le API
3. Visualizza i risultati in tempo reale

### Metodo 3: Browser Diretto
Apri questi URL nel browser:

| Pagina | URL |
|--------|-----|
| Homepage Frontend | http://localhost:8080 |
| Pokemon Cards | http://localhost:8080/pokemon/cards |
| Pokemon Sealed | http://localhost:8080/pokemon/sealed |
| OnePiece Cards | http://localhost:8080/onepiece/cards |
| OnePiece Sealed | http://localhost:8080/onepiece/sealed |
| API Backend | http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE |

### Metodo 4: cURL Veloce
```bash
# Test Backend
curl http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE

# Crea una Card
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

---

## ✅ CHECKLIST VELOCE

Prima di iniziare:
- [ ] Java 21 installato
- [ ] Maven installato
- [ ] MongoDB installato
- [ ] Porta 8080 libera (frontend)
- [ ] Porta 8081 libera (backend)
- [ ] Porta 27017 libera (MongoDB)

Dopo l'avvio:
- [ ] Backend risponde su http://localhost:8081
- [ ] Frontend risponde su http://localhost:8080
- [ ] MongoDB in esecuzione
- [ ] Nessun errore nei log

---

## 🎯 TEST FONDAMENTALI

### Test 1: Verifica Servizi
```bash
# Backend
curl http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE

# Frontend
curl http://localhost:8080
```

### Test 2: Crea Pokemon Card
```bash
curl -X POST http://localhost:8081/api/v1/pokemon/addcard \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Pikachu",
    "dataInserimentoAcquisto": "2026-01-29T10:00:00",
    "prezzoAcquisto": 50.00,
    "espansione": "Base Set",
    "gradata": false
  }'
```

### Test 3: Aggiungi Vendita
```bash
# Sostituisci CARD_ID con l'ID ottenuto dal Test 2
curl -X POST http://localhost:8081/api/v1/pokemon/addvendita \
  -H "Content-Type: application/json" \
  -d '{
    "id": "CARD_ID",
    "vendita": {
      "dataVendita": "2026-01-29",
      "prezzoVendita": 80.00,
      "costiVendita": 5.00,
      "prezzoNetto": "75.00",
      "piattaformaVendita": "eBay"
    },
    "tipoProdotto": "CARD"
  }'
```

---

## 🛠️ TROUBLESHOOTING RAPIDO

### Problema: MongoDB non si avvia
```bash
brew services start mongodb-community@7.0
brew services list  # Verifica lo stato
```

### Problema: Porta occupata
```bash
# Trova il processo che usa la porta 8080 o 8081
lsof -i :8080
lsof -i :8081

# Termina il processo
kill -9 <PID>
```

### Problema: Backend non risponde
```bash
# Verifica che sia in esecuzione
ps aux | grep wiam

# Controlla i log
cd wiam
mvn spring-boot:run
# Cerca errori nell'output
```

### Problema: Frontend non carica dati
1. Apri console browser (F12)
2. Vai alla tab "Network"
3. Verifica le chiamate a http://localhost:8081
4. Controlla gli errori CORS o 404

---

## 📊 PORTE UTILIZZATE

| Servizio | Porta | URL |
|----------|-------|-----|
| Frontend | 8080 | http://localhost:8080 |
| Backend | 8081 | http://localhost:8081 |
| MongoDB | 27017 | mongodb://localhost:27017 |

---

## 🎉 SUCCESSO!

Se vedi:
- ✅ Backend risponde su porta 8081
- ✅ Frontend carica su porta 8080
- ✅ Lista delle card viene visualizzata
- ✅ Puoi creare nuove card
- ✅ Puoi aggiungere vendite

**L'integrazione funziona perfettamente!** 🎊

---

## 📚 DOCUMENTAZIONE COMPLETA

Per maggiori dettagli consulta:
- `README_TEST_INTEGRAZIONE.md` - Guida completa
- `GUIDA_AVVIO_E_TEST.md` - Istruzioni dettagliate
- `wiam-frontend/CORREZIONI_DTO.md` - Dettaglio correzioni
- `WIAM_Postman_Collection.json` - Collezione Postman

---

## ⏹️ STOP SERVIZI

### Stop MongoDB
```bash
brew services stop mongodb-community@7.0
```

### Stop Microservizi
Premi `Ctrl+C` nei terminali dove sono in esecuzione

O trova e termina i processi:
```bash
ps aux | grep wiam
kill <PID_BACKEND> <PID_FRONTEND>
```

---

**Data**: 29 Gennaio 2026  
**Versione**: 1.0.0  
**Stato**: ✅ Pronto per il test
