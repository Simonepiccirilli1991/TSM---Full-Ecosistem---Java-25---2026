package it.tsm.wiamfrontend.service;

import it.tsm.wiamfrontend.dto.pokemon.AddPokemonCardRequestDTO;
import it.tsm.wiamfrontend.dto.pokemon.PokemonCardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonCardService {

    private final WebClient webClient;

    public List<PokemonCardDTO> getAllCards() {
        log.debug("Fetching all Pokemon cards - DISPONIBILE");
        return webClient.get()
                .uri("/api/v1/pokemon/getcardsbystatus/DISPONIBILE")
                .retrieve()
                .bodyToFlux(PokemonCardDTO.class)
                .collectList()
                .block();
    }

    public PokemonCardDTO getCardById(String id) {
        log.debug("Fetching Pokemon card with id: {}", id);
        return webClient.get()
                .uri("/api/v1/pokemon/getcard/{id}", id)
                .retrieve()
                .bodyToMono(PokemonCardDTO.class)
                .block();
    }

    public PokemonCardDTO createCard(PokemonCardDTO card) {
        log.debug("Creating new Pokemon card: {}", card.getNome());

        // Crea il DTO request con solo i campi necessari
        AddPokemonCardRequestDTO request = new AddPokemonCardRequestDTO(
            card.getNome(),
            card.getDataInserimentoAcquisto(),
            card.getPrezzoAcquisto(),
            card.getEspansione(),
            card.getGradata(),
            card.getCasaGradazione(),
            card.getVotoGradazione(),
            card.getCodiceGradazione(),
            card.getFoto()
        );

        return webClient.post()
                .uri("/api/v1/pokemon/addcard")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PokemonCardDTO.class)
                .block();
    }

    public PokemonCardDTO updateCard(String id, PokemonCardDTO card) {
        log.debug("Updating Pokemon card with id: {}", id);

        // Crea il DTO request con solo i campi necessari
        AddPokemonCardRequestDTO request = new AddPokemonCardRequestDTO(
            card.getNome(),
            card.getDataInserimentoAcquisto(),
            card.getPrezzoAcquisto(),
            card.getEspansione(),
            card.getGradata(),
            card.getCasaGradazione(),
            card.getVotoGradazione(),
            card.getCodiceGradazione(),
            card.getFoto()
        );

        return webClient.post()
                .uri("/api/v1/pokemon/addcard")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PokemonCardDTO.class)
                .block();
    }

    public void deleteCard(String id) {
        log.debug("Deleting Pokemon card with id: {}", id);
        webClient.delete()
                .uri("/api/v1/pokemon/deletecard/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
