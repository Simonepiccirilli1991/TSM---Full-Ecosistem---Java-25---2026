# WIAM Frontend

Frontend web per il sistema di gestione WIAM (Web Inventory And Management) per carte collezionabili.

## Tecnologie

- Java 21
- Spring Boot 3.2.2
- Thymeleaf
- Bootstrap 5
- WebClient (per chiamate API)

## Requisiti

- Java 21+
- Maven 3.6+
- Backend WIAM in esecuzione su `http://localhost:8081`

## Configurazione

L'applicazione è configurata tramite `application.yml`:

```yaml
server:
  port: 8080

wiam:
  backend:
    url: http://localhost:8081
```

## Avvio

### Con Maven

```bash
cd wiam-frontend
mvn spring-boot:run
```

### Con Java

```bash
mvn clean package
java -jar target/wiam-frontend-0.0.1-SNAPSHOT.jar
```

## Accesso

Dopo l'avvio, l'applicazione sarà disponibile su:

```
http://localhost:8080
```

## Funzionalità

### Pokémon
- Gestione carte Pokémon (CRUD completo)
- Gestione sealed Pokémon (CRUD completo)
- Registrazione vendite

### One Piece
- Gestione carte One Piece (CRUD completo)
- Gestione sealed One Piece (CRUD completo)
- Registrazione vendite

### Reportistica
- Dashboard generale con statistiche
- Report dettagliati per categoria
- Analisi profitti

## Struttura

```
wiam-frontend/
├── src/main/java/it/tsm/wiamfrontend/
│   ├── config/          # Configurazione WebClient
│   ├── controller/      # Controller MVC
│   ├── service/         # Servizi per chiamate API
│   └── dto/             # Data Transfer Objects
├── src/main/resources/
│   ├── templates/       # Template Thymeleaf
│   │   ├── fragments/   # Componenti riutilizzabili
│   │   ├── pokemon/     # Pagine Pokémon
│   │   ├── onepiece/    # Pagine One Piece
│   │   └── reportistica/# Pagine reportistica
│   └── static/          # CSS, JS, immagini
└── pom.xml
```

## Note

- L'applicazione richiede che il backend WIAM sia in esecuzione
- Non è richiesta autenticazione (accesso libero)
- Tutte le chiamate API sono sincrone tramite WebClient
