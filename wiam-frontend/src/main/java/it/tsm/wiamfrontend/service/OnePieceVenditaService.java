package it.tsm.wiamfrontend.service;

import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnePieceVenditaService {

    private final WebClient webClient;

    public void addVenditaCard(String cardId, VenditaDTO vendita) {
        log.debug("Adding vendita to One Piece card: {}", cardId);
        webClient.post()
                .uri("/api/v1/onepiece/addvendita")
                .bodyValue(new VenditaRequest(cardId, vendita, "CARD"))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void addVenditaSealed(String sealedId, VenditaDTO vendita) {
        log.debug("Adding vendita to One Piece sealed: {}", sealedId);
        webClient.post()
                .uri("/api/v1/onepiece/addvendita")
                .bodyValue(new VenditaRequest(sealedId, vendita, "SEALED"))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    // Inner class for request - aligned with AddOnePieceVenditaRequest from wiam
    public record VenditaRequest(String id, VenditaDTO vendita, String tipoProdotto) {}
}
