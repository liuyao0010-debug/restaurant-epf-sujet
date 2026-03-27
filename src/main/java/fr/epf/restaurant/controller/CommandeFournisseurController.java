package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.CreerCommandeFournisseurRequest;
import fr.epf.restaurant.model.CommandeFournisseur;
import fr.epf.restaurant.service.CommandeFournisseurService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commandes/fournisseur")
public class CommandeFournisseurController {

    private final CommandeFournisseurService commandeFournisseurService;

    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    @GetMapping
    public List<CommandeFournisseur> getAllCommandes(
            @RequestParam(required = false) String statut) {
        if (statut != null) {
            CommandeFournisseur.StatutCommandeFournisseur statutEnum =
                    CommandeFournisseur.StatutCommandeFournisseur.valueOf(statut.toUpperCase());
            return commandeFournisseurService.getCommandesByStatut(statutEnum);
        }
        return commandeFournisseurService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public CommandeFournisseur getCommandeById(@PathVariable Long id) {
        return commandeFournisseurService.getCommandeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommandeFournisseur createCommande(@RequestBody CreerCommandeFournisseurRequest request) {
        return commandeFournisseurService.creerCommande(request);
    }

    @PutMapping("/{id}/envoyer")
    public CommandeFournisseur envoyerCommande(@PathVariable Long id) {
        return commandeFournisseurService.envoyerCommande(id);
    }

    @PutMapping("/{id}/recevoir")
    public CommandeFournisseur recevoirCommande(@PathVariable Long id) {
        return commandeFournisseurService.recevoirCommande(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommande(@PathVariable Long id) {
        commandeFournisseurService.supprimerCommande(id);
    }
}