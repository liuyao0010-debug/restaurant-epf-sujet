package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.CommandeDao;
import fr.epf.restaurant.dto.CommandeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeClientService {
    private final CommandeDao dao;
    public CommandeClientService(CommandeDao dao){this.dao=dao;}
    public List<CommandeDTO> getAllCommandes(){return dao.findClientCommandes();}
    public CommandeDTO getCommandeById(Long id){return dao.findClientCommandes().stream().filter(c->c.getId().equals(id)).findFirst().orElse(null);}
    public CommandeDTO createCommande(CommandeDTO dto){return dao.createClientCommande(dto);}
    public CommandeDTO preparerCommande(Long id){return dao.updateStatut(id,"PREPARE");}
    public CommandeDTO servirCommande(Long id){return dao.updateStatut(id,"SERVI");}
    public void deleteCommande(Long id){dao.deleteClientCommande(id);}
}