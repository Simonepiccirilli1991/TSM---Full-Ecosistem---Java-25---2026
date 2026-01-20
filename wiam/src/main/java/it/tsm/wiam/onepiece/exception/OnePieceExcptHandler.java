package it.tsm.wiam.onepiece.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static it.tsm.wiam.onepiece.util.OnePieceCostants.CodiciErrori.*;

@Slf4j
@RestControllerAdvice
public class OnePieceExcptHandler {

    @ExceptionHandler(OnePieceException.class)
    public ResponseEntity<OnePieceError> opExceptionHandler(OnePieceException e){

        var status = statusHandler(e.getCodice());
        var response = new OnePieceError(e.getMessaggio(),e.getErrore());

        return ResponseEntity.status(status).body(response);
    }



    private HttpStatus statusHandler(String codice){

       return switch (codice) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            case CONFLICT -> HttpStatus.CONFLICT;

            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
