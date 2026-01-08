package it.tsm.wiam.pokemon.entity;

import it.tsm.wiam.pokemon.model.Vendita;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("pokemon-card")
public class PokemonCard {

    @MongoId
    private String id;
    private String nome;
    private String dataInserimentoAcquisto;
    private String dataLastUpdate;
    private String stato; // acquistato, venduto
    private Double prezzoAcquisto;
    private String espansione;
    private boolean gradata;
    private String casaGradazione;
    private String votoGradazione;
    private String codiceGradazione;
    private byte[] foto;
    private Vendita vendita;
}
