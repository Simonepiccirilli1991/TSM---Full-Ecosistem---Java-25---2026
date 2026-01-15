package it.tsm.wiam.pokemon.service;


import it.tsm.wiam.pokemon.entity.PokemonSealed;
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
        entity.setStato(PokemonCostants.Stati.ACQUISTATO);
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
}
