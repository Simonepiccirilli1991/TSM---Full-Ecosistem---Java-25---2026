package it.tsm.wiam.onepiece.exception;

import lombok.Data;

@Data
public class OnePieceException extends RuntimeException{

    private String codice;
    private String errore;
    private String messaggio;

    public OnePieceException(String codice, String errore, String messaggio) {
        this.codice = codice;
        this.errore = errore;
        this.messaggio = messaggio;
    }
}
