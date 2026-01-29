package it.tsm.wiamfrontend.controller;

import it.tsm.wiamfrontend.dto.pokemon.PokemonCardDTO;
import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import it.tsm.wiamfrontend.service.PokemonCardService;
import it.tsm.wiamfrontend.service.PokemonVenditaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pokemon/cards")
@RequiredArgsConstructor
public class PokemonCardController {

    private final PokemonCardService cardService;
    private final PokemonVenditaService venditaService;

    @GetMapping
    public String listCards(Model model) {
        try {
            model.addAttribute("cards", cardService.getAllCards());
            return "pokemon/cards/list";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento delle carte: " + e.getMessage());
            return "pokemon/cards/list";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("card", new PokemonCardDTO());
        return "pokemon/cards/form";
    }

    @PostMapping
    public String createCard(@ModelAttribute PokemonCardDTO card, RedirectAttributes redirectAttributes) {
        try {
            cardService.createCard(card);
            redirectAttributes.addFlashAttribute("success", "Carta creata con successo!");
            return "redirect:/pokemon/cards";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nella creazione: " + e.getMessage());
            return "redirect:/pokemon/cards/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("card", cardService.getCardById(id));
            return "pokemon/cards/form";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento: " + e.getMessage());
            return "redirect:/pokemon/cards";
        }
    }

    @PostMapping("/{id}")
    public String updateCard(@PathVariable String id, @ModelAttribute PokemonCardDTO card, RedirectAttributes redirectAttributes) {
        try {
            cardService.updateCard(id, card);
            redirectAttributes.addFlashAttribute("success", "Carta aggiornata con successo!");
            return "redirect:/pokemon/cards";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nell'aggiornamento: " + e.getMessage());
            return "redirect:/pokemon/cards/" + id + "/edit";
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
        return "redirect:/pokemon/cards";
    }

    @GetMapping("/{id}/vendita")
    public String showVenditaForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("card", cardService.getCardById(id));
            model.addAttribute("vendita", new VenditaDTO());
            return "pokemon/vendite/form";
        } catch (Exception e) {
            model.addAttribute("error", "Errore: " + e.getMessage());
            return "redirect:/pokemon/cards";
        }
    }

    @PostMapping("/{id}/vendita")
    public String addVendita(@PathVariable String id, @ModelAttribute VenditaDTO vendita, RedirectAttributes redirectAttributes) {
        try {
            venditaService.addVenditaCard(id, vendita);
            redirectAttributes.addFlashAttribute("success", "Vendita registrata con successo!");
            return "redirect:/pokemon/cards";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore nella registrazione vendita: " + e.getMessage());
            return "redirect:/pokemon/cards/" + id + "/vendita";
        }
    }
}
