package fr.epf.restaurant.controller;

import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.dto.FournisseurCatalogueDto;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.model.Fournisseur;
import fr.epf.restaurant.model.FournisseurIngredient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurDao fournisseurDao;

    public FournisseurController(FournisseurDao fournisseurDao) {
        this.fournisseurDao = fournisseurDao;
    }

    @GetMapping
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurDao.findAll();
    }

    @GetMapping("/{id}")
    public Fournisseur getFournisseurById(@PathVariable Long id) {
        Fournisseur fournisseur = fournisseurDao.findById(id);
        if (fournisseur == null) {
            throw new ResourceNotFoundException("Fournisseur non trouvé avec l'id: " + id);
        }
        return fournisseur;
    }

    @GetMapping("/{id}/catalogue")
    public List<FournisseurCatalogueDto> getCatalogue(@PathVariable Long id) {
        Fournisseur fournisseur = fournisseurDao.findById(id);
        if (fournisseur == null) {
            throw new ResourceNotFoundException("Fournisseur non trouvé avec l'id: " + id);
        }

        List<FournisseurIngredient> catalogue = fournisseurDao.findCatalogueByFournisseurId(id);
        return catalogue.stream()
                .map(fi -> new FournisseurCatalogueDto(
                        fi.getIngredient().getId(),
                        fi.getIngredient().getNom(),
                        fi.getIngredient().getUnite(),
                        fi.getPrixUnitaire()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
        return fournisseurDao.save(fournisseur);
    }
}