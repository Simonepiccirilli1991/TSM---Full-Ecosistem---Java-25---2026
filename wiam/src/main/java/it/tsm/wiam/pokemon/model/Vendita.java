package it.tsm.wiam.pokemon.model;

import lombok.Data;

@Data
public class Vendita {


    private String dataVendita;
    private Double prezzoVendita;
    private Double costiVendita;
    private String prezzoNetto;
    private String piattaformaVendita;
}
