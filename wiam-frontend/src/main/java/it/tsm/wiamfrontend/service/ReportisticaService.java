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
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getReportPokemonCards() {
        log.debug("Fetching report Pokemon cards");
        ReportRequest request = new ReportRequest("POKEMON", "CARD");
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getReportPokemonSealed() {
        log.debug("Fetching report Pokemon sealed");
        ReportRequest request = new ReportRequest("POKEMON", "SEALED");
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getReportOnePiece() {
        log.debug("Fetching report One Piece");
        ReportRequest request = new ReportRequest("ONEPIECE", null);
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getReportOnePieceCards() {
        log.debug("Fetching report One Piece cards");
        ReportRequest request = new ReportRequest("ONEPIECE", "CARD");
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getReportOnePieceSealed() {
        log.debug("Fetching report One Piece sealed");
        ReportRequest request = new ReportRequest("ONEPIECE", "SEALED");
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getProfittiTotali() {
        log.debug("Fetching profitti totali");
        ReportRequest request = new ReportRequest("TUTTO", null);
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getProfittiPokemon() {
        log.debug("Fetching profitti Pokemon");
        ReportRequest request = new ReportRequest("POKEMON", null);
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    public Map<String, Object> getProfittiOnePiece() {
        log.debug("Fetching profitti One Piece");
        ReportRequest request = new ReportRequest("ONEPIECE", null);
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
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
