package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.CommandeFournisseurDTO;
import fr.epf.restaurant.service.CommandeFournisseurService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/commande-fournisseurs")
public class CommandeFournisseurController {
    private final CommandeFournisseurService service;

    public CommandeFournisseurController(CommandeFournisseurService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommandeFournisseurDTO> getCommandesFournisseurs() {
        return service.getAllCommandes();
    }
}