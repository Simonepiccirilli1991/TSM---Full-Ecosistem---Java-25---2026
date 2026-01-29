package it.tsm.wiamfrontend.dto.pokemon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Request per aggiungere un Pokemon Sealed - corrisponde ad AddPokemonSealedRequest di wiam
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPokemonSealedRequestDTO {
    private String nome;
    private LocalDateTime dataInserimentoAcquisto;
    private Double prezzoAcquisto;
    private String espansione;
    private String codiceProdotto;
    private byte[] foto;
}
