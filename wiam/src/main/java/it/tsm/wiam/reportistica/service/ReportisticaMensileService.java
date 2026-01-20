package it.tsm.wiam.reportistica.service;


import it.tsm.wiam.onepiece.service.OnePieceCardService;
import it.tsm.wiam.onepiece.service.OnePieceSealedService;
import it.tsm.wiam.pokemon.service.PokemonCardService;
import it.tsm.wiam.pokemon.service.PokemonSealedService;
import it.tsm.wiam.reportistica.model.ReportDto;
import it.tsm.wiam.reportistica.model.ReportisticaMensileRequest;
import it.tsm.wiam.reportistica.model.ReportisticaResponse;
import it.tsm.wiam.reportistica.util.ReportisticaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportisticaMensileService {

    private final PokemonSealedService pokemonSealedService;
    private final PokemonCardService pokemonCardService;
    private final OnePieceCardService onePieceCardService;
    private final OnePieceSealedService onePieceSealedService;
    private final ReportisticaUtil reportisticaUtil;

    ObjectMapper mapper = new ObjectMapper();


    public ReportisticaResponse reportMensileAcquisti(ReportisticaMensileRequest request){
        log.info("ReportisticaMensile Acquisti Service started with raw request: {}",request);

        var inizioMese = request.startDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        var fineMese = request.endDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        var statoAcquisto = "Acquisto";

        var report = switch (request.tipoProdotto())  {

            case "Pokemon" -> {
                // filtro per acquisti
                var listaAcquisti = filtraPokemonAcquisti(statoAcquisto,inizioMese,fineMese);
                yield new ReportisticaResponse(listaAcquisti);
            }

            case "OnePiece" -> {
                var listaAcquisti = filtraOnePieceAcquisti(statoAcquisto,inizioMese,fineMese);
                yield new ReportisticaResponse(listaAcquisti);

            }

            default -> {
                log.info("ReportisticaMensile for pokemon e onepiece");

                var listaPokemon = filtraPokemonAcquisti(statoAcquisto,inizioMese,fineMese);
                var listaOnePiece = filtraOnePieceAcquisti(statoAcquisto,inizioMese,fineMese);
                // istanzion nuova lista
                listaPokemon.addAll(listaOnePiece);
                yield new ReportisticaResponse(listaPokemon);
            }
        };
        log.info("ReportisticaMensile Acquisti Service ended successfully with response: {}",report);
        return report;
    }

    public ReportisticaResponse reportMensileVendite(ReportisticaMensileRequest request){
        log.info("ReportisticaMensileService vendite started with raw request: {}",request);

        var inizioMese = request.startDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        var fineMese = request.endDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        var statoVendita = "Venduto";

        var report = switch (request.tipoProdotto())  {

            case "Pokemon" -> {
                // altrimenti vado per vendita
                var listaVendite = filtraPokemonVendita(statoVendita,inizioMese,fineMese);
                yield new ReportisticaResponse(listaVendite);
            }

            case "OnePiece" -> {
                // filtro per vendita
                var listaVendite = filtraOnePieceVendita(statoVendita,inizioMese,fineMese);
                yield new ReportisticaResponse(listaVendite);
            }

            default -> {
                log.info("ReportisticaMensile for pokemon e onepiece");
                // caso acquisti
                var listaPokemon = filtraPokemonVendita(statoVendita,inizioMese,fineMese);
                var listaOnePiece = filtraOnePieceVendita(statoVendita,inizioMese,fineMese);
                // istanzion nuova lista
                listaPokemon.addAll(listaOnePiece);
                yield new ReportisticaResponse(listaPokemon);
            }
        };
        log.info("ReportisticaMensile vendite Service ended successfully with response: {}",report);
        return report;
    }

    private List<ReportDto> filtraPokemonAcquisti(String stato,String inizioMese, String fineMese){
        log.info("Filtra pokemon acquisti from {} to {}",inizioMese,fineMese);
        var sealedPkm = pokemonSealedService.filteringByStatoAndRangeTimeAcquistoSealed(stato,inizioMese, fineMese);
        var cardPkm = pokemonCardService.filteringByStatoAndRangeTimeAcquistoCard(stato,inizioMese, fineMese);
        // dichiaro lista
        var listaReport = new ArrayList<ReportDto>();
        // ciclo lsita e rimappo su dto
        sealedPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("Pokemon sealed");
            listaReport.add(dto);
        });
        // stessa cosa per le carte
        cardPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("Pokemon card");
            listaReport.add(dto);
        });

        log.info("Filtra pokemon acquisti ended successfully with response: {}",listaReport);
        return listaReport;
    }

    private List<ReportDto> filtraPokemonVendita(String stato,String inizioMese, String fineMese){
        log.info("Filtra pokemon vendite from {} to {}",inizioMese,fineMese);
        var sealedPkm = pokemonSealedService.filteringByStatoAndRangeTimeVenditaSealed(stato,inizioMese, fineMese);
        var cardPkm = pokemonCardService.filteringByStatoAndRangeTimeVenditaCard(stato,inizioMese, fineMese);
        // dichiaro lista
        var listaReport = new ArrayList<ReportDto>();
        // ciclo lsita e rimappo su dto
        sealedPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("Pokemon sealed");
            listaReport.add(dto);
        });
        // stessa cosa per le carte
        cardPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("Pokemon card");
            listaReport.add(dto);
        });

        log.info("Filtra pokemon acquisti ended successfully with response: {}",listaReport);
        return listaReport;
    }

    private List<ReportDto> filtraOnePieceAcquisti(String stato,String inizioMese, String fineMese){
        log.info("Filtra OnePiece acquisti from {} to {}",inizioMese,fineMese);
        var sealedPkm = onePieceSealedService.filteringByStatoAndRangeTimeAcquistoSealed(stato,inizioMese, fineMese);
        var cardPkm = onePieceCardService.filteringByStatoAndRangeTimeAcquistoCard(stato,inizioMese, fineMese);
        // dichiaro lista
        var listaReport = new ArrayList<ReportDto>();
        // ciclo lsita e rimappo su dto
        sealedPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("OnePiece sealed");
            listaReport.add(dto);
        });
        // stessa cosa per le carte
        cardPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("OnePiece card");
            listaReport.add(dto);
        });

        log.info("Filtra OnePiece acquisti ended successfully with response: {}",listaReport);
        return listaReport;
    }

    private List<ReportDto> filtraOnePieceVendita(String stato,String inizioMese, String fineMese){
        log.info("Filtra OnePiece vendite from {} to {}",inizioMese,fineMese);
        var sealedPkm = onePieceSealedService.filteringByStatoAndRangeTimeVenditaSealed(stato,inizioMese, fineMese);
        var cardPkm = onePieceCardService.filteringByStatoAndRangeTimeVenditaCard(stato,inizioMese, fineMese);
        // dichiaro lista
        var listaReport = new ArrayList<ReportDto>();
        // ciclo lsita e rimappo su dto
        sealedPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("OnePiece sealed");
            listaReport.add(dto);
        });
        // stessa cosa per le carte
        cardPkm.forEach(i -> {
            var dto = (ReportDto) reportisticaUtil.mappingEntityToDTO(i,ReportDto.class);
            dto.setTipoProdotto("OnePiece card");
            listaReport.add(dto);
        });

        log.info("Filtra OnePiece acquisti ended successfully with response: {}",listaReport);
        return listaReport;
    }
}
