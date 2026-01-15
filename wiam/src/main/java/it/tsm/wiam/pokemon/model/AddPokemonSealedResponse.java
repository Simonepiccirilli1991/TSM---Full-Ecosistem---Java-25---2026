package it.tsm.wiam.pokemon.model;

import it.tsm.wiam.pokemon.entity.PokemonSealed;

public record AddPokemonSealedResponse
        (String messaggio, PokemonSealed carta) {
}
