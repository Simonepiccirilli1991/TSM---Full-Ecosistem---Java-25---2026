# Implementazione Filtering e Logica Reportistica Frontend

## Data: 30 Gennaio 2026

## Obiettivo
Implementare la logica di filtering e calcolo statistiche sulla risposta del microservizio WIAM per la reportistica, processando i dati in base allo stato (venduto/acquistato) e calcolando profitti, costi e altre metriche.

## Modifiche Effettuate

### 1. Nuovi DTO Creati

#### ReportisticaRecapDTO.java
DTO principale che contiene tutte le statistiche aggregate:
- **Conteggi**: totaleAcquisti, totaleVendite, totaleInVendita
- **Importi acquisti**: totaleSpeso, costoMedioAcquisto
- **Importi vendite**: totaleIncassato, totaleCostiVendita, ricavoNettoVendite, prezzoMedioVendita
- **Profitti**: profittoNetto, marginePercentuale
- **Dettagli per tipo**: pokemon, onePiece

#### ReportisticaDettaglioDTO.java
DTO per le statistiche specifiche per tipo prodotto (Pokemon/OnePiece):
- tipoProdotto
- totaleAcquisti, totaleVendite, totaleInVendita
- totaleSpeso, totaleIncassato, totaleCostiVendita
- profittoNetto

#### ReportItemDTO.java
DTO per mappare ogni singolo item dalla risposta del backend:
- Tutti i campi dell'entity (id, nome, prezzo, ecc.)
- stato: "disponibile" / "non disponibile" (stato del prodotto)
- statoAcquisto: "acquistato" o "venduto" (stato dell'acquisto - CAMPO UTILIZZATO PER I CALCOLI)
- vendita: Map contenente i dati della vendita (se presente)

#### ReportResponseDTO.java
DTO wrapper per la risposta del backend contenente la lista di ReportItemDTO.

### 2. Logica di Calcolo nel Service

#### ReportisticaService.java - Metodo calcolaStatistiche()

Il metodo elabora la lista di ReportItemDTO e implementa la seguente logica:

**Per ogni item:**

1. **Conteggio Acquisti**: Tutti gli item vengono contati come acquisti
2. **Totale Speso**: Somma di tutti i prezzoAcquisto
3. **Analisi per StatoAcquisto** (campo chiave per i calcoli):
   - Se statoAcquisto = "venduto":
     - Incrementa totaleVendite
     - Estrae dalla mappa vendita:
       - prezzoVendita → aggiunto a totaleIncassato
       - costiVendita → aggiunto a totaleCostiVendita
   - Se statoAcquisto = "acquistato":
     - Incrementa totaleInVendita (oggetti ancora da vendere)

4. **Suddivisione per Tipo Prodotto**: Tutti i calcoli vengono replicati separatamente per "Pokemon" e "OnePiece"

**Calcoli Finali:**

1. **Ricavo Netto Vendite** = totaleIncassato - totaleCostiVendita
2. **Profitto Netto** = ricavoNettoVendite - totaleSpeso
3. **Margine Percentuale** = (profittoNetto / totaleSpeso) * 100
4. **Costo Medio Acquisto** = totaleSpeso / totaleAcquisti
5. **Prezzo Medio Vendita** = totaleIncassato / totaleVendite

### 3. Controller Aggiornato

#### ReportisticaController.java
- Rimosso il metodo getProfittiTotali() (duplicato)
- Il metodo dashboard() ora passa un singolo oggetto ReportisticaRecapDTO al template
- Gestione errori mantenuta

### 4. Template HTML Aggiornato

#### dashboard.html

**Sezione Recap Generale** - Divisa in due righe:

**Prima Riga - Contatori:**
- Totale Acquisti
- Totale Vendite (oggetti venduti)
- In Vendita (oggetti ancora da vendere)

**Seconda Riga - Importi:**
- Totale Speso (con media per acquisto)
- Totale Incassato (con media per vendita)
- Costi Vendita (commissioni e spese)
- Ricavo Netto Vendite (incasso - costi)

**Sezione Analisi Profitti:**

**Card Principale:**
- Profitto Netto Totale (grande display)
- Margine Percentuale
- Colore verde se positivo, rosso se negativo

**Due Card per Tipo Prodotto:**
- Pokemon (bordo warning/giallo)
- One Piece (bordo danger/rosso)

Ogni card mostra:
- Numero acquisti e vendite
- Totale speso e incassato
- Profitto netto con colore condizionale

## Logica Implementata

### Stati Gestiti
Il filtering si basa sul campo **statoAcquisto** del ReportDto:
1. **"acquistato"**: Prodotto acquistato ma non ancora venduto (in vendita)
2. **"venduto"**: Prodotto venduto con dati di vendita associati

**Nota:** Il campo `stato` (disponibile/non disponibile) non viene utilizzato per i calcoli della reportistica.

### Casi d'Uso Coperti

1. **Inventory Management**: Conteggio prodotti in vendita vs venduti
2. **Cash Flow Analysis**: 
   - Quanto ho speso (totaleSpeso)
   - Quanto ho incassato (totaleIncassato)
   - Costi associati (totaleCostiVendita)
   - Ricavo netto effettivo

3. **Profitability Analysis**:
   - Profitto netto totale
   - Profitto per categoria prodotto
   - Margine percentuale

4. **Performance Metrics**:
   - Prezzo medio di acquisto
   - Prezzo medio di vendita
   - Confronto tra categorie

5. **Decision Support**:
   - Quale categoria performa meglio
   - Qual è il ritorno sull'investimento
   - Quali sono i costi nascosti (commissioni)

## Conversione Dati

Il metodo `convertToBigDecimal()` gestisce la conversione da vari tipi (Double, Integer, Long, String, BigDecimal) a BigDecimal per garantire precisione nei calcoli monetari.

## Precisione Numerica

Tutti i calcoli monetari utilizzano BigDecimal con:
- RoundingMode.HALF_UP
- Scala di 2 decimali per gli importi
- Scala di 4 decimali per calcoli intermedi

## Testing

Per testare l'implementazione:
1. Avviare il microservizio WIAM (backend) sulla porta 8081
2. Avviare wiam-frontend sulla porta 8080
3. Navigare su http://localhost:8080/reportistica
4. Verificare che tutti i dati siano visualizzati correttamente
5. Controllare i calcoli incrociando con i dati del backend

## Note Tecniche

- Le modifiche sono state effettuate SOLO su wiam-frontend
- Il backend WIAM non è stato modificato
- La response del backend viene processata lato frontend
- Compatibile con la struttura esistente del microservizio WIAM
