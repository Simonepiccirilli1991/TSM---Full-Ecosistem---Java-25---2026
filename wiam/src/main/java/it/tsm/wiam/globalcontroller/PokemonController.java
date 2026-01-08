package it.tsm.wiam.globalcontroller;

import it.tsm.wiam.pokemon.model.AddPokemonCardRequest;
import it.tsm.wiam.pokemon.model.AddPokemonCardResponse;
import it.tsm.wiam.pokemon.service.PokemonCardService;
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



    @PostMapping("addcard")
    public ResponseEntity<AddPokemonCardResponse> addPokemonCard(@RequestBody AddPokemonCardRequest request){
        return ResponseEntity.ok(pokemonCardService.aggiungiCartaPokemon(request));
    }


}
