# AGENTS.md - Istruzioni per la creazione del Frontend WIAM

## ✅ STATO: IMPLEMENTAZIONE COMPLETATA (29 Gennaio 2026)

Il frontend WIAM è stato completamente implementato. Tutti i file sono nella cartella `wiam-frontend/`.

Per compilare e avviare:
```bash
cd wiam-frontend
mvn spring-boot:run
```

Accesso: http://localhost:8080

Documentazione completa in:
- `wiam-frontend/README.md`
- `wiam-frontend/DEPLOYMENT_GUIDE.md`
- `wiam-frontend/ENDPOINT_MAPPING.md`
- `wiam-frontend/IMPLEMENTAZIONE_COMPLETA.md`

---

## Contesto del Progetto
Questo è un ecosistema di microservizi. Il MS `wiam` è un gestionale per:
- Gestione carte e sealed Pokemon
- Gestione carte e sealed One Piece
- Gestione vendite per entrambe le categorie
- Reportistica (recap acquisti/vendite)

## Obiettivo
Creare un modulo `wiam-frontend` con Spring Boot + Thymeleaf che si integri con le API REST del MS `wiam`.

## Stack Tecnologico Richiesto
- Java 21+
- Spring Boot 3.x
- Spring Web
- Thymeleaf
- WebClient (per chiamate HTTP al backend)
- Bootstrap 5 (per UI)
- Maven

## Struttura del Modulo da Creare
## API Backend WIAM da Integrare

Base URL: `http://localhost:8081`

---

### POKEMON

#### Pokemon Cards
- `GET /pokemon/card` - Lista tutte le carte Pokemon
- `GET /pokemon/card/{id}` - Dettaglio carta Pokemon
- `POST /pokemon/card` - Crea nuova carta Pokemon
- `PUT /pokemon/card/{id}` - Modifica carta Pokemon
- `DELETE /pokemon/card/{id}` - Elimina carta Pokemon

#### Pokemon Sealed
- `GET /pokemon/sealed` - Lista tutti i sealed Pokemon
- `GET /pokemon/sealed/{id}` - Dettaglio sealed Pokemon
- `POST /pokemon/sealed` - Crea nuovo sealed Pokemon
- `PUT /pokemon/sealed/{id}` - Modifica sealed Pokemon
- `DELETE /pokemon/sealed/{id}` - Elimina sealed Pokemon

#### Pokemon Vendite
- `GET /pokemon/vendita` - Lista tutte le vendite Pokemon
- `GET /pokemon/vendita/{id}` - Dettaglio vendita Pokemon
- `POST /pokemon/vendita/card` - Crea vendita carta Pokemon
- `POST /pokemon/vendita/sealed` - Crea vendita sealed Pokemon
- `PUT /pokemon/vendita/{id}` - Modifica vendita Pokemon
- `DELETE /pokemon/vendita/{id}` - Elimina vendita Pokemon

---

### ONE PIECE

#### One Piece Cards
- `GET /onepiece/card` - Lista tutte le carte One Piece
- `GET /onepiece/card/{id}` - Dettaglio carta One Piece
- `POST /onepiece/card` - Crea nuova carta One Piece
- `PUT /onepiece/card/{id}` - Modifica carta One Piece
- `DELETE /onepiece/card/{id}` - Elimina carta One Piece

#### One Piece Sealed
- `GET /onepiece/sealed` - Lista tutti i sealed One Piece
- `GET /onepiece/sealed/{id}` - Dettaglio sealed One Piece
- `POST /onepiece/sealed` - Crea nuovo sealed One Piece
- `PUT /onepiece/sealed/{id}` - Modifica sealed One Piece
- `DELETE /onepiece/sealed/{id}` - Elimina sealed One Piece

#### One Piece Vendite
- `GET /onepiece/vendita` - Lista tutte le vendite One Piece
- `GET /onepiece/vendita/{id}` - Dettaglio vendita One Piece
- `POST /onepiece/vendita/card` - Crea vendita carta One Piece
- `POST /onepiece/vendita/sealed` - Crea vendita sealed One Piece
- `PUT /onepiece/vendita/{id}` - Modifica vendita One Piece
- `DELETE /onepiece/vendita/{id}` - Elimina vendita One Piece

---

### REPORTISTICA

- `GET /reportistica/recap` - Recap generale di tutte le operazioni
- `GET /reportistica/pokemon` - Report specifico Pokemon
- `GET /reportistica/pokemon/cards` - Report carte Pokemon
- `GET /reportistica/pokemon/sealed` - Report sealed Pokemon
- `GET /reportistica/onepiece` - Report specifico One Piece
- `GET /reportistica/onepiece/cards` - Report carte One Piece
- `GET /reportistica/onepiece/sealed` - Report sealed One Piece
- `GET /reportistica/profitti` - Calcolo profitti totali
- `GET /reportistica/profitti/pokemon` - Profitti Pokemon
- `GET /reportistica/profitti/onepiece` - Profitti One Piece

---

### GLOBAL CONTROLLER (Health/Info)

- `GET /health` - Health check del servizio
- `GET /info` - Informazioni sul servizio

---

## Requisiti Funzionali

1. **NESSUNA AUTENTICAZIONE** - L'accesso deve essere libero
2. **Homepage** con navigazione a tutte le sezioni (Pokemon, One Piece, Reportistica)
3. **CRUD completo** per:
    - Carte Pokemon
    - Sealed Pokemon
    - Vendite Pokemon
    - Carte One Piece
    - Sealed One Piece
    - Vendite One Piece
4. **Dashboard reportistica** con:
    - Tabelle riepilogative
    - Grafici profitti
    - Filtri per categoria (Pokemon/One Piece)
5. **Gestione errori** - mostrare messaggi user-friendly
6. **Responsive design** con Bootstrap
7. **Navigazione** - Navbar con menu dropdown per categorie

## Configurazione

```yaml
server:
  port: 8080

wiam:
  backend:
    url: http://localhost:8081

spring:
  thymeleaf:
    cache: false
    prefix: classpath:/templates/
    suffix: .html
