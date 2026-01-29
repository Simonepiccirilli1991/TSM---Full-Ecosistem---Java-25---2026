package it.tsm.wiamfrontend.dto.onepiece;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Request per aggiungere una OnePiece Card - corrisponde ad AddOnePieceCardRequest di wiam
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOnePieceCardRequestDTO {
    private String nome;
    private LocalDateTime dataInserimentoAcquisto;
    private Double prezzoAcquisto;
    private String espansione;
    private Boolean gradata;
    private String casaGradazione;
    private String votoGradazione;
    private String codiceGradazione;
    private byte[] foto;
}
