package it.tsm.wiamfrontend.service;

import it.tsm.wiamfrontend.dto.pokemon.AddPokemonSealedRequestDTO;
import it.tsm.wiamfrontend.dto.pokemon.PokemonSealedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonSealedService {

    private final WebClient webClient;

    public List<PokemonSealedDTO> getAllSealed() {
        log.debug("Fetching all Pokemon sealed - DISPONIBILE");
        return webClient.get()
                .uri("/api/v1/pokemon/getsealedbystatus/DISPONIBILE")
                .retrieve()
                .bodyToFlux(PokemonSealedDTO.class)
                .collectList()
                .block();
    }

    public PokemonSealedDTO getSealedById(String id) {
        log.debug("Fetching Pokemon sealed with id: {}", id);
        return webClient.get()
                .uri("/api/v1/pokemon/getsealed/{id}", id)
                .retrieve()
                .bodyToMono(PokemonSealedDTO.class)
                .block();
    }

    public PokemonSealedDTO createSealed(PokemonSealedDTO sealed) {
        log.debug("Creating new Pokemon sealed: {}", sealed.getNome());

        // Crea il DTO request con solo i campi necessari
        AddPokemonSealedRequestDTO request = new AddPokemonSealedRequestDTO(
            sealed.getNome(),
            sealed.getDataInserimentoAcquisto(),
            sealed.getPrezzoAcquisto(),
            sealed.getEspansione(),
            sealed.getCodiceProdotto(),
            sealed.getFoto()
        );

        return webClient.post()
                .uri("/api/v1/pokemon/addsealed")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PokemonSealedDTO.class)
                .block();
    }

    public PokemonSealedDTO updateSealed(String id, PokemonSealedDTO sealed) {
        log.debug("Updating Pokemon sealed with id: {}", id);

        // Crea il DTO request con solo i campi necessari
        AddPokemonSealedRequestDTO request = new AddPokemonSealedRequestDTO(
            sealed.getNome(),
            sealed.getDataInserimentoAcquisto(),
            sealed.getPrezzoAcquisto(),
            sealed.getEspansione(),
            sealed.getCodiceProdotto(),
            sealed.getFoto()
        );

        return webClient.post()
                .uri("/api/v1/pokemon/addsealed")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PokemonSealedDTO.class)
                .block();
    }

    public void deleteSealed(String id) {
        log.debug("Deleting Pokemon sealed with id: {}", id);
        webClient.delete()
                .uri("/api/v1/pokemon/deletesealed/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
