package it.tsm.wiam.pokemon.repository;

import it.tsm.wiam.pokemon.entity.PokemonSealed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonSealedRepo extends MongoRepository<PokemonSealed,String> {
}
