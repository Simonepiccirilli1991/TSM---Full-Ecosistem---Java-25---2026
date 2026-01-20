package it.tsm.wiam.reportistica.service;


import it.tsm.wiam.onepiece.service.OnePieceCardService;
import it.tsm.wiam.onepiece.service.OnePieceSealedService;
import it.tsm.wiam.pokemon.service.PokemonCardService;
import it.tsm.wiam.pokemon.service.PokemonSealedService;
import it.tsm.wiam.reportistica.model.ReportDto;
import it.tsm.wiam.reportistica.model.ReportisticaRequest;
import it.tsm.wiam.reportistica.model.ReportisticaResponse;
import it.tsm.wiam.reportistica.util.ReportisticaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ReportisticaService {


    private final PokemonSealedService pokemonSealedService;
    private final PokemonCardService pokemonCardService;
    private final OnePieceCardService onePieceCardService;
    private final OnePieceSealedService onePieceSealedService;
    private final ReportisticaUtil reportisticaUtil;


    // creo servizio di reportistica per pokemon sealed e card e poi per one Piece sealed e card
    public ReportisticaResponse reportDettaglio(ReportisticaRequest request){
        log.info("ReportisticaService started with raw request: {}",request);

        // filtro per reportistica
        var resp = switch (request.tipoProdotto()){

            case "Pokemon" -> {
                var listaPokemon = filteringPokemon(request.stato());
                yield new ReportisticaResponse(listaPokemon);
            }

            case "OnePiece" -> {
                var listaOnePiece = filteringOnePiece(request.stato());
                yield new ReportisticaResponse(listaOnePiece);
            }


            default -> {
                log.info("Reportistica for pokemon e onepiece");
                var listaPokemon = filteringPokemon(request.stato());
                var listaOnePiece = filteringOnePiece(request.stato());
                // istanzion nuova lista
                var listaTotale = new ArrayList<ReportDto>();
                // addo pokemon
                listaTotale.addAll(listaPokemon);
                // addo one piece
                listaTotale.addAll(listaOnePiece);
                // ritorno lista totale
                yield new ReportisticaResponse(listaTotale);
            }
        };

        log.info("Reportistica Service ended successfully with response: {}",resp);
        return resp;
    }

    // filtering pokemon
    private List<ReportDto> filteringPokemon(String stato){
        log.info("Filtering reportistica Pokemon per stato: {}",stato);

        var carteSingole = pokemonCardService.getCartaByStato(stato);
        var listaReport = new ArrayList<ReportDto>();

        // devo rimappare i dto sulla resp per carte
        carteSingole.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i, ReportDto.class);
            // setto parametro che sia pokemon
            dto.setTipoProdotto("Pokemon");
            //addo alla lita
            listaReport.add(dto);
        });

        // ora riprendo le card sealed
        var carteSealed = pokemonSealedService.getSealedByStato(stato);
        // devo rimappare i dto dei sealed sulla resp
        carteSealed.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i, ReportDto.class);
            dto.setTipoProdotto("Pokemon");
            listaReport.add(dto);
        });

        log.info("Filtering reportistica Pokemon per stato ended successfully with response: {}",listaReport);
        return listaReport;
    }


    // filtering one piece
    private List<ReportDto> filteringOnePiece(String stato){
        log.info("Filtering reportistica OnePiece per stato: {}",stato);

        var carteSingole = onePieceCardService.getCartaByStato(stato);
        var listaReport = new ArrayList<ReportDto>();

        // devo rimappare i dto sulla resp per carte
        carteSingole.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i, ReportDto.class);
            // setto tipo prodotto
            dto.setTipoProdotto("OnePiece");
            //addo alla lita
            listaReport.add(dto);
        });

        // ora riprendo le card sealed
        var carteSealed = onePieceSealedService.getSealedByStato(stato);
        // devo rimappare i dto dei sealed sulla resp
        carteSealed.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i, ReportDto.class);
            dto.setTipoProdotto("OnePiece");
            listaReport.add(dto);
        });

        log.info("Filtering reportistica OnePiece per stato ended successfully with response: {}",listaReport);
        return listaReport;
    }


}
