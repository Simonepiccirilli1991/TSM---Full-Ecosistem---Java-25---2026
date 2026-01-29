package it.tsm.wiamfrontend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportisticaService {

    private final WebClient webClient;

    public Map<String, Object> getRecapGenerale() {
        log.debug("Fetching recap generale");
        // Usa il metodo POST con request body per reportistica
        ReportRequest request = new ReportRequest("TUTTO", null);
        return webClient.post()
                .uri("/api/v1/report/creareport")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
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

    // Inner class for request
    public record ReportRequest(String categoria, String tipoProdotto) {}
}
