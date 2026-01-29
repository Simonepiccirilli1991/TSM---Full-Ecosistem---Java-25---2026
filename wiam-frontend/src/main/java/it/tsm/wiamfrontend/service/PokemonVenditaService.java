package it.tsm.wiamfrontend.service;

import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonVenditaService {

    private final WebClient webClient;

    public void addVenditaCard(String cardId, VenditaDTO vendita) {
        log.debug("Adding vendita to Pokemon card: {}", cardId);
        webClient.post()
                .uri("/api/v1/pokemon/addvendita")
                .bodyValue(new VenditaRequest(cardId, vendita, "Carta"))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void addVenditaSealed(String sealedId, VenditaDTO vendita) {
        log.debug("Adding vendita to Pokemon sealed: {}", sealedId);
        webClient.post()
                .uri("/api/v1/pokemon/addvendita")
                .bodyValue(new VenditaRequest(sealedId, vendita, "Sealed"))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    // Inner class for request - aligned with AddVenditaRequest from wiam
    public record VenditaRequest(String id, VenditaDTO vendita, String tipoProdotto) {}
}
