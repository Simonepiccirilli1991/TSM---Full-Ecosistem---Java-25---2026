package it.tsm.wiam.pokemon.model;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.entity.PokemonSealed;

public record AddVenditaResponse(

        String messaggio,
        PokemonCard cartaPokemon,
        PokemonSealed pokemonSealed
) {
}
