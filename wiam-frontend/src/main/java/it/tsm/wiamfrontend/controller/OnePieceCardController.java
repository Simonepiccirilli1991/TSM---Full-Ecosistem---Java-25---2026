package it.tsm.wiamfrontend.controller;

import it.tsm.wiamfrontend.dto.onepiece.OnePieceCardDTO;
import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import it.tsm.wiamfrontend.service.OnePieceCardService;
import it.tsm.wiamfrontend.service.OnePieceVenditaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/onepiece/cards")
@RequiredArgsConstructor
public class OnePieceCardController {

    private final OnePieceCardService cardService;
    private final OnePieceVenditaService venditaService;

    @GetMapping
    public String listCards(Model model) {
        try {
            model.addAttribute("cards", cardService.getAllCards());
            return "onepiece/cards/list";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento delle carte: " + e.getMessage());
            return "onepiece/cards/list";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("card", new OnePieceCardDTO());
        return "onepiece/cards/form";
    }

    @PostMapping
    public String createCard(@ModelAttribute OnePieceCardDTO card, RedirectAttributes redirectAttributes) {
        try {
            cardService.createCard(card);
            redirectAttributes.addFlashAttribute("success", "Carta creata con successo!");
            return "redirect:/onepiece/cards";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nella creazione: " + e.getMessage());
            return "redirect:/onepiece/cards/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("card", cardService.getCardById(id));
            return "onepiece/cards/form";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento: " + e.getMessage());
            return "redirect:/onepiece/cards";
        }
    }

    @PostMapping("/{id}")
    public String updateCard(@PathVariable String id, @ModelAttribute OnePieceCardDTO card, RedirectAttributes redirectAttributes) {
        try {
            cardService.updateCard(id, card);
            redirectAttributes.addFlashAttribute("success", "Carta aggiornata con successo!");
            return "redirect:/onepiece/cards";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'aggiornamento: " + e.getMessage());
            return "redirect:/onepiece/cards/" + id + "/edit";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteCard(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            cardService.deleteCard(id);
            redirectAttributes.addFlashAttribute("success", "Carta eliminata con successo!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'eliminazione: " + e.getMessage());
        }
        return "redirect:/onepiece/cards";
    }

    @GetMapping("/{id}/vendita")
    public String showVenditaForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("card", cardService.getCardById(id));
            model.addAttribute("vendita", new VenditaDTO());
            return "onepiece/vendite/form";
        } catch (Exception e) {
            model.addAttribute("error", "Errore: " + e.getMessage());
            return "redirect:/onepiece/cards";
        }
    }

    @PostMapping("/{id}/vendita")
    public String addVendita(@PathVariable String id, @ModelAttribute VenditaDTO vendita, RedirectAttributes redirectAttributes) {
        try {
            venditaService.addVenditaCard(id, vendita);
            redirectAttributes.addFlashAttribute("success", "Vendita registrata con successo!");
            return "redirect:/onepiece/cards";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nella registrazione vendita: " + e.getMessage());
            return "redirect:/onepiece/cards/" + id + "/vendita";
        }
    }
}
