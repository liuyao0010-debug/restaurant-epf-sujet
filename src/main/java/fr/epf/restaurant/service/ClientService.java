package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.ClientDao;
import fr.epf.restaurant.dto.ClientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientDao dao;
    public ClientService(ClientDao dao){this.dao=dao;}
    public List<ClientDTO> getAllClients(){return dao.findAll();}
}