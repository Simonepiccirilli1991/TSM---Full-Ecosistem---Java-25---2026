# 🔄 Aggiornamento Logica Reportistica - Campo statoAcquisto

## Data: 30 Gennaio 2026
## Versione: 1.1

---

## ⚠️ CAMBIO IMPORTANTE

### Campo Utilizzato per i Calcoli

**PRIMA (v1.0):**
- Si utilizzava il campo `stato` per determinare se un prodotto era venduto o acquistato

**ADESSO (v1.1):**
- Si utilizza il campo `statoAcquisto` dal ReportDto del backend WIAM
- Il campo `stato` (disponibile/non disponibile) viene ignorato nei calcoli

---

## 📝 Motivazione del Cambio

Nel backend WIAM, il `ReportDto` contiene DUE campi distinti:

1. **`stato`**: Indica lo stato del prodotto (disponibile / non disponibile)
2. **`statoAcquisto`**: Indica lo stato dell'acquisto (venduto / acquistato)

Per la reportistica è necessario usare **`statoAcquisto`** in quanto:
- ✅ Identifica chiaramente se un prodotto è stato venduto o è ancora in vendita
- ✅ È il campo semanticamente corretto per i calcoli finanziari
- ✅ Evita confusione con lo stato di disponibilità del prodotto

---

## 🔧 Modifiche Tecniche Applicate

### 1. ReportItemDTO.java

**Aggiunto campo:**
```java
private String statoAcquisto; // venduto / acquistato
```

**Aggiornato commento:**
```java
private String stato; // disponibile / non disponibile (stato del prodotto)
private String statoAcquisto; // venduto / acquistato (stato dell'acquisto)
```

### 2. ReportisticaService.java

**Modificato metodo calcolaStatistiche():**

**Prima:**
```java
if ("venduto".equalsIgnoreCase(item.getStato())) {
    // logica per venduto
} else if ("acquistato".equalsIgnoreCase(item.getStato())) {
    // logica per acquistato
}
```

**Dopo:**
```java
if ("venduto".equalsIgnoreCase(item.getStatoAcquisto())) {
    // logica per venduto
} else if ("acquistato".equalsIgnoreCase(item.getStatoAcquisto())) {
    // logica per acquistato
}
```

### 3. Documentazione

Aggiornati i seguenti file:
- ✅ REPORTISTICA_FILTERING_IMPLEMENTATION.md
- ✅ RIEPILOGO_IMPLEMENTAZIONE.md
- ✅ Aggiunto questo file (AGGIORNAMENTO_STATO_ACQUISTO.md)

---

## 🎯 Valori Gestiti

### Campo: statoAcquisto

| Valore | Significato | Azione nel Calcolo |
|--------|-------------|-------------------|
| `"venduto"` | Prodotto venduto con successo | • Conta come vendita<br>• Aggiungi incasso<br>• Aggiungi costi vendita<br>• Calcola profitto |
| `"acquistato"` | Prodotto acquistato ma non ancora venduto | • Conta come "in vendita"<br>• Include nel totale speso<br>• Non calcola profitto |

### Campo: stato (NON UTILIZZATO)

| Valore | Significato | Uso in Reportistica |
|--------|-------------|-------------------|
| `"disponibile"` | Prodotto disponibile | ❌ Ignorato |
| `"non disponibile"` | Prodotto non disponibile | ❌ Ignorato |

---

## 🔍 Verifica Implementazione

### Controllo Rapido

1. **ReportItemDTO.java**: ✅ Campo `statoAcquisto` presente
2. **ReportisticaService.java**: ✅ Usa `getStatoAcquisto()`
3. **Documentazione**: ✅ Aggiornata

### Test Manuale

Per verificare che il cambio funzioni:

1. Avvia backend WIAM e frontend
2. Vai su http://localhost:8080/reportistica
3. Verifica che:
   - I conteggi di vendite e "in vendita" siano corretti
   - I profitti vengano calcolati solo per prodotti venduti
   - I prodotti acquistati ma non venduti appaiano come "in vendita"

---

## ⚡ Impatto sugli Utenti

### ✅ Nessun Impatto Visivo

La dashboard rimane identica a livello di UI. Il cambio è puramente tecnico e migliora:
- ✅ Precisione dei calcoli
- ✅ Correttezza semantica
- ✅ Allineamento con il backend

### ✅ Migliore Accuratezza

Con questo cambio, i calcoli sono più accurati perché:
- Si usa il campo corretto dal backend
- Si evitano ambiguità tra "stato prodotto" e "stato acquisto"
- I dati riflettono meglio la realtà del business

---

## 📋 Checklist Migrazione

Se stai aggiornando da v1.0 a v1.1:

- [x] Aggiornato ReportItemDTO con campo statoAcquisto
- [x] Modificato ReportisticaService per usare statoAcquisto
- [x] Aggiornata documentazione
- [x] Testato con dati reali dal backend
- [ ] Deploy in ambiente di test
- [ ] Validazione con dati di produzione

---

## 🐛 Troubleshooting

### Problema: "Field statoAcquisto not found"

**Causa:** Il backend WIAM non sta inviando il campo statoAcquisto

**Soluzione:**
1. Verifica che il backend WIAM sia aggiornato
2. Controlla il ReportDto nel backend contenga il campo statoAcquisto
3. Verifica la response HTTP con un tool come Postman

### Problema: I calcoli sembrano errati

**Causa:** Possibile discrepanza tra stato e statoAcquisto nel backend

**Soluzione:**
1. Controlla i dati nel database
2. Verifica che statoAcquisto sia popolato correttamente
3. Analizza i log del service per vedere i valori ricevuti

---

## 📊 Esempio Pratico

### Dati dal Backend

```json
{
  "report": [
    {
      "id": "1",
      "nome": "Charizard",
      "stato": "disponibile",
      "statoAcquisto": "venduto",
      "prezzoAcquisto": 50.0,
      "vendita": {
        "prezzoVendita": 100.0,
        "costiVendita": 10.0
      }
    },
    {
      "id": "2",
      "nome": "Pikachu",
      "stato": "disponibile",
      "statoAcquisto": "acquistato",
      "prezzoAcquisto": 20.0
    }
  ]
}
```

### Calcoli Effettuati

**Charizard (statoAcquisto = "venduto"):**
- Speso: 50€
- Incassato: 100€
- Costi: 10€
- Profitto: 100 - 10 - 50 = **40€**

**Pikachu (statoAcquisto = "acquistato"):**
- Speso: 20€
- Stato: **In vendita** (non ancora venduto)
- Profitto: 0€ (non ancora calcolabile)

**Totali:**
- Totale Speso: 70€
- Totale Incassato: 100€
- Totale Costi: 10€
- Profitto Netto: 40€
- In Vendita: 1 prodotto

---

## 🎓 Conclusione

Questo aggiornamento migliora la correttezza e la chiarezza del codice, utilizzando il campo semanticamente corretto (`statoAcquisto`) per i calcoli della reportistica.

**Versione:** 1.1  
**Status:** ✅ Implementato e Testato  
**Compatibilità:** Backend WIAM con ReportDto aggiornato

---

*Ultimo aggiornamento: 30 Gennaio 2026*
