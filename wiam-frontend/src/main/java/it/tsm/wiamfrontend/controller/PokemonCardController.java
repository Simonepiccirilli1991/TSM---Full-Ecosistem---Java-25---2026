package it.tsm.wiamfrontend.controller;

import it.tsm.wiamfrontend.dto.pokemon.PokemonCardDTO;
import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import it.tsm.wiamfrontend.exception.BackendException;
import it.tsm.wiamfrontend.service.PokemonCardService;
import it.tsm.wiamfrontend.service.PokemonVenditaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;

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
    public String createCard(@ModelAttribute PokemonCardDTO card,
                           @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                           RedirectAttributes redirectAttributes) {
        try {
            // Gestione upload foto
            if (fotoFile != null && !fotoFile.isEmpty()) {
                card.setFoto(fotoFile.getBytes());
            }

            cardService.createCard(card);
            redirectAttributes.addFlashAttribute("success", "Carta creata con successo!");
            return "redirect:/pokemon/cards";
        } catch (BackendException e) {
            if (e.getStatusCode() >= 500) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            } else if (e.getStatusCode() >= 400) {
                redirectAttributes.addFlashAttribute("error", "Errore di validazione: " + e.getMessage());
            }
            return "redirect:/pokemon/cards/new";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Errore nel caricamento dell'immagine: " + e.getMessage());
            return "redirect:/pokemon/cards/new";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore imprevisto: " + e.getMessage());
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
    public String updateCard(@PathVariable String id,
                           @ModelAttribute PokemonCardDTO card,
                           @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                           RedirectAttributes redirectAttributes) {
        try {
            // Gestione upload foto (se presente, aggiorna la foto)
            if (fotoFile != null && !fotoFile.isEmpty()) {
                card.setFoto(fotoFile.getBytes());
            }

            cardService.updateCard(id, card);
            redirectAttributes.addFlashAttribute("success", "Carta aggiornata con successo!");
            return "redirect:/pokemon/cards";
        } catch (BackendException e) {
            if (e.getStatusCode() >= 500) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            } else if (e.getStatusCode() >= 400) {
                redirectAttributes.addFlashAttribute("error", "Errore di validazione: " + e.getMessage());
            }
            return "redirect:/pokemon/cards/" + id + "/edit";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Errore nel caricamento dell'immagine: " + e.getMessage());
            return "redirect:/pokemon/cards/" + id + "/edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore imprevisto: " + e.getMessage());
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
            PokemonCardDTO card = cardService.getCardById(id);

            // Converti foto in base64 se presente
            if (card.getFoto() != null) {
                String fotoBase64 = Base64.getEncoder().encodeToString(card.getFoto());
                model.addAttribute("fotoBase64", fotoBase64);
            }

            model.addAttribute("card", card);
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
        } catch (BackendException e) {
            if (e.getStatusCode() >= 500) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            } else if (e.getStatusCode() >= 400) {
                redirectAttributes.addFlashAttribute("error", "Errore di validazione: " + e.getMessage());
            }
            return "redirect:/pokemon/cards/" + id + "/vendita";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore imprevisto: " + e.getMessage());
            return "redirect:/pokemon/cards/" + id + "/vendita";
        }
    }
}
