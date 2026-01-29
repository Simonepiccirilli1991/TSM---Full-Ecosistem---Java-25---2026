package it.tsm.wiamfrontend.dto.onepiece;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Request per aggiungere un OnePiece Sealed - corrisponde ad AddOnePieceSealedRequest di wiam
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOnePieceSealedRequestDTO {
    private String nome;
    private LocalDateTime dataInserimentoAcquisto;
    private Double prezzoAcquisto;
    private String espansione;
    private String codiceProdotto;
    private byte[] foto;
}
