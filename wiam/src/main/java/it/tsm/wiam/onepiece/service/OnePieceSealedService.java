package it.tsm.wiam.onepiece.service;


import it.tsm.wiam.onepiece.entity.OnePieceCard;
import it.tsm.wiam.onepiece.entity.OnePieceSealed;
import it.tsm.wiam.onepiece.model.AddOnePieceSealedRequest;
import it.tsm.wiam.onepiece.model.AddOnePieceSealedResponse;
import it.tsm.wiam.onepiece.repository.OnePieceSealedRepo;
import it.tsm.wiam.onepiece.exception.OnePieceException;
import it.tsm.wiam.onepiece.util.OnePieceCostants;
import it.tsm.wiam.pokemon.util.PokemonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static it.tsm.wiam.onepiece.util.OnePieceCostants.Stati.ACQUISTATO;
import static it.tsm.wiam.onepiece.util.OnePieceCostants.Stati.DISPONIBILE;

@Service
@Slf4j
@RequiredArgsConstructor
public class OnePieceSealedService {


    private final OnePieceSealedRepo onePieceSealedRepo;
    private final PokemonUtil pokemonUtil;



    public AddOnePieceSealedResponse addOnePieceSealedService(AddOnePieceSealedRequest request){
        log.info("AddOnePieceSealedService started with raw request: {}",request);

        // valido request
        request.validateRequest();
        // setto entity
        var entity = new OnePieceSealed();
        var id = pokemonUtil.createIdOnePieceSealed();

        entity.setId(id);
        entity.setNome(request.nome());
        entity.setEspansione(request.espansione());
        entity.setStato(DISPONIBILE);
        entity.setStatoAcquisto(ACQUISTATO);
        entity.setCodiceProdotto(request.codiceProdotto());
        entity.setDataInserimentoAcquisto(request.dataInserimentoAcquisto().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        entity.setPrezzoAcquisto(request.prezzoAcquisto());
        // setto foto
        if(!ObjectUtils.isEmpty(request.foto()))
            entity.setFoto(request.foto());

        // salvo a db
        var acquisto = onePieceSealedRepo.save(entity);
        var resp = new AddOnePieceSealedResponse("Acquisto salvato con successo",acquisto);
        log.info("AddOnePieceSealedService ended successfulyl with response: {}",resp);
        return resp;
    }

    public void cancellaSealed(String id){
        log.info("Cancella sealed onepiece service started with id: {}",id);

        var acquistoCarta = onePieceSealedRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on Cancella sealed onepiece service, id carta non trovato");
                    return new OnePieceException("OP-404","Errore on cancella sealed onepiece, id non valido","Id sealed non trovato");
                });

        onePieceSealedRepo.delete(acquistoCarta);
        log.info("Cancella sealed onepiece service ended successfully");
    }

    // get carta by id
    public OnePieceSealed getSealedById(String id){
        log.info("GetSealedById onepiece service started with id: {}",id);

        var carta = onePieceSealedRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on get sealed by id onepiece service, id sealed non trovato");
                    return new OnePieceException("OP-404","Errore on get sealed by id onepiece, id non valido","Id sealed non trovato");
                });

        log.info("GetSealedById onepiece ended successfully with response: {}",carta);
        return carta;
    }

    // get carta by stato
    public List<OnePieceSealed> getSealedByStato(String stato){
        log.info("GetSealedByStato onepiece service started with stato: {}", stato);

        var carte = onePieceSealedRepo.findByStato(stato);

        log.info("GetSealedByStato onepiece ended successfully with response: {}", carte);
        return carte;
    }


    // filtering by stato (acquisto o) e range of time  su data acquisto
    public List<OnePieceSealed> filteringByStatoAndRangeTimeAcquistoSealed(String stato, String dataInizio, String dataFine){
        log.info("Filtering OnePiece sealed acquistate by stato: {} , dataInizio: {} , dataFine: {}",stato,dataInizio,dataFine);

        var carte = onePieceSealedRepo.findByStatusAndDateRangeAcquisto(
                stato,
                dataInizio,
                dataFine
        );

        log.info("Filtering OnePiece sealed by stato and range time ended successfully with response: {}",carte);
        return carte;
    }

    // filtering by stato (venduto o) e range of time su data vendita
    public List<OnePieceSealed> filteringByStatoAndRangeTimeVenditaSealed(String stato, String dataInizio, String dataFine) {
        log.info("Filtering OnePiece sealed vendute by stato: {} , dataInizio: {} , dataFine: {}", stato, dataInizio, dataFine);
        var carte = onePieceSealedRepo.findByStatoAndVenditaDateRangeVendita(
                stato,
                dataInizio,
                dataFine
        );
        log.info("Filtering OnePiece sealed by stato and range time ended successfully with response: {}", carte);
        return carte;
    }
}
