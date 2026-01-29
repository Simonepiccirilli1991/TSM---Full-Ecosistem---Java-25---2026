# 🔧 Fix Template Thymeleaf - Errore nomeSealed

## 🐛 Problema Risolto

**Errore originale**:
```
org.springframework.beans.NotReadablePropertyException: 
Invalid property 'nomeSealed' of bean class [it.tsm.wiamfrontend.dto.pokemon.PokemonSealedDTO]: 
Bean property 'nomeSealed' is not readable or has an invalid getter method
```

**Causa**: I template HTML Thymeleaf stavano usando i vecchi nomi dei campi (`nomeSealed`, `nomeCarta`, `rarita`, `condizione`, `lingua`, `tipologia`) che non esistono più nei DTO dopo l'allineamento con il backend.

---

## ✅ Correzioni Applicate

### 1. Template Pokemon Card Form (`pokemon/cards/form.html`)

**Prima**:
```html
<input type="text" th:field="*{nomeCarta}" required>
<input type="text" th:field="*{rarita}">
<select th:field="*{condizione}">...</select>
<select th:field="*{lingua}">...</select>
<input type="date" th:field="*{dataInserimentoAcquisto}">
```

**Dopo**:
```html
<input type="text" th:field="*{nome}" required>
<select th:field="*{gradata}">...</select>
<input type="text" th:field="*{casaGradazione}">
<input type="text" th:field="*{votoGradazione}">
<input type="text" th:field="*{codiceGradazione}">
<input type="datetime-local" th:field="*{dataInserimentoAcquisto}" required>
```

### 2. Template Pokemon Card List (`pokemon/cards/list.html`)

**Prima**:
- Colonne: Nome, Espansione, Rarità, Condizione, Lingua
- Campo: `${card.nomeCarta}`

**Dopo**:
- Colonne: Nome, Espansione, Gradata, Casa/Voto
- Campo: `${card.nome}`
- Badge per indicare se gradata o raw
- Visualizzazione casa e voto gradazione

### 3. Template Pokemon Sealed Form (`pokemon/sealed/form.html`)

**Prima**:
```html
<input type="text" th:field="*{nomeSealed}" required>
<select th:field="*{tipologia}">...</select>
<select th:field="*{lingua}">...</select>
<input type="date" th:field="*{dataInserimentoAcquisto}">
```

**Dopo**:
```html
<input type="text" th:field="*{nome}" required>
<input type="text" th:field="*{espansione}" required>
<input type="text" th:field="*{codiceProdotto}" required>
<input type="datetime-local" th:field="*{dataInserimentoAcquisto}" required>
```

### 4. Template Pokemon Sealed List (`pokemon/sealed/list.html`)

**Prima**:
- Colonne: Nome, Tipologia, Lingua
- Campo: `${sealed.nomeSealed}`

**Dopo**:
- Colonne: Nome, Espansione, Codice Prodotto
- Campo: `${sealed.nome}`

### 5. Template OnePiece Card Form (`onepiece/cards/form.html`)

**Correzioni identiche a Pokemon Card Form**

### 6. Template OnePiece Card List (`onepiece/cards/list.html`)

**Correzioni identiche a Pokemon Card List**

### 7. Template OnePiece Sealed Form (`onepiece/sealed/form.html`)

**Correzioni identiche a Pokemon Sealed Form**

### 8. Template OnePiece Sealed List (`onepiece/sealed/list.html`)

**Correzioni identiche a Pokemon Sealed List**

---

## 📋 Mapping Completo Campi

### Card (Pokemon e OnePiece)

| Vecchio Nome | Nuovo Nome | Tipo |
|--------------|-----------|------|
| `nomeCarta` | `nome` | String |
| `rarita` | ❌ RIMOSSO | - |
| `condizione` | ❌ RIMOSSO | - |
| `lingua` | ❌ RIMOSSO | - |
| - | `gradata` | Boolean |
| - | `casaGradazione` | String |
| - | `votoGradazione` | String |
| - | `codiceGradazione` | String |
| `dataInserimentoAcquisto` (date) | `dataInserimentoAcquisto` (datetime-local) | LocalDateTime |

### Sealed (Pokemon e OnePiece)

| Vecchio Nome | Nuovo Nome | Tipo |
|--------------|-----------|------|
| `nomeSealed` | `nome` | String |
| `tipologia` | ❌ RIMOSSO | - |
| `lingua` | ❌ RIMOSSO | - |
| - | `espansione` | String |
| - | `codiceProdotto` | String |
| `dataInserimentoAcquisto` (date) | `dataInserimentoAcquisto` (datetime-local) | LocalDateTime |

---

## 🎯 Modifiche Chiave

### 1. Campi Input Data
**Prima**: `<input type="date">`  
**Dopo**: `<input type="datetime-local">`

**Motivo**: Il backend si aspetta `LocalDateTime` in formato ISO-8601 (es: `2026-01-29T10:00:00`)

### 2. Campi Obbligatori
Aggiunti `required` ai campi:
- `nome`
- `espansione`
- `codiceProdotto` (per Sealed)
- `prezzoAcquisto`
- `dataInserimentoAcquisto`

### 3. Badge nelle Liste
Aggiunto badge per visualizzare:
- **Card**: se è gradata o raw
- **Stato**: DISPONIBILE (verde) o VENDUTO (rosso)

### 4. Colspan Corretti
Liste Pokemon Cards: `colspan="8"` (era 9)  
Liste OnePiece Cards: `colspan="8"` (era 9)

---

## ✅ Risultato Atteso

Dopo queste correzioni:

1. ✅ Il form Pokemon Sealed si carica senza errori
2. ✅ Il form Pokemon Card si carica correttamente
3. ✅ Le liste mostrano le colonne corrette
4. ✅ I form inviano i dati nel formato corretto al backend
5. ✅ Il binding Thymeleaf funziona con i nomi corretti dei campi
6. ✅ Le stesse correzioni sono applicate a OnePiece

---

## 🧪 Test Rapido

### 1. Riavvia il Frontend
```bash
cd wiam-frontend
mvn spring-boot:run
```

### 2. Prova ad Accedere
- Pokemon Sealed: http://localhost:8080/pokemon/sealed/new
- Pokemon Card: http://localhost:8080/pokemon/cards/new
- OnePiece Sealed: http://localhost:8080/onepiece/sealed/new
- OnePiece Card: http://localhost:8080/onepiece/cards/new

### 3. Verifica
- ✅ I form si caricano senza errori
- ✅ I campi corretti sono visualizzati
- ✅ È possibile compilare e inviare i form
- ✅ Le liste mostrano i dati correttamente

---

## 📚 File Modificati

```
wiam-frontend/src/main/resources/templates/
├── pokemon/
│   ├── cards/
│   │   ├── form.html ✅ MODIFICATO
│   │   └── list.html ✅ MODIFICATO
│   └── sealed/
│       ├── form.html ✅ MODIFICATO
│       └── list.html ✅ MODIFICATO
└── onepiece/
    ├── cards/
    │   ├── form.html ✅ MODIFICATO
    │   └── list.html ✅ MODIFICATO
    └── sealed/
        ├── form.html ✅ MODIFICATO
        └── list.html ✅ MODIFICATO
```

**Totale**: 8 file HTML corretti

---

## 🎉 Stato Finale

✅ **ERRORE RISOLTO**: I template ora usano i nomi corretti dei campi  
✅ **ALLINEAMENTO COMPLETO**: Frontend ↔ Backend perfettamente sincronizzati  
✅ **PRONTO PER IL TEST**: Tutti i form dovrebbero funzionare correttamente  

---

**Data correzione**: 29 Gennaio 2026  
**Errore**: `NotReadablePropertyException: Invalid property 'nomeSealed'`  
**Stato**: ✅ RISOLTO
