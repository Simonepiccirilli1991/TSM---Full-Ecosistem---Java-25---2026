package it.tsm.wiam.onepiece.repository;

import it.tsm.wiam.onepiece.entity.OnePieceSealed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnePieceSealedRepo extends MongoRepository<OnePieceSealed,String> {

    List<OnePieceSealed> findByStato(String stato);

}
