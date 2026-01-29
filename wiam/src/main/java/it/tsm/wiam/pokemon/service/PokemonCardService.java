package it.tsm.wiam.pokemon.service;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.exception.PokemonException;
import it.tsm.wiam.pokemon.model.AddPokemonCardRequest;
import it.tsm.wiam.pokemon.model.AddPokemonCardResponse;
import it.tsm.wiam.pokemon.repository.PokemonCardRepo;
import it.tsm.wiam.pokemon.util.PokemonCostants;
import it.tsm.wiam.pokemon.util.PokemonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static it.tsm.wiam.pokemon.util.PokemonCostants.Stati.DISPONIBILE;

@Service
@Slf4j
@RequiredArgsConstructor
public class PokemonCardService {

    private final PokemonCardRepo pokemonCardRepo;
    private final PokemonUtil pokemonUtil;
    private final PhotoGridFsService photoGridFsService;

    // add carta
    @Transactional
    public AddPokemonCardResponse aggiungiCartaPokemon(AddPokemonCardRequest request){
        log.info("Aggiungi Carta Pokemon service started with raw request: {}",request);
        // valido request
        request.validateRequest();
        // monto dto
        var acquistoPokemon = new PokemonCard();
        acquistoPokemon.setNome(request.nome());
        acquistoPokemon.setEspansione(request.espansione());
        acquistoPokemon.setPrezzoAcquisto(request.prezzoAcquisto());
        acquistoPokemon.setDataInserimentoAcquisto(request.dataInserimentoAcquisto().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        acquistoPokemon.setStatoAcquisto(PokemonCostants.Stati.ACQUISTATO);
        acquistoPokemon.setStato(DISPONIBILE);
        // genero id
        var cardId = pokemonUtil.createIdPokemonCard();
        // setto id
        acquistoPokemon.setId(cardId);
        // setto foto se presente
        if(!ObjectUtils.isEmpty(request.foto()))
            acquistoPokemon.setFoto(request.foto());
        //parametri in caso di carta gradata
        if(Boolean.TRUE.equals(request.gradata())){
            acquistoPokemon.setGradata(request.gradata());
            acquistoPokemon.setCasaGradazione(request.casaGradazione());
            acquistoPokemon.setCodiceGradazione(request.codiceGradazione());
            acquistoPokemon.setVotoGradazione(request.votoGradazione());
        }

        // salvo a db l'entity
        var acquistoResp = pokemonCardRepo.save(acquistoPokemon);

        var resp = new AddPokemonCardResponse("Salvato pokemon card successfully",acquistoResp);
        log.info("Aggiungi Carta Pokemon service ended successfully with response: {}",resp);
        return resp;
    }

    // cancella carta
    public void cancellaCarta(String id){
        log.info("Cancella carta service started with id: {}",id);

        var acquistoCarta = pokemonCardRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on Cancella carta service, id carta non trovato");
                    return new PokemonException("PKM-404","Errore on cancella carta, id non valido","Id carta non trovato");
                });

        pokemonCardRepo.delete(acquistoCarta);
        log.info("Cancella carta service ended successfully");
    }

    // get carta by id
    public PokemonCard getCartaById(String id){
        log.info("GetCartaById service started with id: {}",id);

        var carta = pokemonCardRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on get carta by id service, id carta non trovato");
                    return new PokemonException("PKM-404","Errore on get carta by id, id non valido","Id carta non trovato");
                });

        log.info("GetCartaById ended successfully with response: {}",carta);
        return carta;
    }

    // get carta by stato
    public List<PokemonCard> getCartaByStato(String stato){
        log.info("GetCartaByStato service started with stato: {}", stato);

        var carte = pokemonCardRepo.findByStato(stato);

        log.info("GetCartaByStato ended successfully with response size: {}", carte.size());
        return carte;
    }

    // filtering by stato (acquisto o) e range of time  su data acquisto
    public List<PokemonCard> filteringByStatoAndRangeTimeAcquistoCard(String stato, String dataInizio, String dataFine){
        log.info("Filtering Pokemon cards by stato: {} , dataInizio: {} , dataFine: {}",stato,dataInizio,dataFine);

        var carte = pokemonCardRepo.findByStatusAndDateRangeAcquisto(
                stato,
                dataInizio,
                dataFine
        );

        log.info("Filtering Pokemon cards by stato and range time ended successfully with response: {}",carte);
        return carte;
    }

    // filtering by stato (venduto o) e range of time su data vendita
    public List<PokemonCard> filteringByStatoAndRangeTimeVenditaCard(String stato, String dataInizio, String dataFine) {
        log.info("Filtering Pokemon cards by stato: {} , dataInizio: {} , dataFine: {}", stato, dataInizio, dataFine);
        var carte = pokemonCardRepo.findByStatoAndVenditaDateRangeVendita(
                stato,
                dataInizio,
                dataFine
        );
        log.info("Filtering Pokemon cards by stato and range time ended successfully with response: {}", carte);
        return carte;
    }
}
