# Fix Template Reportistica Dashboard - Accesso Map

## 🐛 Problema

**Errore Thymeleaf:**
```
SpelEvaluationException: Property or field 'totaleAcquisti' cannot be found 
on object of type 'java.util.LinkedHashMap'
```

## 🔍 Causa

Il service `ReportisticaService` nel frontend restituisce `Map<String, Object>` che viene deserializzato come `LinkedHashMap`. Il template `dashboard.html` tentava di accedere ai valori usando la sintassi per oggetti (`.property`) invece della sintassi per Map (`['key']`).

## ✅ Soluzione Applicata

### File Modificato:
`templates/reportistica/dashboard.html`

### Cambiamenti:

**Prima (NON FUNZIONAVA):**
```html
<h3 th:text="${recap.totaleAcquisti} ?: '0'"></h3>
<h3 th:text="${recap.totaleVendite} ?: '0'"></h3>
<h3 th:text="${recap.totaleSpeso != null ? #numbers.formatDecimal(recap.totaleSpeso, 1, 2) + ' €' : '0.00 €'}"></h3>
<h3 th:text="${recap.totaleIncassato != null ? #numbers.formatDecimal(recap.totaleIncassato, 1, 2) + ' €' : '0.00 €'}"></h3>

<h2 th:text="${profitti.profittoNetto != null ? #numbers.formatDecimal(profitti.profittoNetto, 1, 2) + ' €' : '0.00 €'}"></h2>
<h3 th:text="${profitti.profittoPokemon != null ? #numbers.formatDecimal(profitti.profittoPokemon, 1, 2) + ' €' : '0.00 €'}"></h3>
<h3 th:text="${profitti.profittoOnePiece != null ? #numbers.formatDecimal(profitti.profittoOnePiece, 1, 2) + ' €' : '0.00 €'}"></h3>
```

**Dopo (FUNZIONA):**
```html
<h3 th:text="${recap['totaleAcquisti']} ?: '0'"></h3>
<h3 th:text="${recap['totaleVendite']} ?: '0'"></h3>
<h3 th:text="${recap['totaleSpeso'] != null ? #numbers.formatDecimal(recap['totaleSpeso'], 1, 2) + ' €' : '0.00 €'}"></h3>
<h3 th:text="${recap['totaleIncassato'] != null ? #numbers.formatDecimal(recap['totaleIncassato'], 1, 2) + ' €' : '0.00 €'}"></h3>

<h2 th:text="${profitti['profittoNetto'] != null ? #numbers.formatDecimal(profitti['profittoNetto'], 1, 2) + ' €' : '0.00 €'}"></h2>
<h3 th:text="${profitti['profittoPokemon'] != null ? #numbers.formatDecimal(profitti['profittoPokemon'], 1, 2) + ' €' : '0.00 €'}"></h3>
<h3 th:text="${profitti['profittoOnePiece'] != null ? #numbers.formatDecimal(profitti['profittoOnePiece'], 1, 2) + ' €' : '0.00 €'}"></h3>
```

## 📊 Campi Corretti

### Sezione Recap:
- `recap['totaleAcquisti']` - Numero totale acquisti
- `recap['totaleVendite']` - Numero totale vendite  
- `recap['totaleSpeso']` - Importo totale speso (€)
- `recap['totaleIncassato']` - Importo totale incassato (€)

### Sezione Profitti:
- `profitti['profittoNetto']` - Profitto netto totale (€)
- `profitti['profittoPokemon']` - Profitto da Pokémon (€)
- `profitti['profittoOnePiece']` - Profitto da One Piece (€)

## 🎯 Sintassi Thymeleaf per Map

Quando il service restituisce una `Map<String, Object>`, in Thymeleaf si deve usare:

```html
<!-- Accesso a Map -->
${mapVariable['key']}

<!-- NON usare (solo per oggetti con getter) -->
${mapVariable.property}
```

## 📝 Note

Il service `ReportisticaService` usa:
```java
public Map<String, Object> getRecapGenerale() {
    return webClient.post()
            .uri("/api/v1/report/creareport")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Map.class)  // Restituisce LinkedHashMap
            .block();
}
```

Questo è corretto perché la risposta JSON dal backend viene deserializzata in una Map dinamica.

## ✅ Risultato

- ✅ Dashboard reportistica si carica correttamente
- ✅ Tutti i valori vengono visualizzati
- ✅ Nessun errore Thymeleaf
- ✅ Formattazione numeri e valute funzionante

Il problema è stato completamente risolto! 🎉
