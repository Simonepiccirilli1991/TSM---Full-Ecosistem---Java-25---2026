package it.tsm.wiam.onepiece.model;

import it.tsm.wiam.onepiece.exception.OnePieceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

import static it.tsm.wiam.onepiece.util.OnePieceCostants.CodiciErrori.BAD_REQUEST;

@Slf4j
public record AddOnePieceSealedRequest(

        String nome,
        LocalDateTime dataInserimentoAcquisto,
        Double prezzoAcquisto,
        String espansione,
        String codiceProdotto,
        byte[] foto) {

    // metodo per validare request
    public void validateRequest() {
        if (ObjectUtils.isEmpty(nome) || ObjectUtils.isEmpty(dataInserimentoAcquisto) ||
                ObjectUtils.isEmpty(prezzoAcquisto) || ObjectUtils.isEmpty(espansione) || ObjectUtils.isEmpty(codiceProdotto)) {
            log.error("Error on validate request for add onepiece sealed, missing parameter");
            throw new OnePieceException(BAD_REQUEST, "Missing parameter", "Invalid request");
        }
    }
}
