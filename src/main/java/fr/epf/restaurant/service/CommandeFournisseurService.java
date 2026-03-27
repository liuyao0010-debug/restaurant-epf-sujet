package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.CommandeFournisseurDao;
import fr.epf.restaurant.dao.FournisseurDao;
import fr.epf.restaurant.dao.IngredientDao;
import fr.epf.restaurant.dto.CreerCommandeFournisseurRequest;
import fr.epf.restaurant.exception.CommandeStatutInvalideException;
import fr.epf.restaurant.exception.InvalidRequestException;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.model.CommandeFournisseur;
import fr.epf.restaurant.model.Fournisseur;
import fr.epf.restaurant.model.Ingredient;
import fr.epf.restaurant.model.LigneCommandeFournisseur;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandeFournisseurService {

    private final CommandeFournisseurDao commandeFournisseurDao;
    private final FournisseurDao fournisseurDao;
    private final IngredientDao ingredientDao;

    public CommandeFournisseurService(CommandeFournisseurDao commandeFournisseurDao,
                                      FournisseurDao fournisseurDao,
                                      IngredientDao ingredientDao) {
        this.commandeFournisseurDao = commandeFournisseurDao;
        this.fournisseurDao = fournisseurDao;
        this.ingredientDao = ingredientDao;
    }

    @Transactional
    public CommandeFournisseur creerCommande(CreerCommandeFournisseurRequest request) {
        Fournisseur fournisseur = fournisseurDao.findById(request.getFournisseurId());
        if (fournisseur == null) {
            throw new ResourceNotFoundException("Fournisseur non trouvé avec l'id: " + request.getFournisseurId());
        }

        List<CreerCommandeFournisseurRequest.LigneCommande> lignes = request.getLignes();
        if (lignes.isEmpty()) {
            throw new InvalidRequestException("La commande doit contenir au moins une ligne");
        }

        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setFournisseur(fournisseur);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(CommandeFournisseur.StatutCommandeFournisseur.EN_ATTENTE);
        commande = commandeFournisseurDao.save(commande);

        for (CreerCommandeFournisseurRequest.LigneCommande ligne : lignes) {
            Ingredient ingredient = ingredientDao.findById(ligne.getIngredientId());
            if (ingredient == null) {
                throw new ResourceNotFoundException("Ingrédient non trouvé avec l'id: " + ligne.getIngredientId());
            }
            commandeFournisseurDao.addLigne(
                    commande.getId(),
                    ligne.getIngredientId(),
                    ligne.getQuantite(),
                    BigDecimal.valueOf(ligne.getPrixUnitaire())
            );
        }

        return commandeFournisseurDao.findById(commande.getId());
    }

    @Transactional
    public CommandeFournisseur envoyerCommande(Long commandeId) {
        CommandeFournisseur commande = commandeFournisseurDao.findById(commandeId);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + commandeId);
        }

        if (commande.getStatut() != CommandeFournisseur.StatutCommandeFournisseur.EN_ATTENTE) {
            throw new CommandeStatutInvalideException(
                    "Impossible d'envoyer une commande au statut: " + commande.getStatut() +
                            ". Seul le statut EN_ATTENTE est autorisé."
            );
        }

        commandeFournisseurDao.updateStatut(commandeId, CommandeFournisseur.StatutCommandeFournisseur.ENVOYEE);
        return commandeFournisseurDao.findById(commandeId);
    }

    @Transactional
    public CommandeFournisseur recevoirCommande(Long commandeId) {
        CommandeFournisseur commande = commandeFournisseurDao.findById(commandeId);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + commandeId);
        }

        if (commande.getStatut() != CommandeFournisseur.StatutCommandeFournisseur.ENVOYEE) {
            throw new CommandeStatutInvalideException(
                    "Impossible de recevoir une commande au statut: " + commande.getStatut() +
                            ". Seul le statut ENVOYEE est autorisé."
            );
        }

        List<LigneCommandeFournisseur> lignes = commande.getLignes();
        if (lignes != null) {
            for (LigneCommandeFournisseur ligne : lignes) {
                ingredientDao.incrementStock(ligne.getIngredient().getId(), ligne.getQuantiteCommandee());
            }
        }

        commandeFournisseurDao.updateStatut(commandeId, CommandeFournisseur.StatutCommandeFournisseur.RECUE);
        return commandeFournisseurDao.findById(commandeId);
    }

    @Transactional
    public void supprimerCommande(Long commandeId) {
        CommandeFournisseur commande = commandeFournisseurDao.findById(commandeId);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + commandeId);
        }



        commandeFournisseurDao.deleteById(commandeId);
    }

    public List<CommandeFournisseur> getAllCommandes() {
        return commandeFournisseurDao.findAll();
    }

    public List<CommandeFournisseur> getCommandesByStatut(CommandeFournisseur.StatutCommandeFournisseur statut) {
        return commandeFournisseurDao.findByStatut(statut);
    }

    public CommandeFournisseur getCommandeById(Long id) {
        CommandeFournisseur commande = commandeFournisseurDao.findById(id);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + id);
        }
        return commande;
    }
}