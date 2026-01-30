package it.tsm.wiam.pokemon.service;


import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.entity.PokemonSealed;
import it.tsm.wiam.pokemon.exception.PokemonException;
import it.tsm.wiam.pokemon.model.AddPokemonCardResponse;
import it.tsm.wiam.pokemon.model.AddPokemonSealedRequest;
import it.tsm.wiam.pokemon.model.AddPokemonSealedResponse;
import it.tsm.wiam.pokemon.repository.PokemonCardRepo;
import it.tsm.wiam.pokemon.repository.PokemonSealedRepo;
import it.tsm.wiam.pokemon.util.PokemonCostants;
import it.tsm.wiam.pokemon.util.PokemonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static it.tsm.wiam.pokemon.util.PokemonCostants.Stati.DISPONIBILE;

@Service
@Slf4j
@RequiredArgsConstructor
public class PokemonSealedService {


    private final PokemonSealedRepo pokemonSealedRepo;
    private final PokemonUtil pokemonUtil;



    public AddPokemonSealedResponse addPokemonSealedService(AddPokemonSealedRequest request){
        log.info("AddPokemonSealedService started with raw request: {}",request);

        // valido request
        request.validateRequest();
        // setto entity
        var entity = new PokemonSealed();
        var id = pokemonUtil.createIdPokemonSealed();

        entity.setId(id);
        entity.setNome(request.nome());
        entity.setEspansione(request.espansione());
        entity.setStatoAcquisto(PokemonCostants.Stati.ACQUISTATO);
        entity.setStato(DISPONIBILE);
        entity.setCodiceProdotto(request.codiceProdotto());
        entity.setDataInserimentoAcquisto(request.dataInserimentoAcquisto().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        entity.setPrezzoAcquisto(request.prezzoAcquisto());
        // setto foto
        if(!ObjectUtils.isEmpty(request.foto()))
            entity.setFoto(request.foto());

        // salvo a db
        var acquisto = pokemonSealedRepo.save(entity);
        var resp = new AddPokemonSealedResponse("Acquisto salvato con successo",acquisto);
        log.info("AddPokemonSealedService ended successfulyl with response: {}",resp);
        return resp;
    }

    public void cancellaSealed(String id){
        log.info("Cancella sealed service started with id: {}",id);

        var acquistoCarta = pokemonSealedRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on Cancella sealed service, id carta non trovato");
                    return new PokemonException("PKM-404","Errore on cancella sealed, id non valido","Id sealed non trovato");
                });

        pokemonSealedRepo.delete(acquistoCarta);
        log.info("Cancella sealed service ended successfully");
    }

    // get carta by id
    public PokemonSealed getSealedById(String id){
        log.info("GetCartaById service started with id: {}",id);

        var carta = pokemonSealedRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on get sealed by id service, id sealed non trovato");
                    return new PokemonException("PKM-404","Errore on get sealed by id, id non valido","Id sealed non trovato");
                });

        log.info("GetSealedById ended successfully with response: {}",carta);
        return carta;
    }

    // get sealed by stato
    public List<PokemonSealed> getSealedByStato(String stato){
        log.info("GetSealedByStato service started with stato: {}", stato);

        var carte = pokemonSealedRepo.findByStato(stato);

        log.info("GetSealedByStato ended successfully with response: {}", carte);
        return carte;
    }
    // get sealed find all
    public List<PokemonSealed> findAllSealed(){
        log.info("GetSealedFindAll service started with findall");

        var carte = pokemonSealedRepo.findAll();

        log.info("GetSealedFindAll ended successfully with response: {}", carte);
        return carte;
    }

    // filtering by stato (acquisto o) e range of time  su data acquisto
    public List<PokemonSealed> filteringByStatoAndRangeTimeAcquistoSealed(String stato, String dataInizio, String dataFine){
        log.info("Filtering Pokemon cards by stato: {} , dataInizio: {} , dataFine: {}",stato,dataInizio,dataFine);

        var carte = pokemonSealedRepo.findByStatusAndDateRangeAcquisto(
                stato,
                dataInizio,
                dataFine
        );

        log.info("Filtering Pokemon cards by stato and range time ended successfully with response: {}",carte);
        return carte;
    }

    // filtering by stato (venduto o) e range of time su data vendita
    public List<PokemonSealed> filteringByStatoAndRangeTimeVenditaSealed(String stato, String dataInizio, String dataFine) {
        log.info("Filtering Pokemon sealed by stato: {} , dataInizio: {} , dataFine: {}", stato, dataInizio, dataFine);
        var carte = pokemonSealedRepo.findByStatoAndVenditaDateRangeVendita(
                stato,
                dataInizio,
                dataFine
        );
        log.info("Filtering Pokemon sealed by stato and range time ended successfully with response: {}", carte);
        return carte;
    }
}
