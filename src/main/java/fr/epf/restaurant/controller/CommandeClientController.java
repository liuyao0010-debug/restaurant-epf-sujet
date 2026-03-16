package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.CommandeDTO;
import fr.epf.restaurant.service.CommandeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService service;

    public CommandeController(CommandeService service) {
        this.service = service;
    }

    @GetMapping("/client")
    public List<CommandeDTO> getCommandesClient() {
        return service.getCommandesClient();
    }

    @PostMapping("/client")
    public CommandeDTO addCommandeClient(@RequestBody CommandeDTO dto) {
        return service.addCommandeClient(dto);
    }

    @GetMapping("/fournisseur")
    public List<CommandeDTO> getCommandesFournisseur() {
        return service.getCommandesFournisseur();
    }

    @PostMapping("/fournisseur")
    public CommandeDTO addCommandeFournisseur(@RequestBody CommandeDTO dto) {
        return service.addCommandeFournisseur(dto);
    }
}