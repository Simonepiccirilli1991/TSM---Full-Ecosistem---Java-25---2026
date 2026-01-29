package it.tsm.wiamfrontend.dto.pokemon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenditaDTO {
    private String dataVendita;
    private Double prezzoVendita;
    private Double costiVendita;
    private String prezzoNetto;
    private String piattaformaVendita;
}
