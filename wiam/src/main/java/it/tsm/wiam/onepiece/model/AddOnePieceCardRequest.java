package it.tsm.wiam.onepiece.model;

import it.tsm.wiam.onepiece.exception.OnePieceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

import static it.tsm.wiam.onepiece.util.OnePieceCostants.CodiciErrori.BAD_REQUEST;

@Slf4j
public record AddOnePieceCardRequest(

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
                log.error("Error on validate request for add onepiece card, missing parameter");
                throw new OnePieceException(BAD_REQUEST,"Missing parameter","Invalid request");
            }

            // valido i dati in caso di gradata
            if(Boolean.TRUE.equals(gradata) && ObjectUtils.isEmpty(casaGradazione)
            && ObjectUtils.isEmpty(votoGradazione) && ObjectUtils.isEmpty(codiceGradazione)){
                log.error("Error on validate request for add onepiece card gradata, missing parameter");
                throw new OnePieceException(BAD_REQUEST,"Missing parameter","Invalid request for card gradata");
            }
        }

        }
