package it.tsm.wiam.onepiece.service;

import it.tsm.wiam.onepiece.entity.OnePieceCard;
import it.tsm.wiam.onepiece.model.AddOnePieceCardRequest;
import it.tsm.wiam.onepiece.model.AddOnePieceCardResponse;
import it.tsm.wiam.onepiece.repository.OnePieceCardRepo;
import it.tsm.wiam.onepiece.exception.OnePieceException;
import it.tsm.wiam.onepiece.util.OnePieceCostants;
import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.util.PokemonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static it.tsm.wiam.onepiece.util.OnePieceCostants.Stati.DISPONIBILE;

@Service
@Slf4j
@RequiredArgsConstructor
public class OnePieceCardService {

    private final OnePieceCardRepo onePieceCardRepo;
    private final PokemonUtil pokemonUtil;

    // add carta
    @Transactional
    public AddOnePieceCardResponse aggiungiCartaOnePiece(AddOnePieceCardRequest request){
        log.info("Aggiungi Carta OnePiece service started with raw request: {}",request);
        // valido request
        request.validateRequest();
        // monto dto
        var acquisto = new OnePieceCard();
        acquisto.setNome(request.nome());
        acquisto.setEspansione(request.espansione());
        acquisto.setPrezzoAcquisto(request.prezzoAcquisto());
        acquisto.setDataInserimentoAcquisto(request.dataInserimentoAcquisto().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        acquisto.setStatoAcquisto(OnePieceCostants.Stati.ACQUISTATO);
        acquisto.setStato(DISPONIBILE);
        // genero id
        var cardId = pokemonUtil.createIdOnePieceCard();
        // setto id
        acquisto.setId(cardId);
        // setto foto se presente
        if(!ObjectUtils.isEmpty(request.foto()))
            acquisto.setFoto(request.foto());
        //parametri in caso di carta gradata
        if(Boolean.TRUE.equals(request.gradata())){
            acquisto.setGradata(request.gradata());
            acquisto.setCasaGradazione(request.casaGradazione());
            acquisto.setCodiceGradazione(request.codiceGradazione());
            acquisto.setVotoGradazione(request.votoGradazione());
        }

        // salvo a db l'entity
        var acquistoResp = onePieceCardRepo.save(acquisto);

        var resp = new AddOnePieceCardResponse("Salvato onepiece card successfully",acquistoResp);
        log.info("Aggiungi Carta OnePiece service ended successfully with response: {}",resp);
        return resp;
    }

    // cancella carta
    public void cancellaCarta(String id){
        log.info("Cancella carta onepiece service started with id: {}",id);

        var acquistoCarta = onePieceCardRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on Cancella carta onepiece service, id carta non trovato");
                    return new OnePieceException("OP-404","Errore on cancella carta onepiece, id non valido","Id carta non trovato");
                });

        onePieceCardRepo.delete(acquistoCarta);
        log.info("Cancella carta onepiece service ended successfully");
    }

    // get carta by id
    public OnePieceCard getCartaById(String id){
        log.info("GetCartaById onepiece service started with id: {}",id);

        var carta = onePieceCardRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on get carta by id onepiece service, id carta non trovato");
                    return new OnePieceException("OP-404","Errore on get carta by id onepiece, id non valido","Id carta non trovato");
                });

        log.info("GetCartaById onepiece ended successfully with response: {}",carta);
        return carta;
    }

    // get carta by stato
    public List<OnePieceCard> getCartaByStato(String stato){
        log.info("GetCartaByStato onepiece service started with stato: {}", stato);

        var carte = onePieceCardRepo.findByStato(stato);

        log.info("GetCartaByStato onepiece ended successfully with response size: {}", carte.size());
        return carte;
    }

    // filtering by stato (acquisto o) e range of time  su data acquisto
    public List<OnePieceCard> filteringByStatoAndRangeTimeAcquistoCard(String stato, String dataInizio, String dataFine){
        log.info("Filtering OnePiece cards acquistate by stato: {} , dataInizio: {} , dataFine: {}",stato,dataInizio,dataFine);

        var carte = onePieceCardRepo.findByStatusAndDateRangeAcquisto(
                stato,
                dataInizio,
                dataFine
        );

        log.info("Filtering OnePiece cards by stato and range time ended successfully with response: {}",carte);
        return carte;
    }

    // filtering by stato (venduto o) e range of time su data vendita
    public List<OnePieceCard> filteringByStatoAndRangeTimeVenditaCard(String stato, String dataInizio, String dataFine) {
        log.info("Filtering OnePiece cards vendute by stato: {} , dataInizio: {} , dataFine: {}", stato, dataInizio, dataFine);
        var carte = onePieceCardRepo.findByStatoAndVenditaDateRangeVendita(
                stato,
                dataInizio,
                dataFine
        );
        log.info("Filtering OnePiece cards by stato and range time ended successfully with response: {}", carte);
        return carte;
    }
}
