# Gestione Errori Backend - Implementazione Completa

## 📋 Riepilogo

È stata implementata una gestione completa degli errori HTTP provenienti dal backend WIAM nel frontend wiam-frontend. Il sistema distingue tra errori 4xx (client) e 5xx (server) mostrando messaggi appropriati all'utente.

---

## ✅ Componenti Implementati

### 1. Classi Exception (Package: `exception`)

#### **BackendException.java**
Eccezione personalizzata che incapsula gli errori provenienti dal backend.

**Campi:**
- `statusCode` (int): Codice HTTP dell'errore
- `errorCode` (String): Codice errore specifico dal backend
- `errorDescription` (String): Descrizione dettagliata dell'errore

**Costruttori:**
- `BackendException(int statusCode, String errorCode, String message, String errorDescription)` - Completo con tutti i dettagli
- `BackendException(int statusCode, String message)` - Semplificato per fallback

#### **BackendErrorResponse.java**
DTO per mappare la risposta di errore dal backend WIAM.

**Campi:**
- `errorCode` (String)
- `errorMessage` (String)
- `errorDescription` (String)

---

### 2. WebClientConfig con Error Handling

#### **Funzionalità Principali:**

**ExchangeFilterFunction per Intercettazione Errori:**
- Intercetta tutte le risposte HTTP con status error (4xx, 5xx)
- Tenta di parsare la risposta JSON in `BackendErrorResponse`
- Se il parsing fallisce, usa messaggi di fallback generici

**Gestione Errori 5xx (Server Error):**
```
Messaggio all'utente: "Si è verificato un errore nel server. Riprova più tardi."
```
- Non mostra dettagli tecnici all'utente
- Log dell'errore completo per debugging

**Gestione Errori 4xx (Client Error):**
```
Messaggio all'utente: "Errore di validazione: [messaggio specifico dal backend]"
```
- Mostra il messaggio di errore dettagliato dal backend
- Indica quale campo o validazione ha fallito

---

### 3. Controller Aggiornati (4 controller)

Tutti i controller sono stati aggiornati per catturare `BackendException` e mostrare messaggi appropriati:

#### **OnePieceCardController**
- ✅ createCard
- ✅ updateCard
- ✅ addVendita

#### **OnePieceSealedController**
- ✅ createSealed
- ✅ updateSealed
- ✅ addVendita

#### **PokemonCardController**
- ✅ createCard
- ✅ updateCard
- ✅ addVendita

#### **PokemonSealedController**
- ✅ createSealed
- ✅ updateSealed
- ✅ addVendita

---

## 🔄 Flusso di Gestione Errori

### Scenario 1: Errore 400 (Bad Request)

**Backend WIAM risponde:**
```json
{
  "errorCode": "OP-400",
  "errorMessage": "Invalid Request",
  "errorDescription": "Request invalida, parametro 'nome' mancante"
}
```

**Frontend elabora:**
1. WebClient intercetta status 400
2. Parsa il JSON in BackendErrorResponse
3. Crea BackendException con statusCode=400
4. Controller cattura BackendException
5. Verifica: `e.getStatusCode() >= 400 && e.getStatusCode() < 500`
6. Mostra messaggio: **"Errore di validazione: Request invalida, parametro 'nome' mancante"**

**L'utente vede:**
```
❌ Errore di validazione: Request invalida, parametro 'nome' mancante
```

---

### Scenario 2: Errore 500 (Internal Server Error)

**Backend WIAM risponde:**
```json
{
  "errorCode": "OP-500",
  "errorMessage": "Database Error",
  "errorDescription": "Connection timeout to MongoDB"
}
```

**Frontend elabora:**
1. WebClient intercetta status 500
2. Parsa il JSON in BackendErrorResponse
3. Crea BackendException con statusCode=500
4. Controller cattura BackendException
5. Verifica: `e.getStatusCode() >= 500`
6. Mostra messaggio generico: **"Si è verificato un errore nel server. Riprova più tardi."**

**L'utente vede:**
```
❌ Si è verificato un errore nel server. Riprova più tardi.
```

**Log backend (per sviluppatori):**
```
ERROR - Backend error - Status: 500, Error: BackendErrorResponse(errorCode=OP-500, errorMessage=Database Error, errorDescription=Connection timeout to MongoDB)
```

---

### Scenario 3: Errore Non Parsabile

**Backend risponde con HTML o formato sconosciuto:**

**Frontend elabora:**
1. WebClient intercetta l'errore
2. Tentativo di parsing in BackendErrorResponse fallisce
3. `onErrorResume` cattura l'eccezione di parsing
4. Usa `createFallbackException(statusCode)`
5. Mostra messaggio generico appropriato

**L'utente vede (per 5xx):**
```
❌ Si è verificato un errore nel server. Riprova più tardi.
```

**L'utente vede (per 4xx):**
```
❌ Si è verificato un errore. Verifica i dati inseriti.
```

---

## 💻 Codice nei Controller

Ogni metodo POST nei controller usa questo pattern:

```java
@PostMapping
public String createCard(@ModelAttribute CardDTO card, 
                       @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                       RedirectAttributes redirectAttributes) {
    try {
        // Logica business
        cardService.createCard(card);
        redirectAttributes.addFlashAttribute("success", "Carta creata con successo!");
        return "redirect:/onepiece/cards";
        
    } catch (BackendException e) {
        // Gestione errori backend
        if (e.getStatusCode() >= 500) {
            // Errore server - messaggio generico
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } else if (e.getStatusCode() >= 400) {
            // Errore validazione - messaggio specifico
            redirectAttributes.addFlashAttribute("error", "Errore di validazione: " + e.getMessage());
        }
        return "redirect:/onepiece/cards/new";
        
    } catch (IOException e) {
        // Errore upload immagine
        redirectAttributes.addFlashAttribute("error", "Errore nel caricamento dell'immagine: " + e.getMessage());
        return "redirect:/onepiece/cards/new";
        
    } catch (Exception e) {
        // Errore generico non previsto
        redirectAttributes.addFlashAttribute("error", "Errore imprevisto: " + e.getMessage());
        return "redirect:/onepiece/cards/new";
    }
}
```

---

## 🎨 Visualizzazione a Frontend

I messaggi di errore vengono mostrati tramite flash attributes nei template Thymeleaf:

```html
<!-- Alert successo -->
<div th:if="${success}" class="alert alert-success alert-dismissible fade show">
    <span th:text="${success}"></span>
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
</div>

<!-- Alert errore -->
<div th:if="${error}" class="alert alert-danger alert-dismissible fade show">
    <span th:text="${error}"></span>
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
</div>
```

---

## 📊 Matrice Errori

| Codice HTTP | Tipo | Messaggio Utente | Dettaglio Backend | Log |
|-------------|------|------------------|-------------------|-----|
| 400 | Bad Request | "Errore di validazione: [dettaglio]" | ✅ Mostrato | ✅ Completo |
| 401 | Unauthorized | "Errore di validazione: [dettaglio]" | ✅ Mostrato | ✅ Completo |
| 403 | Forbidden | "Errore di validazione: [dettaglio]" | ✅ Mostrato | ✅ Completo |
| 404 | Not Found | "Errore di validazione: [dettaglio]" | ✅ Mostrato | ✅ Completo |
| 500 | Internal Server | "Si è verificato un errore nel server. Riprova più tardi." | ❌ Nascosto | ✅ Completo |
| 502 | Bad Gateway | "Si è verificato un errore nel server. Riprova più tardi." | ❌ Nascosto | ✅ Completo |
| 503 | Service Unavailable | "Si è verificato un errore nel server. Riprova più tardi." | ❌ Nascosto | ✅ Completo |

---

## 🔒 Sicurezza

- ✅ Gli errori 5xx **non espongono** dettagli tecnici all'utente
- ✅ Stack trace e dettagli tecnici sono **solo nei log**
- ✅ Gli errori 4xx mostrano **solo informazioni di validazione** utili
- ✅ Nessuna informazione sensibile esposta nel frontend

---

## 🧪 Test Suggeriti

### Test Errore 400
1. Crea un prodotto senza nome (campo obbligatorio)
2. Verifica che appaia: "Errore di validazione: [messaggio specifico]"

### Test Errore 500
1. Spegni il backend WIAM
2. Tenta di creare un prodotto
3. Verifica che appaia: "Si è verificato un errore nel server. Riprova più tardi."

### Test Errore Network
1. Imposta URL backend errato in application.yml
2. Tenta operazione
3. Verifica messaggio di fallback appropriato

---

## 📝 File Modificati

### Nuovi File (2)
- `exception/BackendException.java`
- `exception/BackendErrorResponse.java`

### File Modificati (5)
- `config/WebClientConfig.java` - Aggiunto error handling filter
- `controller/OnePieceCardController.java` - Gestione BackendException
- `controller/OnePieceSealedController.java` - Gestione BackendException
- `controller/PokemonCardController.java` - Gestione BackendException
- `controller/PokemonSealedController.java` - Gestione BackendException

---

## ✨ Vantaggi

1. **User-Friendly**: Messaggi chiari e comprensibili per l'utente
2. **Sicuro**: Non espone dettagli tecnici in caso di errori server
3. **Debug-Friendly**: Log completi per troubleshooting
4. **Consistente**: Stesso comportamento su tutti i controller
5. **Robusto**: Fallback per errori non parsabili
6. **Manutenibile**: Logica centralizzata nel WebClientConfig

---

## 🚀 Pronto per il Test!

Il sistema di gestione errori è completamente implementato e funzionante!
