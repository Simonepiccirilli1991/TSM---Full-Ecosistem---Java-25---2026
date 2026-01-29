package it.tsm.wiamfrontend.service;

import it.tsm.wiamfrontend.dto.onepiece.AddOnePieceSealedRequestDTO;
import it.tsm.wiamfrontend.dto.onepiece.OnePieceSealedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnePieceSealedService {

    private final WebClient webClient;

    public List<OnePieceSealedDTO> getAllSealed() {
        log.debug("Fetching all One Piece sealed - DISPONIBILE");
        return webClient.get()
                .uri("/api/v1/onepiece/getsealedbystatus/DISPONIBILE")
                .retrieve()
                .bodyToFlux(OnePieceSealedDTO.class)
                .collectList()
                .block();
    }

    public OnePieceSealedDTO getSealedById(String id) {
        log.debug("Fetching One Piece sealed with id: {}", id);
        return webClient.get()
                .uri("/api/v1/onepiece/getsealed/{id}", id)
                .retrieve()
                .bodyToMono(OnePieceSealedDTO.class)
                .block();
    }

    public OnePieceSealedDTO createSealed(OnePieceSealedDTO sealed) {
        log.debug("Creating new One Piece sealed: {}", sealed.getNome());

        // Crea il DTO request con solo i campi necessari
        AddOnePieceSealedRequestDTO request = new AddOnePieceSealedRequestDTO(
            sealed.getNome(),
            sealed.getDataInserimentoAcquisto(),
            sealed.getPrezzoAcquisto(),
            sealed.getEspansione(),
            sealed.getCodiceProdotto(),
            sealed.getFoto()
        );

        return webClient.post()
                .uri("/api/v1/onepiece/addsealed")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OnePieceSealedDTO.class)
                .block();
    }

    public OnePieceSealedDTO updateSealed(String id, OnePieceSealedDTO sealed) {
        log.debug("Updating One Piece sealed with id: {}", id);

        // Crea il DTO request con solo i campi necessari
        AddOnePieceSealedRequestDTO request = new AddOnePieceSealedRequestDTO(
            sealed.getNome(),
            sealed.getDataInserimentoAcquisto(),
            sealed.getPrezzoAcquisto(),
            sealed.getEspansione(),
            sealed.getCodiceProdotto(),
            sealed.getFoto()
        );

        return webClient.post()
                .uri("/api/v1/onepiece/addsealed")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OnePieceSealedDTO.class)
                .block();
    }

    public void deleteSealed(String id) {
        log.debug("Deleting One Piece sealed with id: {}", id);
        webClient.delete()
                .uri("/api/v1/onepiece/deletesealed/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
