package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.IngredientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientDao dao;
    public IngredientService(IngredientDao dao){this.dao=dao;}
    public List<IngredientDTO> getAllIngredients(){return dao.findAll();}
    public List<IngredientDTO> getIngredientsAlertes(){return dao.findAlertes();}
    public Double getPrix(Long id){return dao.getPrix(id);}
    public String getRecommandation(Long id){return dao.getRecommandation(id);}
}