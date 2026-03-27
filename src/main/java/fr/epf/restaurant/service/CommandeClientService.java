package fr.epf.restaurant.service;

import fr.epf.restaurant.dao.ClientDao;
import fr.epf.restaurant.dao.CommandeClientDao;
import fr.epf.restaurant.dao.PlatDao;
import fr.epf.restaurant.dto.CreerCommandeClientRequest;
import fr.epf.restaurant.dto.PreparationResultDto;
import fr.epf.restaurant.exception.CommandeStatutInvalideException;
import fr.epf.restaurant.exception.ResourceNotFoundException;
import fr.epf.restaurant.model.Client;
import fr.epf.restaurant.model.CommandeClient;
import fr.epf.restaurant.model.LigneCommandeClient;
import fr.epf.restaurant.model.Plat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommandeClientService {

    private final CommandeClientDao commandeClientDao;
    private final ClientDao clientDao;
    private final PlatDao platDao;
    private final StockService stockService;
    private final JdbcTemplate jdbcTemplate;

    public CommandeClientService(CommandeClientDao commandeClientDao,
                                 ClientDao clientDao,
                                 PlatDao platDao,
                                 StockService stockService,
                                 JdbcTemplate jdbcTemplate) {
        this.commandeClientDao = commandeClientDao;
        this.clientDao = clientDao;
        this.platDao = platDao;
        this.stockService = stockService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public CommandeClient creerCommande(CreerCommandeClientRequest request) {
        Client client = clientDao.findById(request.getClientId());
        if (client == null) {
            throw new ResourceNotFoundException("Client non trouvé avec l'id: " + request.getClientId());
        }

        List<Plat> plats = new ArrayList<>();
        List<Integer> quantites = new ArrayList<>();
        for (CreerCommandeClientRequest.LigneCommande ligne : request.getLignes()) {
            Plat plat = platDao.findById(ligne.getPlatId());
            if (plat == null) {
                throw new ResourceNotFoundException("Plat non trouvé avec l'id: " + ligne.getPlatId());
            }
            plats.add(plat);
            quantites.add(ligne.getQuantite());
        }

        CommandeClient commande = new CommandeClient();
        commande.setClient(client);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(CommandeClient.StatutCommandeClient.EN_ATTENTE);
        commande = commandeClientDao.save(commande);

        for (int i = 0; i < plats.size(); i++) {
            commandeClientDao.addLigne(commande.getId(), plats.get(i).getId(), quantites.get(i));
        }

        return commandeClientDao.findById(commande.getId());
    }

    @Transactional
    public PreparationResultDto preparerCommande(Long commandeId) {
        CommandeClient commande = commandeClientDao.findById(commandeId);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + commandeId);
        }

        if (commande.getStatut() != CommandeClient.StatutCommandeClient.EN_ATTENTE) {
            throw new CommandeStatutInvalideException(
                    "Impossible de préparer une commande au statut: " + commande.getStatut() +
                            ". Seul le statut EN_ATTENTE est autorisé."
            );
        }

        List<Plat> plats = new ArrayList<>();
        List<Integer> quantites = new ArrayList<>();
        for (LigneCommandeClient ligne : commande.getLignes()) {
            plats.add(ligne.getPlat());
            quantites.add(ligne.getQuantite());
        }
        Map<Long, Double> quantitesRequises = stockService.calculerQuantitesRequises(plats, quantites);

        stockService.verifierStockSuffisant(quantitesRequises);
        stockService.consommerStock(quantitesRequises);
        commandeClientDao.updateStatut(commandeId, CommandeClient.StatutCommandeClient.EN_PREPARATION);

        List<String> alertes = stockService.getAlertesApresConsommation(quantitesRequises);
        commande = commandeClientDao.findById(commandeId);
        return new PreparationResultDto(commande, alertes);
    }

    @Transactional
    public CommandeClient servirCommande(Long commandeId) {
        CommandeClient commande = commandeClientDao.findById(commandeId);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + commandeId);
        }

        if (commande.getStatut() != CommandeClient.StatutCommandeClient.EN_PREPARATION) {
            throw new CommandeStatutInvalideException(
                    "Impossible de servir une commande au statut: " + commande.getStatut() +
                            ". Seul le statut EN_PREPARATION est autorisé."
            );
        }

        commandeClientDao.updateStatut(commandeId, CommandeClient.StatutCommandeClient.SERVIE);
        return commandeClientDao.findById(commandeId);
    }

    @Transactional
    public void supprimerCommande(Long commandeId) {
        CommandeClient commande = commandeClientDao.findById(commandeId);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + commandeId);
        }

        if (commande.getStatut() == CommandeClient.StatutCommandeClient.EN_PREPARATION) {
            List<Plat> plats = new ArrayList<>();
            List<Integer> quantites = new ArrayList<>();
            for (LigneCommandeClient ligne : commande.getLignes()) {
                plats.add(ligne.getPlat());
                quantites.add(ligne.getQuantite());
            }
            Map<Long, Double> quantitesConsommees = stockService.calculerQuantitesRequises(plats, quantites);
            stockService.restituerStock(quantitesConsommees);
        }

        String deleteLignesSql = "DELETE FROM LIGNE_COMMANDE_CLIENT WHERE commande_client_id = ?";
        jdbcTemplate.update(deleteLignesSql, commandeId);

        commandeClientDao.deleteById(commandeId);
    }

    public List<CommandeClient> getAllCommandes() {
        return commandeClientDao.findAll();
    }

    public List<CommandeClient> getCommandesByStatut(CommandeClient.StatutCommandeClient statut) {
        return commandeClientDao.findByStatut(statut);
    }

    public CommandeClient getCommandeById(Long id) {
        CommandeClient commande = commandeClientDao.findById(id);
        if (commande == null) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + id);
        }
        return commande;
    }
}