package it.tsm.wiamfrontend.dto.onepiece;

import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnePieceCardDTO {
    private String id;
    private String nome;
    private LocalDateTime dataInserimentoAcquisto;
    private String dataLastUpdate;
    private String stato;
    private Double prezzoAcquisto;
    private String espansione;
    private Boolean gradata;
    private String casaGradazione;
    private String votoGradazione;
    private String codiceGradazione;
    private byte[] foto;
    private VenditaDTO vendita;
}
