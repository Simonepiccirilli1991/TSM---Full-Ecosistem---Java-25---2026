package it.tsm.wiamfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportItemDTO {

    private String id;
    private String nome;
    private String dataInserimentoAcquisto;
    private String dataLastUpdate;
    private String stato; // disponibile / non disponibile (stato del prodotto)
    private String statoAcquisto; // venduto / acquistato (stato dell'acquisto)
    private Double prezzoAcquisto;
    private String espansione;
    private boolean gradata;
    private String casaGradazione;
    private String votoGradazione;
    private String codiceGradazione;
    private byte[] foto;
    private Map<String, Object> vendita; // Map per gestire il JSON dinamico
    private String codiceProdotto;
    private String tipoProdotto; // One piece o pokemon
}
