package it.tsm.wiamfrontend.controller;

import it.tsm.wiamfrontend.dto.onepiece.OnePieceSealedDTO;
import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import it.tsm.wiamfrontend.service.OnePieceSealedService;
import it.tsm.wiamfrontend.service.OnePieceVenditaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/onepiece/sealed")
@RequiredArgsConstructor
public class OnePieceSealedController {

    private final OnePieceSealedService sealedService;
    private final OnePieceVenditaService venditaService;

    @GetMapping
    public String listSealed(Model model) {
        try {
            model.addAttribute("sealedList", sealedService.getAllSealed());
            return "onepiece/sealed/list";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento: " + e.getMessage());
            return "onepiece/sealed/list";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("sealed", new OnePieceSealedDTO());
        return "onepiece/sealed/form";
    }

    @PostMapping
    public String createSealed(@ModelAttribute OnePieceSealedDTO sealed, RedirectAttributes redirectAttributes) {
        try {
            sealedService.createSealed(sealed);
            redirectAttributes.addFlashAttribute("success", "Sealed creato con successo!");
            return "redirect:/onepiece/sealed";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nella creazione: " + e.getMessage());
            return "redirect:/onepiece/sealed/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("sealed", sealedService.getSealedById(id));
            return "onepiece/sealed/form";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento: " + e.getMessage());
            return "redirect:/onepiece/sealed";
        }
    }

    @PostMapping("/{id}")
    public String updateSealed(@PathVariable String id, @ModelAttribute OnePieceSealedDTO sealed, RedirectAttributes redirectAttributes) {
        try {
            sealedService.updateSealed(id, sealed);
            redirectAttributes.addFlashAttribute("success", "Sealed aggiornato con successo!");
            return "redirect:/onepiece/sealed";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'aggiornamento: " + e.getMessage());
            return "redirect:/onepiece/sealed/" + id + "/edit";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteSealed(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            sealedService.deleteSealed(id);
            redirectAttributes.addFlashAttribute("success", "Sealed eliminato con successo!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'eliminazione: " + e.getMessage());
        }
        return "redirect:/onepiece/sealed";
    }

    @GetMapping("/{id}/vendita")
    public String showVenditaForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("sealed", sealedService.getSealedById(id));
            model.addAttribute("vendita", new VenditaDTO());
            return "onepiece/vendite/form-sealed";
        } catch (Exception e) {
            model.addAttribute("error", "Errore: " + e.getMessage());
            return "redirect:/onepiece/sealed";
        }
    }

    @PostMapping("/{id}/vendita")
    public String addVendita(@PathVariable String id, @ModelAttribute VenditaDTO vendita, RedirectAttributes redirectAttributes) {
        try {
            venditaService.addVenditaSealed(id, vendita);
            redirectAttributes.addFlashAttribute("success", "Vendita registrata con successo!");
            return "redirect:/onepiece/sealed";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nella registrazione vendita: " + e.getMessage());
            return "redirect:/onepiece/sealed/" + id + "/vendita";
        }
    }
}
