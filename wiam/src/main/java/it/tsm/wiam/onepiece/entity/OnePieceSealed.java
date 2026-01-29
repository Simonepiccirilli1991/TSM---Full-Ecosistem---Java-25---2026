package it.tsm.wiam.onepiece.entity;

import it.tsm.wiam.pokemon.model.Vendita;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("onepiece-sealed")
public class OnePieceSealed {

    @MongoId
    private String id;
    private String nome;
    private String dataInserimentoAcquisto;
    private String dataLastUpdate;
    private String stato; // acquistato, venduto
    private String statoAcquisto;
    private Double prezzoAcquisto;
    private String espansione;
    private String codiceProdotto;
    private byte[] foto;
    private Vendita vendita;
}
