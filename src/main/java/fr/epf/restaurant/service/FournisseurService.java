package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.dto.FournisseurDTO;
import fr.epf.restaurant.dto.PlatDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurService {
    private final FournisseurDao dao;
    public FournisseurService(FournisseurDao dao){this.dao=dao;}
    public List<FournisseurDTO> getAllFournisseurs(){return dao.findAll();}
    public List<PlatDTO> getCatalogue(Long id){return dao.getCatalogue(id);}
}