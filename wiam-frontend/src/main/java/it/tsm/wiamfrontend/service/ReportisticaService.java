package it.tsm.wiamfrontend.service;

import it.tsm.wiamfrontend.dto.ReportItemDTO;
import it.tsm.wiamfrontend.dto.ReportResponseDTO;
import it.tsm.wiamfrontend.dto.ReportisticaDettaglioDTO;
import it.tsm.wiamfrontend.dto.ReportisticaRecapDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportisticaService {

    private final WebClient webClient;

    public ReportisticaRecapDTO getRecapGenerale() {
        log.debug("Fetching recap generale");
        // Usa il metodo POST con request body per reportistica
        ReportRequest request = new ReportRequest("TUTTO", null);

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return ReportisticaRecapDTO.builder().build();
        }

        return calcolaStatistiche(response.getReport());
    }

    public Map<String, Object> getReportPokemon() {
        log.debug("Fetching report Pokemon");
        ReportRequest request = new ReportRequest("POKEMON", null);

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totaleProdotti", 0,
                "disponibili", 0,
                "venduti", 0,
                "valoreTotale", 0.0
            );
        }

        return processaReportSemplice(response.getReport());
    }

    public Map<String, Object> getReportPokemonCards() {
        log.debug("Fetching report Pokemon cards");
        ReportRequest request = new ReportRequest("POKEMON", "CARD");

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totale", 0,
                "disponibili", 0,
                "vendute", 0,
                "valore", 0.0
            );
        }

        return processaReportDettaglio(response.getReport());
    }

    public Map<String, Object> getReportPokemonSealed() {
        log.debug("Fetching report Pokemon sealed");
        ReportRequest request = new ReportRequest("POKEMON", "SEALED");

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totale", 0,
                "disponibili", 0,
                "venduti", 0,
                "valore", 0.0
            );
        }

        return processaReportDettaglio(response.getReport());
    }

    public Map<String, Object> getReportOnePiece() {
        log.debug("Fetching report One Piece");
        ReportRequest request = new ReportRequest("ONEPIECE", null);

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totaleProdotti", 0,
                "disponibili", 0,
                "venduti", 0,
                "valoreTotale", 0.0
            );
        }

        return processaReportSemplice(response.getReport());
    }

    public Map<String, Object> getReportOnePieceCards() {
        log.debug("Fetching report One Piece cards");
        ReportRequest request = new ReportRequest("ONEPIECE", "CARD");

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totale", 0,
                "disponibili", 0,
                "vendute", 0,
                "valore", 0.0
            );
        }

        return processaReportDettaglio(response.getReport());
    }

    public Map<String, Object> getReportOnePieceSealed() {
        log.debug("Fetching report One Piece sealed");
        ReportRequest request = new ReportRequest("ONEPIECE", "SEALED");

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totale", 0,
                "disponibili", 0,
                "venduti", 0,
                "valore", 0.0
            );
        }

        return processaReportDettaglio(response.getReport());
    }

    public Map<String, Object> getProfittiTotali() {
        log.debug("Fetching profitti totali");
        ReportRequest request = new ReportRequest("TUTTO", null);

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totaleSpeso", 0.0,
                "totaleIncassato", 0.0,
                "profittoNetto", 0.0
            );
        }

        return processaProfitti(response.getReport());
    }

    public Map<String, Object> getProfittiPokemon() {
        log.debug("Fetching profitti Pokemon");
        ReportRequest request = new ReportRequest("POKEMON", null);

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totaleSpeso", 0.0,
                "totaleIncassato", 0.0,
                "profittoNetto", 0.0
            );
        }

        return processaProfitti(response.getReport());
    }

    public Map<String, Object> getProfittiOnePiece() {
        log.debug("Fetching profitti One Piece");
        ReportRequest request = new ReportRequest("ONEPIECE", null);

        ReportResponseDTO response = webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReportResponseDTO.class)
                .block();

        if (response == null || response.getReport() == null) {
            return Map.of(
                "totaleSpeso", 0.0,
                "totaleIncassato", 0.0,
                "profittoNetto", 0.0
            );
        }

        return processaProfitti(response.getReport());
    }

    /**
     * Calcola le statistiche dettagliate dalla lista di report items
     */
    private ReportisticaRecapDTO calcolaStatistiche(List<ReportItemDTO> items) {
        log.debug("Calcolando statistiche per {} items", items.size());

        // Inizializza contatori
        int totaleAcquisti = 0;
        int totaleVendite = 0;
        int totaleInVendita = 0;

        BigDecimal totaleSpeso = BigDecimal.ZERO;
        BigDecimal totaleIncassato = BigDecimal.ZERO;
        BigDecimal totaleCostiVendita = BigDecimal.ZERO;

        // Statistiche per tipo prodotto
        ReportisticaDettaglioDTO pokemonStats = ReportisticaDettaglioDTO.builder()
                .tipoProdotto("Pokemon")
                .totaleAcquisti(0)
                .totaleVendite(0)
                .totaleInVendita(0)
                .totaleSpeso(BigDecimal.ZERO)
                .totaleIncassato(BigDecimal.ZERO)
                .totaleCostiVendita(BigDecimal.ZERO)
                .profittoNetto(BigDecimal.ZERO)
                .build();

        ReportisticaDettaglioDTO onePieceStats = ReportisticaDettaglioDTO.builder()
                .tipoProdotto("OnePiece")
                .totaleAcquisti(0)
                .totaleVendite(0)
                .totaleInVendita(0)
                .totaleSpeso(BigDecimal.ZERO)
                .totaleIncassato(BigDecimal.ZERO)
                .totaleCostiVendita(BigDecimal.ZERO)
                .profittoNetto(BigDecimal.ZERO)
                .build();

        // Processa ogni item
        for (ReportItemDTO item : items) {
            totaleAcquisti++;

            // Aggiungi il prezzo di acquisto
            if (item.getPrezzoAcquisto() != null) {
                BigDecimal prezzoAcquisto = BigDecimal.valueOf(item.getPrezzoAcquisto());
                totaleSpeso = totaleSpeso.add(prezzoAcquisto);

                // Aggiungi al tipo prodotto specifico
                if ("Pokemon".equalsIgnoreCase(item.getTipoProdotto())) {
                    pokemonStats.setTotaleAcquisti(pokemonStats.getTotaleAcquisti() + 1);
                    pokemonStats.setTotaleSpeso(pokemonStats.getTotaleSpeso().add(prezzoAcquisto));
                } else if ("OnePiece".equalsIgnoreCase(item.getTipoProdotto())) {
                    onePieceStats.setTotaleAcquisti(onePieceStats.getTotaleAcquisti() + 1);
                    onePieceStats.setTotaleSpeso(onePieceStats.getTotaleSpeso().add(prezzoAcquisto));
                }
            }

            // Controlla lo statoAcquisto (venduto/acquistato)
            if ("venduto".equalsIgnoreCase(item.getStatoAcquisto())) {
                totaleVendite++;

                // Estrai i dati della vendita
                if (item.getVendita() != null) {
                    Map<String, Object> vendita = item.getVendita();

                    // Prezzo vendita
                    Object prezzoVenditaObj = vendita.get("prezzoVendita");
                    if (prezzoVenditaObj != null) {
                        BigDecimal prezzoVendita = convertToBigDecimal(prezzoVenditaObj);
                        totaleIncassato = totaleIncassato.add(prezzoVendita);

                        if ("Pokemon".equalsIgnoreCase(item.getTipoProdotto())) {
                            pokemonStats.setTotaleVendite(pokemonStats.getTotaleVendite() + 1);
                            pokemonStats.setTotaleIncassato(pokemonStats.getTotaleIncassato().add(prezzoVendita));
                        } else if ("OnePiece".equalsIgnoreCase(item.getTipoProdotto())) {
                            onePieceStats.setTotaleVendite(onePieceStats.getTotaleVendite() + 1);
                            onePieceStats.setTotaleIncassato(onePieceStats.getTotaleIncassato().add(prezzoVendita));
                        }
                    }

                    // Costi vendita
                    Object costiVenditaObj = vendita.get("costiVendita");
                    if (costiVenditaObj != null) {
                        BigDecimal costiVendita = convertToBigDecimal(costiVenditaObj);
                        totaleCostiVendita = totaleCostiVendita.add(costiVendita);

                        if ("Pokemon".equalsIgnoreCase(item.getTipoProdotto())) {
                            pokemonStats.setTotaleCostiVendita(pokemonStats.getTotaleCostiVendita().add(costiVendita));
                        } else if ("OnePiece".equalsIgnoreCase(item.getTipoProdotto())) {
                            onePieceStats.setTotaleCostiVendita(onePieceStats.getTotaleCostiVendita().add(costiVendita));
                        }
                    }
                }
            } else if ("acquistato".equalsIgnoreCase(item.getStatoAcquisto())) {
                totaleInVendita++;

                if ("Pokemon".equalsIgnoreCase(item.getTipoProdotto())) {
                    pokemonStats.setTotaleInVendita(pokemonStats.getTotaleInVendita() + 1);
                } else if ("OnePiece".equalsIgnoreCase(item.getTipoProdotto())) {
                    onePieceStats.setTotaleInVendita(onePieceStats.getTotaleInVendita() + 1);
                }
            }
        }

        // Calcola ricavo netto e profitto
        BigDecimal ricavoNettoVendite = totaleIncassato.subtract(totaleCostiVendita);
        BigDecimal profittoNetto = ricavoNettoVendite.subtract(totaleSpeso);

        // Calcola margine percentuale
        BigDecimal marginePercentuale = BigDecimal.ZERO;
        if (totaleSpeso.compareTo(BigDecimal.ZERO) > 0) {
            marginePercentuale = profittoNetto
                    .divide(totaleSpeso, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        // Calcola profitto per tipo prodotto
        pokemonStats.setProfittoNetto(
                pokemonStats.getTotaleIncassato()
                        .subtract(pokemonStats.getTotaleCostiVendita())
                        .subtract(pokemonStats.getTotaleSpeso())
        );

        onePieceStats.setProfittoNetto(
                onePieceStats.getTotaleIncassato()
                        .subtract(onePieceStats.getTotaleCostiVendita())
                        .subtract(onePieceStats.getTotaleSpeso())
        );

        // Calcola prezzi medi
        BigDecimal costoMedioAcquisto = totaleAcquisti > 0
                ? totaleSpeso.divide(BigDecimal.valueOf(totaleAcquisti), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal prezzoMedioVendita = totaleVendite > 0
                ? totaleIncassato.divide(BigDecimal.valueOf(totaleVendite), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        // Costruisci il DTO finale
        ReportisticaRecapDTO recap = ReportisticaRecapDTO.builder()
                .totaleAcquisti(totaleAcquisti)
                .totaleVendite(totaleVendite)
                .totaleInVendita(totaleInVendita)
                .totaleSpeso(totaleSpeso.setScale(2, RoundingMode.HALF_UP))
                .totaleIncassato(totaleIncassato.setScale(2, RoundingMode.HALF_UP))
                .totaleCostiVendita(totaleCostiVendita.setScale(2, RoundingMode.HALF_UP))
                .ricavoNettoVendite(ricavoNettoVendite.setScale(2, RoundingMode.HALF_UP))
                .profittoNetto(profittoNetto.setScale(2, RoundingMode.HALF_UP))
                .marginePercentuale(marginePercentuale)
                .costoMedioAcquisto(costoMedioAcquisto)
                .prezzoMedioVendita(prezzoMedioVendita)
                .pokemon(pokemonStats)
                .onePiece(onePieceStats)
                .build();

        log.debug("Statistiche calcolate: {}", recap);
        return recap;
    }

    /**
     * Processa report semplice (generale) - totaleProdotti, disponibili, venduti, valoreTotale
     */
    private Map<String, Object> processaReportSemplice(List<ReportItemDTO> items) {
        log.debug("Processando report semplice per {} items", items.size());

        int totaleProdotti = items.size();
        int disponibili = 0;
        int venduti = 0;
        double valoreTotale = 0.0;

        for (ReportItemDTO item : items) {
            // Conta disponibili/venduti basato su statoAcquisto
            if ("acquistato".equalsIgnoreCase(item.getStatoAcquisto())) {
                disponibili++;
            } else if ("venduto".equalsIgnoreCase(item.getStatoAcquisto())) {
                venduti++;
            }

            // Somma il valore (prezzo acquisto)
            if (item.getPrezzoAcquisto() != null) {
                valoreTotale += item.getPrezzoAcquisto();
            }
        }

        return Map.of(
            "totaleProdotti", totaleProdotti,
            "disponibili", disponibili,
            "venduti", venduti,
            "valoreTotale", valoreTotale
        );
    }

    /**
     * Processa report dettaglio (cards/sealed) - totale, disponibili, vendute, valore
     */
    private Map<String, Object> processaReportDettaglio(List<ReportItemDTO> items) {
        log.debug("Processando report dettaglio per {} items", items.size());

        int totale = items.size();
        int disponibili = 0;
        int vendute = 0;
        double valore = 0.0;

        for (ReportItemDTO item : items) {
            // Conta disponibili/vendute basato su statoAcquisto
            if ("acquistato".equalsIgnoreCase(item.getStatoAcquisto())) {
                disponibili++;
                // Il valore conta solo i disponibili (inventario attuale)
                if (item.getPrezzoAcquisto() != null) {
                    valore += item.getPrezzoAcquisto();
                }
            } else if ("venduto".equalsIgnoreCase(item.getStatoAcquisto())) {
                vendute++;
            }
        }

        return Map.of(
            "totale", totale,
            "disponibili", disponibili,
            "vendute", vendute,
            "valore", valore
        );
    }

    /**
     * Processa profitti - totaleSpeso, totaleIncassato, profittoNetto
     */
    private Map<String, Object> processaProfitti(List<ReportItemDTO> items) {
        log.debug("Processando profitti per {} items", items.size());

        double totaleSpeso = 0.0;
        double totaleIncassato = 0.0;
        double totaleCostiVendita = 0.0;

        for (ReportItemDTO item : items) {
            // Somma sempre il prezzo di acquisto (speso)
            if (item.getPrezzoAcquisto() != null) {
                totaleSpeso += item.getPrezzoAcquisto();
            }

            // Se venduto, aggiungi incassi
            if ("venduto".equalsIgnoreCase(item.getStatoAcquisto()) && item.getVendita() != null) {
                Map<String, Object> vendita = item.getVendita();

                Object prezzoVenditaObj = vendita.get("prezzoVendita");
                if (prezzoVenditaObj != null) {
                    totaleIncassato += convertToBigDecimal(prezzoVenditaObj).doubleValue();
                }

                Object costiVenditaObj = vendita.get("costiVendita");
                if (costiVenditaObj != null) {
                    totaleCostiVendita += convertToBigDecimal(costiVenditaObj).doubleValue();
                }
            }
        }

        double profittoNetto = totaleIncassato - totaleCostiVendita - totaleSpeso;

        return Map.of(
            "totaleSpeso", totaleSpeso,
            "totaleIncassato", totaleIncassato,
            "profittoNetto", profittoNetto
        );
    }

    /**
     * Converte un Object in BigDecimal gestendo vari tipi
     */
    private BigDecimal convertToBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Double) {
            return BigDecimal.valueOf((Double) value);
        } else if (value instanceof Integer) {
            return BigDecimal.valueOf((Integer) value);
        } else if (value instanceof Long) {
            return BigDecimal.valueOf((Long) value);
        } else if (value instanceof String) {
            try {
                return new BigDecimal((String) value);
            } catch (NumberFormatException e) {
                log.warn("Cannot convert string to BigDecimal: {}", value);
                return BigDecimal.ZERO;
            }
        }

        return BigDecimal.ZERO;
    }

    // Inner class for request
    public record ReportRequest(String categoria, String tipoProdotto) {}
}
