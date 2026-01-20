package it.tsm.wiam.onepiece.repository;

import it.tsm.wiam.onepiece.entity.OnePieceCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnePieceCardRepo extends MongoRepository<OnePieceCard,String> {

    List<OnePieceCard> findByStato(String stato);

}
