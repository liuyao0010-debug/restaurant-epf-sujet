package fr.epf.restaurant.dao;

import fr.epf.restaurant.model.CommandeClient;
import fr.epf.restaurant.model.LigneCommandeClient;
import fr.epf.restaurant.model.Client;
import fr.epf.restaurant.model.Plat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommandeClientDao {

    private final JdbcTemplate jdbcTemplate;
    private final ClientDao clientDao;
    private final PlatDao platDao;

    public CommandeClientDao(JdbcTemplate jdbcTemplate, ClientDao clientDao, PlatDao platDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.clientDao = clientDao;
        this.platDao = platDao;
    }

    private RowMapper<CommandeClient> commandeClientRowMapper() {
        return new RowMapper<CommandeClient>() {
            @Override
            public CommandeClient mapRow(ResultSet rs, int rowNum) throws SQLException {
                CommandeClient commande = new CommandeClient();
                commande.setId(rs.getLong("id"));
                Long clientId = rs.getLong("client_id");
                Client client = clientDao.findById(clientId);
                commande.setClient(client);
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setStatut(CommandeClient.StatutCommandeClient.valueOf(rs.getString("statut")));
                return commande;
            }
        };
    }

    public List<CommandeClient> findAll() {
        String sql = "SELECT id, client_id, date_commande, statut "
                   + "FROM COMMANDE_CLIENT "
                   + "ORDER BY id DESC";
        List<CommandeClient> commandes = jdbcTemplate.query(sql, commandeClientRowMapper());
        for (CommandeClient commande : commandes) {
            commande.setLignes(findLignesByCommandeId(commande.getId()));
        }
        return commandes;
    }

    public List<CommandeClient> findByStatut(CommandeClient.StatutCommandeClient statut) {
        String sql = "SELECT id, client_id, date_commande, statut "
                   + "FROM COMMANDE_CLIENT "
                   + "WHERE statut = ? "
                   + "ORDER BY id DESC";
        List<CommandeClient> commandes = jdbcTemplate.query(sql, commandeClientRowMapper(), statut.name());
        for (CommandeClient commande : commandes) {
            commande.setLignes(findLignesByCommandeId(commande.getId()));
        }
        return commandes;
    }

    public CommandeClient findById(Long id) {
        String sql = "SELECT id, client_id, date_commande, statut "
                   + "FROM COMMANDE_CLIENT "
                   + "WHERE id = ?";
        List<CommandeClient> results = jdbcTemplate.query(sql, commandeClientRowMapper(), id);
        CommandeClient commande = results.stream().findFirst().orElse(null);
        if (commande != null) {
            commande.setLignes(findLignesByCommandeId(id));
        }
        return commande;
    }

    public CommandeClient save(CommandeClient commande) {
        String sql = "INSERT INTO COMMANDE_CLIENT (client_id, date_commande, statut) "
                   + "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, commande.getClient().getId());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(commande.getDateCommande()));
            ps.setString(3, commande.getStatut().name());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            commande.setId(key.longValue());
        }
        return commande;
    }

    public void updateStatut(Long commandeId, CommandeClient.StatutCommandeClient statut) {
        String sql = "UPDATE COMMANDE_CLIENT SET statut = ? WHERE id = ?";
        jdbcTemplate.update(sql, statut.name(), commandeId);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM COMMANDE_CLIENT WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public LigneCommandeClient addLigne(Long commandeId, Long platId, Integer quantite) {
        String sql = "INSERT INTO LIGNE_COMMANDE_CLIENT (commande_client_id, plat_id, quantite) "
                   + "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, commandeId);
            ps.setLong(2, platId);
            ps.setInt(3, quantite);
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return findLigneById(key.longValue());
        }
        return null;
    }

    public List<LigneCommandeClient> findLignesByCommandeId(Long commandeId) {
        String sql = "SELECT lcc.id, lcc.plat_id, lcc.quantite "
                   + "FROM LIGNE_COMMANDE_CLIENT lcc "
                   + "WHERE lcc.commande_client_id = ?";
        return jdbcTemplate.query(sql, new RowMapper<LigneCommandeClient>() {
            @Override
            public LigneCommandeClient mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long platId = rs.getLong("plat_id");
                Plat plat = platDao.findById(platId);
                LigneCommandeClient ligne = new LigneCommandeClient();
                ligne.setId(rs.getLong("id"));
                ligne.setPlat(plat);
                ligne.setQuantite(rs.getInt("quantite"));
                return ligne;
            }
        }, commandeId);
    }

    public LigneCommandeClient findLigneById(Long id) {
        String sql = "SELECT lcc.id, lcc.plat_id, lcc.quantite "
                   + "FROM LIGNE_COMMANDE_CLIENT lcc "
                   + "WHERE lcc.id = ?";
        List<LigneCommandeClient> results = jdbcTemplate.query(sql, new RowMapper<LigneCommandeClient>() {
            @Override
            public LigneCommandeClient mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long platId = rs.getLong("plat_id");
                Plat plat = platDao.findById(platId);
                LigneCommandeClient ligne = new LigneCommandeClient();
                ligne.setId(rs.getLong("id"));
                ligne.setPlat(plat);
                ligne.setQuantite(rs.getInt("quantite"));
                return ligne;
            }
        }, id);
        return results.stream().findFirst().orElse(null);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM COMMANDE_CLIENT WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public CommandeClient.StatutCommandeClient getStatut(Long id) {
        String sql = "SELECT statut FROM COMMANDE_CLIENT WHERE id = ?";
        return CommandeClient.StatutCommandeClient.valueOf(
                jdbcTemplate.queryForObject(sql, String.class, id));
    }
}