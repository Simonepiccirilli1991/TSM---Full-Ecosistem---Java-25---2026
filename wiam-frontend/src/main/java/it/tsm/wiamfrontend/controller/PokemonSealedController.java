package it.tsm.wiamfrontend.controller;

import it.tsm.wiamfrontend.dto.pokemon.PokemonSealedDTO;
import it.tsm.wiamfrontend.dto.pokemon.VenditaDTO;
import it.tsm.wiamfrontend.exception.BackendException;
import it.tsm.wiamfrontend.service.PokemonSealedService;
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
@RequestMapping("/pokemon/sealed")
@RequiredArgsConstructor
public class PokemonSealedController {

    private final PokemonSealedService sealedService;
    private final PokemonVenditaService venditaService;

    @GetMapping
    public String listSealed(Model model) {
        try {
            model.addAttribute("sealedList", sealedService.getAllSealed());
            return "pokemon/sealed/list";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento: " + e.getMessage());
            return "pokemon/sealed/list";
        }
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("sealed", new PokemonSealedDTO());
        return "pokemon/sealed/form";
    }

    @PostMapping
    public String createSealed(@ModelAttribute PokemonSealedDTO sealed,
                             @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                             RedirectAttributes redirectAttributes) {
        try {
            // Gestione upload foto
            if (fotoFile != null && !fotoFile.isEmpty()) {
                sealed.setFoto(fotoFile.getBytes());
            }

            sealedService.createSealed(sealed);
            redirectAttributes.addFlashAttribute("success", "Sealed creato con successo!");
            return "redirect:/pokemon/sealed";
        } catch (BackendException e) {
            if (e.getStatusCode() >= 500) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            } else if (e.getStatusCode() >= 400) {
                redirectAttributes.addFlashAttribute("error", "Errore di validazione: " + e.getMessage());
            }
            return "redirect:/pokemon/sealed/new";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Errore nel caricamento dell'immagine: " + e.getMessage());
            return "redirect:/pokemon/sealed/new";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore imprevisto: " + e.getMessage());
            return "redirect:/pokemon/sealed/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        try {
            model.addAttribute("sealed", sealedService.getSealedById(id));
            return "pokemon/sealed/form";
        } catch (Exception e) {
            model.addAttribute("error", "Errore nel caricamento: " + e.getMessage());
            return "redirect:/pokemon/sealed";
        }
    }

    @PostMapping("/{id}")
    public String updateSealed(@PathVariable String id,
                             @ModelAttribute PokemonSealedDTO sealed,
                             @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                             RedirectAttributes redirectAttributes) {
        try {
            // Gestione upload foto (se presente, aggiorna la foto)
            if (fotoFile != null && !fotoFile.isEmpty()) {
                sealed.setFoto(fotoFile.getBytes());
            }

            sealedService.updateSealed(id, sealed);
            redirectAttributes.addFlashAttribute("success", "Sealed aggiornato con successo!");
            return "redirect:/pokemon/sealed";
        } catch (BackendException e) {
            if (e.getStatusCode() >= 500) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            } else if (e.getStatusCode() >= 400) {
                redirectAttributes.addFlashAttribute("error", "Errore di validazione: " + e.getMessage());
            }
            return "redirect:/pokemon/sealed/" + id + "/edit";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Errore nel caricamento dell'immagine: " + e.getMessage());
            return "redirect:/pokemon/sealed/" + id + "/edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore imprevisto: " + e.getMessage());
            return "redirect:/pokemon/sealed/" + id + "/edit";
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
        return "redirect:/pokemon/sealed";
    }

    @GetMapping("/{id}/vendita")
    public String showVenditaForm(@PathVariable String id, Model model) {
        try {
            PokemonSealedDTO sealed = sealedService.getSealedById(id);

            // Converti foto in base64 se presente
            if (sealed.getFoto() != null) {
                String fotoBase64 = Base64.getEncoder().encodeToString(sealed.getFoto());
                model.addAttribute("fotoBase64", fotoBase64);
            }

            model.addAttribute("sealed", sealed);
            model.addAttribute("vendita", new VenditaDTO());
            return "pokemon/vendite/form-sealed";
        } catch (Exception e) {
            model.addAttribute("error", "Errore: " + e.getMessage());
            return "redirect:/pokemon/sealed";
        }
    }

    @PostMapping("/{id}/vendita")
    public String addVendita(@PathVariable String id, @ModelAttribute VenditaDTO vendita, RedirectAttributes redirectAttributes) {
        try {
            venditaService.addVenditaSealed(id, vendita);
            redirectAttributes.addFlashAttribute("success", "Vendita registrata con successo!");
            return "redirect:/pokemon/sealed";
        } catch (BackendException e) {
            if (e.getStatusCode() >= 500) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            } else if (e.getStatusCode() >= 400) {
                redirectAttributes.addFlashAttribute("error", "Errore di validazione: " + e.getMessage());
            }
            return "redirect:/pokemon/sealed/" + id + "/vendita";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore imprevisto: " + e.getMessage());
            return "redirect:/pokemon/sealed/" + id + "/vendita";
        }
    }
}
