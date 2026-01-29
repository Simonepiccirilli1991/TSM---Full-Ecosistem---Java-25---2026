package it.tsm.wiamfrontend.dto.reportistica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Integer totaleAcquisti;
    private Integer totaleVendite;
    private BigDecimal totaleSpeso;
    private BigDecimal totaleIncassato;
    private BigDecimal profittoNetto;
    private List<Object> dettagli;
}
