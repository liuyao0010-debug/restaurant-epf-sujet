// CommandeFournisseurService.java
package fr.epf.restaurant.service;
import fr.epf.restaurant.dao.CommandeFournisseurDao;
import fr.epf.restaurant.dto.CommandeFournisseurDTO;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CommandeFournisseurService {
    private final CommandeFournisseurDao dao;
    public CommandeFournisseurService(CommandeFournisseurDao dao) {
        this.dao = dao;
    }
    public List<CommandeFournisseurDTO> getAllCommandes() {
        return dao.findAll();
    }
}