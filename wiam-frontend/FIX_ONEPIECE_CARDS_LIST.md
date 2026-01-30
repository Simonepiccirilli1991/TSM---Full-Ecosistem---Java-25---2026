# Fix Template One Piece Cards List

## 🐛 Problema Riscontrato

**Errore Thymeleaf:**
```
Exception evaluating SpringEL expression: "card.nomeCarta"
Property or field 'nomeCarta' cannot be found on object of type 
'it.tsm.wiamfrontend.dto.onepiece.OnePieceCardDTO'
```

## 🔍 Causa

Il template `onepiece/cards/list.html` utilizzava nomi di campo non corrispondenti al DTO:
- Template usava: `card.nomeCarta`, `card.rarita`, `card.condizione`, `card.lingua`
- DTO ha: `card.nome`, `card.gradata`, `card.casaGradazione`, `card.votoGradazione`

## ✅ Soluzione Applicata

### File Modificato:
`templates/onepiece/cards/list.html`

### Modifiche:
1. **Cambiato** `card.nomeCarta` → `card.nome`
2. **Rimossi** campi non esistenti: `rarita`, `condizione`, `lingua`
3. **Aggiunti** campi corretti: `gradata`, `casaGradazione`, `votoGradazione`
4. **Aggiornato** colspan da 8 a 8 (numero corretto di colonne)

### Tabella Prima:
| Nome | Espansione | Rarità | Condizione | Lingua | Prezzo | Data | Stato | Azioni |
|------|------------|--------|------------|--------|--------|------|-------|--------|

### Tabella Dopo:
| Nome | Espansione | Gradata | Gradazione | Prezzo | Data | Stato | Azioni |
|------|------------|---------|------------|--------|------|-------|--------|

### Logica Gradazione:
```html
<!-- Badge Gradata -->
<span th:if="${card.gradata}" class="badge bg-info">Sì</span>
<span th:unless="${card.gradata}" class="badge bg-secondary">No</span>

<!-- Info Gradazione -->
<span th:if="${card.gradata}" 
      th:text="${card.casaGradazione + ' ' + card.votoGradazione}"></span>
<span th:unless="${card.gradata}">-</span>
```

## 📊 Campi DTO One Piece Card

**Campi disponibili in `OnePieceCardDTO`:**
- ✅ `nome` (String)
- ✅ `espansione` (String)
- ✅ `gradata` (Boolean)
- ✅ `casaGradazione` (String) - es: "PSA", "BGS", "CGC"
- ✅ `votoGradazione` (String) - es: "10", "9.5"
- ✅ `codiceGradazione` (String)
- ✅ `prezzoAcquisto` (Double)
- ✅ `dataInserimentoAcquisto` (LocalDateTime)
- ✅ `stato` (String) - "DISPONIBILE" o "VENDUTO"
- ✅ `foto` (byte[])
- ✅ `vendita` (VenditaDTO)

**Campi NON disponibili:**
- ❌ `nomeCarta`
- ❌ `rarita`
- ❌ `condizione`
- ❌ `lingua`

## ✅ Test Superato

Dopo la correzione, la pagina `/onepiece/cards` si carica correttamente e mostra:
- Nome della carta
- Espansione
- Stato gradazione (Sì/No)
- Dettagli gradazione (se presente)
- Prezzo acquisto
- Data acquisto
- Stato (Disponibile/Venduto)
- Pulsanti azioni

## 🔄 Altri Template Verificati

- ✅ `onepiece/sealed/list.html` - OK
- ✅ `pokemon/cards/list.html` - OK
- ✅ `pokemon/sealed/list.html` - OK

Tutti gli altri template list usano i campi corretti dei rispettivi DTO.

## 🎯 Risultato

Template corretto e funzionante. La lista delle carte One Piece ora si carica senza errori.
