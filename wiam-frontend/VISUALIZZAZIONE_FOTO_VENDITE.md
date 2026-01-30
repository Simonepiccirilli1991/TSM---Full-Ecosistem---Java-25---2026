# Visualizzazione Foto nei Form di Vendita

## Modifiche Implementate

È stata implementata la visualizzazione delle foto dei prodotti nei form di registrazione vendita.

### File Modificati

#### 1. Template One Piece
- **`/templates/onepiece/vendite/form-sealed.html`**: Aggiunta visualizzazione foto per sealed One Piece
- **`/templates/onepiece/vendite/form.html`**: Aggiunta visualizzazione foto per carte One Piece

#### 2. Template Pokemon
- **`/templates/pokemon/vendite/form-sealed.html`**: Aggiunta visualizzazione foto per sealed Pokemon
- **`/templates/pokemon/vendite/form.html`**: Aggiunta visualizzazione foto per carte Pokemon

### Funzionalità

Quando si apre il form per registrare una vendita, viene ora mostrata:

1. **La foto del prodotto** (se presente):
   - Visualizzata in formato thumbnail (max 200px)
   - Con bordo arrotondato e ombra
   - Conversione automatica da byte[] a base64 per la visualizzazione

2. **Placeholder** (se la foto non è presente):
   - Icona generica di immagine
   - Testo "Nessuna foto disponibile"

### Layout

La sezione informazioni prodotto è stata riorganizzata in un layout a due colonne:
- **Colonna sinistra (8/12)**: Informazioni testuali del prodotto
- **Colonna destra (4/12)**: Foto del prodotto o placeholder

### Note Tecniche

- Le foto sono salvate come `byte[]` nell'entity principale (OnePieceCard, OnePieceSealed, PokemonCard, PokemonSealed)
- La conversione a base64 per la visualizzazione avviene tramite Thymeleaf usando `T(java.util.Base64).getEncoder().encode()`
- Le foto possono essere caricate solo in fase di **creazione dell'acquisto**, non durante la registrazione della vendita
- Il formato supportato è `image/jpeg` (può essere esteso se necessario)

### Prossimi Sviluppi Possibili

Se in futuro si volesse permettere l'aggiornamento della foto durante la vendita, sarebbe necessario:
1. Aggiungere un endpoint di update nel backend WIAM
2. Modificare il form per accettare multipart/form-data
3. Aggiungere un campo di upload con preview nel form
