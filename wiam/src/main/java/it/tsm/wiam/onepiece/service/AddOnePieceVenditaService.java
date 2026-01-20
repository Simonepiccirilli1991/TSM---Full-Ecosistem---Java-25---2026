package it.tsm.wiam.onepiece.service;


import it.tsm.wiam.onepiece.model.AddOnePieceVenditaRequest;
import it.tsm.wiam.onepiece.model.AddOnePieceVenditaResponse;
import it.tsm.wiam.onepiece.repository.OnePieceCardRepo;
import it.tsm.wiam.onepiece.repository.OnePieceSealedRepo;
import it.tsm.wiam.onepiece.exception.OnePieceException;
import it.tsm.wiam.pokemon.model.Vendita;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static it.tsm.wiam.onepiece.util.OnePieceCostants.TipoProdotto.CARD;
import static it.tsm.wiam.onepiece.util.OnePieceCostants.TipoProdotto.SEALED;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddOnePieceVenditaService {


    private final OnePieceCardRepo onePieceCardRepo;
    private final OnePieceSealedRepo onePieceSealedRepo;



    public AddOnePieceVenditaResponse addVenditaOnePieceService(AddOnePieceVenditaRequest request){
        log.info("AddVenditaOnePiece Service started with raw request: {}",request);

        // valido request
        request.validateRequest();

        // devo capire su che caso d'uso vado, se sul sealed o carta singola
        var resp = switch (request.tipoProdotto()) {

            case SEALED -> {
                log.info("Add vendita for carta onepiece");
                yield venditaCarta(request.id(), request.vendita());
            }

            case CARD -> {
                log.info("Add vendita for sealed onepiece");
                yield venditaSealed(request.id(), request.vendita());
            }

            default -> {
                log.error("Error on AddVenditaOnePieceService , tipo prodotto non gestito");
                throw new OnePieceException("OP-500","Errore on add vendita onepiece service","Tipo prodotto non valido");
            }
        };

        //ritorno response
        log.info("AddVenditaOnePiece Service ended successfully with response: {}",resp);
        return resp;
    }


    private AddOnePieceVenditaResponse venditaCarta(String id, Vendita vendita){
            // devo recuperare la carta con l'id
        var acquisto = onePieceCardRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on AddVenditaOnePiece, acquisto carta non trovato");
                    return new OnePieceException("OP-500","Id carta non valido","Carta acquisto non trovata");
                });

        // setto vendita su acquisto
        acquisto.setVendita(vendita);
        acquisto.setDataLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // setto stato a venduto
        acquisto.setStato("VENDUTO");
        // salvo a db
        onePieceCardRepo.save(acquisto);
        // setto response
        return new AddOnePieceVenditaResponse("Vendita aggiunta con successo",acquisto,null);
    }

    private AddOnePieceVenditaResponse venditaSealed(String id, Vendita vendita){
        // devo recuperare la carta con l'id
        var acquisto = onePieceSealedRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on AddVenditaOnePiece, acquisto sealed non trovato");
                    return new OnePieceException("OP-500","Id sealed non valido","Sealed acquisto non trovata");
                });

        // setto vendita su acquisto
        acquisto.setVendita(vendita);
        acquisto.setDataLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // setto stato a venduto
        acquisto.setStato("VENDUTO");
        // salvo a db
        onePieceSealedRepo.save(acquisto);
        // setto response
        return new AddOnePieceVenditaResponse("Vendita aggiunta con successo",null,acquisto);
    }
}
