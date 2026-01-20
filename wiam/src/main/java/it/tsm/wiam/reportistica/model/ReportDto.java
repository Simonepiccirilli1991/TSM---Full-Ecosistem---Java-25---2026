package it.tsm.wiam.reportistica.model;

import it.tsm.wiam.pokemon.model.Vendita;
import lombok.Data;

@Data
public class ReportDto {

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
    private String codiceProdotto;
    private String tipoProdotto; // One piece o pokemon
}
