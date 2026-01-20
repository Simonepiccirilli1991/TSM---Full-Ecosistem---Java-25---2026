package it.tsm.wiam.onepiece.repository;

import it.tsm.wiam.onepiece.entity.OnePieceSealed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnePieceSealedRepo extends MongoRepository<OnePieceSealed,String> {

    List<OnePieceSealed> findByStato(String stato);

    @Query("{ 'status': ?0, 'dataInserimentoAcquisto': { $gte: ?1, $lte: ?2 } }")
    List<OnePieceSealed> findByStatusAndDateRangeAcquisto(String status, String startDate, String endDate);

    @Query("{ 'stato': ?0, 'vendita.dataVendita': { $gte: ?1, $lte: ?2 } }")
    List<OnePieceSealed> findByStatoAndVenditaDateRangeVendita(String stato, String startDate, String endDate);

}
