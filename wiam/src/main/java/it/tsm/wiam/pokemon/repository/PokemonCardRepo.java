package it.tsm.wiam.pokemon.repository;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonCardRepo extends MongoRepository<PokemonCard,String> {

    List<PokemonCard> findByStato(String stato);

}
