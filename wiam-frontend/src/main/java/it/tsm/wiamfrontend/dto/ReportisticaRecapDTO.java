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
public class ReportisticaRecapDTO {

    // Conteggi
    private Integer totaleAcquisti;
    private Integer totaleVendite;
    private Integer totaleInVendita;

    // Importi acquisti
    private BigDecimal totaleSpeso;
    private BigDecimal costoMedioAcquisto;

    // Importi vendite
    private BigDecimal totaleIncassato;
    private BigDecimal totaleCostiVendita;
    private BigDecimal ricavoNettoVendite;
    private BigDecimal prezzoMedioVendita;

    // Profitti
    private BigDecimal profittoNetto;
    private BigDecimal marginePercentuale;

    // Dettagli per tipo prodotto
    private ReportisticaDettaglioDTO pokemon;
    private ReportisticaDettaglioDTO onePiece;
}
