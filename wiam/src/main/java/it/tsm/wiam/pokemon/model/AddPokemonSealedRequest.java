package it.tsm.wiam.pokemon.model;

import it.tsm.wiam.pokemon.exception.PokemonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

import static it.tsm.wiam.pokemon.util.PokemonCostants.CodiciErrori.BAD_REQUEST;

@Slf4j
public record AddPokemonSealedRequest(

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
            log.error("Error on validate request for add pokemon sealed, missing parameter");
            throw new PokemonException(BAD_REQUEST, "Missing parameter", "Invalid request");
        }
    }
}
