package it.tsm.wiam.globalcontroller;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.entity.PokemonSealed;
import it.tsm.wiam.pokemon.model.*;
import it.tsm.wiam.pokemon.service.AddVenditaService;
import it.tsm.wiam.pokemon.service.PokemonCardService;
import it.tsm.wiam.pokemon.service.PokemonSealedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pokemon")
public class PokemonController {


    private final PokemonCardService pokemonCardService;
    private final PokemonSealedService pokemonSealedService;
    private final AddVenditaService addVenditaService;

    // add carta
    @PostMapping("addcard")
    public ResponseEntity<AddPokemonCardResponse> addPokemonCard(@RequestBody AddPokemonCardRequest request){
        return ResponseEntity.ok(pokemonCardService.aggiungiCartaPokemon(request));
    }
    // delete carta
    @DeleteMapping("deletecard/{id}")
    public ResponseEntity<Void> cancellaCarta(@PathVariable ("id") String id){
        pokemonCardService.cancellaCarta(id);
        return ResponseEntity.ok(null);
    }

    // get carta by id
    @GetMapping("getcard/{id}")
    public ResponseEntity<PokemonCard> getPokemonCardById(@PathVariable ("id") String id) {
        return ResponseEntity.ok(pokemonCardService.getCartaById(id));
    }

    // get carta by stato
    @GetMapping("getcardsbystatus/{status}")
    public ResponseEntity<List<PokemonCard>> getPokemonCardsByStatus(@PathVariable ("status") String status) {
        return ResponseEntity.ok(pokemonCardService.getCartaByStato(status));
    }

    // PRODOTTI SEALED

    // add prodotto sealed
    @PostMapping("addsealed")
    public ResponseEntity<AddPokemonSealedResponse> addPokemonSealed(@RequestBody AddPokemonSealedRequest request){
        return ResponseEntity.ok(pokemonSealedService.addPokemonSealedService(request));
    }
    // delete sealed
    @DeleteMapping("deletesealed/{id}")
    public ResponseEntity<Void> cancellaSealed(@PathVariable ("id") String id){
        pokemonSealedService.cancellaSealed(id);
        return ResponseEntity.ok(null);
    }

    // get sealed by id
    @GetMapping("getsealed/{id}")
    public ResponseEntity<PokemonSealed> getPokemonSealedById(@PathVariable ("id") String id) {
        return ResponseEntity.ok(pokemonSealedService.getSealedById(id));
    }

    // get sealed by stato
    @GetMapping("getsealedbystatus/{status}")
    public ResponseEntity<List<PokemonSealed>> getPokemonSealedByStatus(@PathVariable ("status") String status) {
        return ResponseEntity.ok(pokemonSealedService.getSealedByStato(status));
    }


    // VENDITE
    // add vendita univoco
    @PostMapping("addvendita")
    public ResponseEntity<AddVenditaResponse> addVenditaPokemon(@RequestBody AddVenditaRequest request){
        return ResponseEntity.ok(addVenditaService.addVenditaPokemonService(request));
    }



}
