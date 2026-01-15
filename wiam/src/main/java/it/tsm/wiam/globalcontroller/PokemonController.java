package it.tsm.wiam.globalcontroller;

import it.tsm.wiam.pokemon.model.*;
import it.tsm.wiam.pokemon.service.AddVenditaService;
import it.tsm.wiam.pokemon.service.PokemonCardService;
import it.tsm.wiam.pokemon.service.PokemonSealedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pokemon")
public class PokemonController {


    private final PokemonCardService pokemonCardService;
    private final PokemonSealedService pokemonSealedService;
    private final AddVenditaService addVenditaService;


    @PostMapping("addcard")
    public ResponseEntity<AddPokemonCardResponse> addPokemonCard(@RequestBody AddPokemonCardRequest request){
        return ResponseEntity.ok(pokemonCardService.aggiungiCartaPokemon(request));
    }

    @PostMapping("addsealed")
    public ResponseEntity<AddPokemonSealedResponse> addPokemonSealed(@RequestBody AddPokemonSealedRequest request){
        return ResponseEntity.ok(pokemonSealedService.addPokemonSealedService(request));
    }

    @PostMapping("addvendita")
    public ResponseEntity<AddVenditaResponse> addVenditaPokemon(@RequestBody AddVenditaRequest request){
        return ResponseEntity.ok(addVenditaService.addVenditaPokemonService(request));
    }



}
