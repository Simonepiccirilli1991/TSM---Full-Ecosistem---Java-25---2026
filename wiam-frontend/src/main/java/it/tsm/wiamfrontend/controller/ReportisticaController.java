package it.tsm.wiamfrontend.controller;

import it.tsm.wiamfrontend.service.ReportisticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reportistica")
@RequiredArgsConstructor
public class ReportisticaController {

    private final ReportisticaService reportisticaService;

    @GetMapping
    public String dashboard(Model model) {
        try {
            model.addAttribute("recap", reportisticaService.getRecapGenerale());
            model.addAttribute("profitti", reportisticaService.getProfittiTotali());
            return "reportistica/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento reportistica: " + e.getMessage());
            return "reportistica/dashboard";
        }
    }

    @GetMapping("/pokemon")
    public String reportPokemon(Model model) {
        try {
            model.addAttribute("report", reportisticaService.getReportPokemon());
            model.addAttribute("reportCards", reportisticaService.getReportPokemonCards());
            model.addAttribute("reportSealed", reportisticaService.getReportPokemonSealed());
            model.addAttribute("profitti", reportisticaService.getProfittiPokemon());
            return "reportistica/pokemon";
        } catch (Exception e) {
            model.addAttribute("error", "Errore: " + e.getMessage());
            return "reportistica/pokemon";
        }
    }

    @GetMapping("/onepiece")
    public String reportOnePiece(Model model) {
        try {
            model.addAttribute("report", reportisticaService.getReportOnePiece());
            model.addAttribute("reportCards", reportisticaService.getReportOnePieceCards());
            model.addAttribute("reportSealed", reportisticaService.getReportOnePieceSealed());
            model.addAttribute("profitti", reportisticaService.getProfittiOnePiece());
            return "reportistica/onepiece";
        } catch (Exception e) {
            model.addAttribute("error", "Errore: " + e.getMessage());
            return "reportistica/onepiece";
        }
    }
}
