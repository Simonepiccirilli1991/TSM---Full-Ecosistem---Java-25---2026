package it.tsm.wiam.pokemon.model;

import it.tsm.wiam.pokemon.exception.PokemonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import static it.tsm.wiam.pokemon.util.PokemonCostants.TipoProdotto.CARD;
import static it.tsm.wiam.pokemon.util.PokemonCostants.TipoProdotto.SEALED;

@Slf4j
public record AddVenditaRequest(

        String id,
        Vendita vendita,
        String tipoProdotto) {


    public void validateRequest(){

        if(ObjectUtils.isEmpty(id) || ObjectUtils.isEmpty(vendita) || ObjectUtils.isEmpty(tipoProdotto)){
            log.error("Error on validateRequest AddVendita, missing parameter");
            throw new PokemonException("PKM-400","Invalid Request","Request invalida, parametri mancanti");
        }

        if(!SEALED.equals(tipoProdotto) && !CARD.equals(tipoProdotto)){
            log.info("Error on validateRequest AddVendita, tipo prodotto non valido");
            throw new PokemonException("PKM-400","Invalid Request","Request invalida, tipo prodotto non valido");
        }
    }
}
