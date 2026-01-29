package it.tsm.wiamfrontend.service;

import it.tsm.wiamfrontend.dto.onepiece.AddOnePieceCardRequestDTO;
import it.tsm.wiamfrontend.dto.onepiece.OnePieceCardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OnePieceCardService {

    private final WebClient webClient;

    public List<OnePieceCardDTO> getAllCards() {
        log.debug("Fetching all One Piece cards - DISPONIBILE");
        return webClient.get()
                .uri("/api/v1/onepiece/getcardsbystatus/DISPONIBILE")
                .retrieve()
                .bodyToFlux(OnePieceCardDTO.class)
                .collectList()
                .block();
    }

    public OnePieceCardDTO getCardById(String id) {
        log.debug("Fetching One Piece card with id: {}", id);
        return webClient.get()
                .uri("/api/v1/onepiece/getcard/{id}", id)
                .retrieve()
                .bodyToMono(OnePieceCardDTO.class)
                .block();
    }

    public OnePieceCardDTO createCard(OnePieceCardDTO card) {
        log.debug("Creating new One Piece card: {}", card.getNome());

        // Crea il DTO request con solo i campi necessari
        AddOnePieceCardRequestDTO request = new AddOnePieceCardRequestDTO(
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
                .uri("/api/v1/onepiece/addcard")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OnePieceCardDTO.class)
                .block();
    }

    public OnePieceCardDTO updateCard(String id, OnePieceCardDTO card) {
        log.debug("Updating One Piece card with id: {}", id);

        // Crea il DTO request con solo i campi necessari
        AddOnePieceCardRequestDTO request = new AddOnePieceCardRequestDTO(
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
                .uri("/api/v1/onepiece/addcard")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OnePieceCardDTO.class)
                .block();
    }

    public void deleteCard(String id) {
        log.debug("Deleting One Piece card with id: {}", id);
        webClient.delete()
                .uri("/api/v1/onepiece/deletecard/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
