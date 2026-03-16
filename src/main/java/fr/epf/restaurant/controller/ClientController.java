package fr.epf.restaurant.controller;

import fr.epf.restaurant.dto.ClientDTO;
import fr.epf.restaurant.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClientDTO> getClients() {
        return service.getAllClients();
    }
}