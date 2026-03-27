package fr.epf.restaurant.controller;

import fr.epf.restaurant.dao.ClientDao;
import fr.epf.restaurant.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientDao.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@RequestBody Client client) {
        return clientDao.save(client);
    }
}