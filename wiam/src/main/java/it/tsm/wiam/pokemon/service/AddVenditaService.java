package it.tsm.wiam.pokemon.service;


import it.tsm.wiam.pokemon.exception.PokemonException;
import it.tsm.wiam.pokemon.model.AddVenditaRequest;
import it.tsm.wiam.pokemon.model.AddVenditaResponse;
import it.tsm.wiam.pokemon.model.Vendita;
import it.tsm.wiam.pokemon.repository.PokemonCardRepo;
import it.tsm.wiam.pokemon.repository.PokemonSealedRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static it.tsm.wiam.pokemon.util.PokemonCostants.Stati.NON_DISPONIBILE;
import static it.tsm.wiam.pokemon.util.PokemonCostants.Stati.VENDUTO;
import static it.tsm.wiam.pokemon.util.PokemonCostants.TipoProdotto.CARD;
import static it.tsm.wiam.pokemon.util.PokemonCostants.TipoProdotto.SEALED;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddVenditaService {


    private final PokemonCardRepo pokemonCardRepo;
    private final PokemonSealedRepo pokemonSealedRepo;



    public AddVenditaResponse addVenditaPokemonService(AddVenditaRequest request){
        log.info("AddVenditaPokemon Service started with raw request: {}",request);

        // valido request
        request.validateRequest();

        // devo capire su che caso d'uso vado, se sul sealed o carta singola
        var resp = switch (request.tipoProdotto()) {

            case SEALED -> {
                log.info("Add vendita for sealed");
                yield venditaSealed(request.id(), request.vendita());
            }

            case CARD -> {
                log.info("Add vendita for carta");
                yield venditaCarta(request.id(), request.vendita());
            }

            default -> {
                log.error("Error on AddVenditaPokemonService , tipo prodotto non gestito");
                throw new PokemonException("PKM-500","Errore on add vendita pokemon service","Tipo prodotto non valido");
            }
        };

        //ritorno response
        log.info("AddVenditaPokemon Service ended successfully with response: {}",resp);
        return resp;
    }


    private AddVenditaResponse venditaCarta(String id, Vendita vendita){
            // devo recuperare la carta con l'id
        var acquisto = pokemonCardRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on AddVenditaPokemon, acquisto carta non trovato");
                    return new PokemonException("PKM-500","Id carta non valido","Carta acquisto non trovata");
                });
        // calcolo prezzo netto
        if(!ObjectUtils.isEmpty(vendita.getCostiVendita()) && 0.00 != vendita.getCostiVendita()){
            var prezzoNetto = vendita.getPrezzoVendita() - vendita.getCostiVendita();
            vendita.setPrezzoNetto(String.valueOf(prezzoNetto));
        } else {
            vendita.setPrezzoNetto(String.valueOf(vendita.getPrezzoVendita()));
        }
        // setto vendita su acquisto
        acquisto.setVendita(vendita);
        acquisto.setDataLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // updato lo stato
        acquisto.setStato(NON_DISPONIBILE);
        acquisto.setStatoAcquisto(VENDUTO);
        // salvo a db
        pokemonCardRepo.save(acquisto);
        // setto response
        return new AddVenditaResponse("Vendita aggiunta con successo",acquisto,null);
    }

    private AddVenditaResponse venditaSealed(String id, Vendita vendita){
        // devo recuperare la carta con l'id
        var acquisto = pokemonSealedRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on AddVenditaPokemon, acquisto sealed non trovato");
                    return new PokemonException("PKM-500","Id sealed non valido","Sealed acquisto non trovata");
                });
        // calcolo prezzo netto
        if(!ObjectUtils.isEmpty(vendita.getCostiVendita()) && 0.00 != vendita.getCostiVendita()){
            var prezzoNetto = vendita.getPrezzoVendita() - vendita.getCostiVendita();
            vendita.setPrezzoNetto(String.valueOf(prezzoNetto));
        } else {
            vendita.setPrezzoNetto(String.valueOf(vendita.getPrezzoVendita()));
        }
        // setto vendita su acquisto
        acquisto.setVendita(vendita);
        acquisto.setStato(NON_DISPONIBILE);
        acquisto.setStatoAcquisto(VENDUTO);
        acquisto.setDataLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // salvo a db
        pokemonSealedRepo.save(acquisto);
        // setto response
        return new AddVenditaResponse("Vendita aggiunta con successo",null,acquisto);
    }
}
