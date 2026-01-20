package it.tsm.wiam.pokemon.repository;

import it.tsm.wiam.pokemon.entity.PokemonSealed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonSealedRepo extends MongoRepository<PokemonSealed,String> {

    List<PokemonSealed> findByStato(String stato);

}
