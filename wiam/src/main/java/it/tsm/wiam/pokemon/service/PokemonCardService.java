package it.tsm.wiam.pokemon.service;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.model.AddPokemonCardRequest;
import it.tsm.wiam.pokemon.model.AddPokemonCardResponse;
import it.tsm.wiam.pokemon.repository.PokemonCardRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class PokemonCardService {

    private final PokemonCardRepo pokemonCardRepo;



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
        acquistoPokemon.setStato("ACQUISTATO");
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
}
