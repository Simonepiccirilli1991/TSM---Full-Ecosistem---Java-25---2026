package it.tsm.wiam.onepiece.model;

import it.tsm.wiam.onepiece.exception.OnePieceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import it.tsm.wiam.pokemon.model.Vendita;

import static it.tsm.wiam.onepiece.util.OnePieceCostants.TipoProdotto.CARD;
import static it.tsm.wiam.onepiece.util.OnePieceCostants.TipoProdotto.SEALED;

@Slf4j
public record AddOnePieceVenditaRequest(

        String id,
        Vendita vendita,
        String tipoProdotto) {


    public void validateRequest(){

        if(ObjectUtils.isEmpty(id) || ObjectUtils.isEmpty(vendita) || ObjectUtils.isEmpty(tipoProdotto)){
            log.error("Error on validateRequest AddOnePieceVendita, missing parameter");
            throw new OnePieceException("OP-400","Invalid Request","Request invalida, parametri mancanti");
        }

        if(!SEALED.equals(tipoProdotto) && !CARD.equals(tipoProdotto)){
            log.info("Error on validateRequest AddOnePieceVendita, tipo prodotto non valido");
            throw new OnePieceException("OP-400","Invalid Request","Request invalida, tipo prodotto non valido");
        }
    }
}
