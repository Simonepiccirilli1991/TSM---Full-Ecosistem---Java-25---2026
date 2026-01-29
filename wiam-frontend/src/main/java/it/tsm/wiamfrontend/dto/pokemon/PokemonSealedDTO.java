package it.tsm.wiamfrontend.dto.pokemon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonSealedDTO {
    private String id;
    private String nome;
    private LocalDateTime dataInserimentoAcquisto;
    private String dataLastUpdate;
    private String stato;
    private Double prezzoAcquisto;
    private String espansione;
    private String codiceProdotto;
    private byte[] foto;
    private VenditaDTO vendita;
}
