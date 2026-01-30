package it.tsm.wiamfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportisticaDettaglioDTO {

    private String tipoProdotto; // "Pokemon" o "OnePiece"

    // Conteggi
    private Integer totaleAcquisti;
    private Integer totaleVendite;
    private Integer totaleInVendita;

    // Importi
    private BigDecimal totaleSpeso;
    private BigDecimal totaleIncassato;
    private BigDecimal totaleCostiVendita;
    private BigDecimal profittoNetto;
}
