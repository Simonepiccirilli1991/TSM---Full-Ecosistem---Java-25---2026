package it.tsm.wiam.pokemon.model;

import it.tsm.wiam.pokemon.entity.PokemonCard;

public record AddPokemonCardResponse(String messaggio, PokemonCard carta) {
}
