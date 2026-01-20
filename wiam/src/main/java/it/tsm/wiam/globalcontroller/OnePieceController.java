package it.tsm.wiam.globalcontroller;

import it.tsm.wiam.onepiece.entity.OnePieceCard;
import it.tsm.wiam.onepiece.entity.OnePieceSealed;
import it.tsm.wiam.onepiece.model.*;
import it.tsm.wiam.onepiece.service.AddOnePieceVenditaService;
import it.tsm.wiam.onepiece.service.OnePieceCardService;
import it.tsm.wiam.onepiece.service.OnePieceSealedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/onepiece")
public class OnePieceController {


    private final OnePieceCardService onePieceCardService;
    private final OnePieceSealedService onePieceSealedService;
    private final AddOnePieceVenditaService addOnePieceVenditaService;

    // add carta
    @PostMapping("addcard")
    public ResponseEntity<AddOnePieceCardResponse> addOnePieceCard(@RequestBody AddOnePieceCardRequest request){
        return ResponseEntity.ok(onePieceCardService.aggiungiCartaOnePiece(request));
    }
    // delete carta
    @DeleteMapping("deletecard/{id}")
    public ResponseEntity<Void> cancellaCarta(@PathVariable ("id") String id){
        onePieceCardService.cancellaCarta(id);
        return ResponseEntity.ok(null);
    }

    // get carta by id
    @GetMapping("getcard/{id}")
    public ResponseEntity<OnePieceCard> getOnePieceCardById(@PathVariable ("id") String id) {
        return ResponseEntity.ok(onePieceCardService.getCartaById(id));
    }

    // get carta by stato
    @GetMapping("getcardsbystatus/{status}")
    public ResponseEntity<List<OnePieceCard>> getOnePieceCardsByStatus(@PathVariable ("status") String status) {
        return ResponseEntity.ok(onePieceCardService.getCartaByStato(status));
    }

    // PRODOTTI SEALED

    // add prodotto sealed
    @PostMapping("addsealed")
    public ResponseEntity<AddOnePieceSealedResponse> addOnePieceSealed(@RequestBody AddOnePieceSealedRequest request){
        return ResponseEntity.ok(onePieceSealedService.addOnePieceSealedService(request));
    }
    // delete sealed
    @DeleteMapping("deletesealed/{id}")
    public ResponseEntity<Void> cancellaSealed(@PathVariable ("id") String id){
        onePieceSealedService.cancellaSealed(id);
        return ResponseEntity.ok(null);
    }

    // get sealed by id
    @GetMapping("getsealed/{id}")
    public ResponseEntity<OnePieceSealed> getOnePieceSealedById(@PathVariable ("id") String id) {
        return ResponseEntity.ok(onePieceSealedService.getSealedById(id));
    }

    // get sealed by stato
    @GetMapping("getsealedbystatus/{status}")
    public ResponseEntity<List<OnePieceSealed>> getOnePieceSealedByStatus(@PathVariable ("status") String status) {
        return ResponseEntity.ok(onePieceSealedService.getSealedByStato(status));
    }


    // VENDITE
    // add vendita univoco
    @PostMapping("addvendita")
    public ResponseEntity<AddOnePieceVenditaResponse> addVenditaOnePiece(@RequestBody AddOnePieceVenditaRequest request){
        return ResponseEntity.ok(addOnePieceVenditaService.addVenditaOnePieceService(request));
    }



}
