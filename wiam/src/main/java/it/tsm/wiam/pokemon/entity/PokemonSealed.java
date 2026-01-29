package it.tsm.wiam.pokemon.entity;

import it.tsm.wiam.pokemon.model.Vendita;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("pokemon-sealed")
public class PokemonSealed {

    @MongoId
    private String id;
    private String nome;
    private String dataInserimentoAcquisto;
    private String dataLastUpdate;
    private String statoAcquisto; // acquistato, venduto
    private Double prezzoAcquisto;
    private String espansione;
    private String codiceProdotto;
    private byte[] foto;
    private Vendita vendita;
    private String stato; // DISPONIBILE, VENDUTO
}
