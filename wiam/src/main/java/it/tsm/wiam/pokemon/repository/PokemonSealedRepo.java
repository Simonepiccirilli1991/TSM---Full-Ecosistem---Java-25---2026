package it.tsm.wiam.pokemon.repository;


import it.tsm.wiam.pokemon.entity.PokemonSealed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonSealedRepo extends MongoRepository<PokemonSealed,String> {

    List<PokemonSealed> findByStato(String stato);

    @Query("{ 'status': ?0, 'dataInserimentoAcquisto': { $gte: ?1, $lte: ?2 } }")
    List<PokemonSealed> findByStatusAndDateRangeAcquisto(String status, String startDate, String endDate);

    @Query("{ 'stato': ?0, 'vendita.dataVendita': { $gte: ?1, $lte: ?2 } }")
    List<PokemonSealed> findByStatoAndVenditaDateRangeVendita(String stato, String startDate, String endDate);

}
