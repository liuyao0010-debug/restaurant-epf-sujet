package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.model.CommandeClient;
import fr.epf.restaurant.service.CommandeClientService;
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
@RequestMapping("/api/commandes/client")
public class CommandeClientController {

    private final CommandeClientService commandeClientService;

    public CommandeClientController(CommandeClientService commandeClientService) {
        this.commandeClientService = commandeClientService;
    }

    @GetMapping
    public List<CommandeClient> getAllCommandes(
            @RequestParam(required = false) String statut) {
        if (statut != null) {
            CommandeClient.StatutCommandeClient statutEnum =
                    CommandeClient.StatutCommandeClient.valueOf(statut.toUpperCase());
            return commandeClientService.getCommandesByStatut(statutEnum);
        }
        return commandeClientService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public CommandeClient getCommandeById(@PathVariable Long id) {
        return commandeClientService.getCommandeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommandeClient createCommande(@RequestBody CreerCommandeClientRequest request) {
        return commandeClientService.creerCommande(request);
    }

    @PutMapping("/{id}/preparer")
    public PreparationResultDto preparerCommande(@PathVariable Long id) {
        return commandeClientService.preparerCommande(id);
    }

    @PutMapping("/{id}/servir")
    public CommandeClient servirCommande(@PathVariable Long id) {
        return commandeClientService.servirCommande(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommande(@PathVariable Long id) {
        commandeClientService.supprimerCommande(id);
    }
}