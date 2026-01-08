package it.tsm.wiam.pokemon.repository;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonCardRepo extends MongoRepository<PokemonCard,String> {
}
