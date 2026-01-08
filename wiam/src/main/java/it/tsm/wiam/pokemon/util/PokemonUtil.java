package it.tsm.wiam.pokemon.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class PokemonUtil {



    public String createIdPokemonSealed(){
        var cardId = "PKM-SEALED-"+ UUID.randomUUID();
        return cardId;
    }

    public String createIdPokemonCard(){
        var cardId = "PKM-CARD-"+ UUID.randomUUID();
        return cardId;
    }
}
