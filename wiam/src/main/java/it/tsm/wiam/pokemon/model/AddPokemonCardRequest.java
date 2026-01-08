package it.tsm.wiam.pokemon.model;

import it.tsm.wiam.pokemon.exception.PokemonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

import static it.tsm.wiam.pokemon.util.PokemonCostants.CodiciErrori.BAD_REQUEST;

@Slf4j
public record AddPokemonCardRequest(

        String nome,
        LocalDateTime dataInserimentoAcquisto,
        Double prezzoAcquisto,
        String espansione,
        Boolean gradata,
        String casaGradazione,
        String votoGradazione,
        String codiceGradazione,
        byte[] foto) {

        // metodo per validare request
        public void validateRequest(){
            if(ObjectUtils.isEmpty(nome) || ObjectUtils.isEmpty(dataInserimentoAcquisto) ||
            ObjectUtils.isEmpty(prezzoAcquisto) || ObjectUtils.isEmpty(espansione)){
                log.error("Error on validate request for add pokemon card, missing parameter");
                throw new PokemonException(BAD_REQUEST,"Missing parameter","Invalid request");
            }

            // valido i dati in caso di gradata
            if(Boolean.TRUE.equals(gradata) && ObjectUtils.isEmpty(casaGradazione)
            && ObjectUtils.isEmpty(votoGradazione) && ObjectUtils.isEmpty(codiceGradazione)){
                log.error("Error on validate request for add pokemon card gradata, missing parameter");
                throw new PokemonException(BAD_REQUEST,"Missing parameter","Invalid request for card gradata");
            }
        }

        }
