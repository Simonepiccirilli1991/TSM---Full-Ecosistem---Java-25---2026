package it.tsm.wiam.pokemon.exception;

import lombok.Data;

@Data
public class PokemonException extends RuntimeException{

    private String codice;
    private String errore;
    private String messaggio;

    public PokemonException(String codice, String errore, String messaggio) {
        this.codice = codice;
        this.errore = errore;
        this.messaggio = messaggio;
    }
}
