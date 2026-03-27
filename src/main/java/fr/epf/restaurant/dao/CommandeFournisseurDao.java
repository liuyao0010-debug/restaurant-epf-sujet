package fr.epf.restaurant.dao;

import fr.epf.restaurant.model.CommandeFournisseur;
import fr.epf.restaurant.model.LigneCommandeFournisseur;
import fr.epf.restaurant.model.Fournisseur;
import fr.epf.restaurant.model.Ingredient;
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
public class CommandeFournisseurDao {

    private final JdbcTemplate jdbcTemplate;
    private final FournisseurDao fournisseurDao;
    private final IngredientDao ingredientDao;

    public CommandeFournisseurDao(JdbcTemplate jdbcTemplate,
                                  FournisseurDao fournisseurDao,
                                  IngredientDao ingredientDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.fournisseurDao = fournisseurDao;
        this.ingredientDao = ingredientDao;
    }

    private RowMapper<CommandeFournisseur> commandeFournisseurRowMapper() {
        return new RowMapper<CommandeFournisseur>() {
            @Override
            public CommandeFournisseur mapRow(ResultSet rs, int rowNum) throws SQLException {
                CommandeFournisseur commande = new CommandeFournisseur();
                commande.setId(rs.getLong("id"));
                Long fournisseurId = rs.getLong("fournisseur_id");
                Fournisseur fournisseur = fournisseurDao.findById(fournisseurId);
                commande.setFournisseur(fournisseur);
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commande.setStatut(CommandeFournisseur.StatutCommandeFournisseur.valueOf(
                        rs.getString("statut")));
                return commande;
            }
        };
    }

    public List<CommandeFournisseur> findAll() {
        String sql = "SELECT id, fournisseur_id, date_commande, statut "
                   + "FROM COMMANDE_FOURNISSEUR "
                   + "ORDER BY id DESC";
        List<CommandeFournisseur> commandes = jdbcTemplate.query(sql, commandeFournisseurRowMapper());
        for (CommandeFournisseur commande : commandes) {
            commande.setLignes(findLignesByCommandeId(commande.getId()));
        }
        return commandes;
    }

    public List<CommandeFournisseur> findByStatut(CommandeFournisseur.StatutCommandeFournisseur statut) {
        String sql = "SELECT id, fournisseur_id, date_commande, statut "
                   + "FROM COMMANDE_FOURNISSEUR "
                   + "WHERE statut = ? "
                   + "ORDER BY id DESC";
        List<CommandeFournisseur> commandes = jdbcTemplate.query(
                sql, commandeFournisseurRowMapper(), statut.name());
        for (CommandeFournisseur commande : commandes) {
            commande.setLignes(findLignesByCommandeId(commande.getId()));
        }
        return commandes;
    }

    public CommandeFournisseur findById(Long id) {
        String sql = "SELECT id, fournisseur_id, date_commande, statut "
                   + "FROM COMMANDE_FOURNISSEUR "
                   + "WHERE id = ?";
        List<CommandeFournisseur> results = jdbcTemplate.query(sql, commandeFournisseurRowMapper(), id);
        CommandeFournisseur commande = results.stream().findFirst().orElse(null);
        if (commande != null) {
            commande.setLignes(findLignesByCommandeId(id));
        }
        return commande;
    }

    public CommandeFournisseur save(CommandeFournisseur commande) {
        String sql = "INSERT INTO COMMANDE_FOURNISSEUR (fournisseur_id, date_commande, statut) "
                   + "VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, commande.getFournisseur().getId());
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

    public void updateStatut(Long commandeId, CommandeFournisseur.StatutCommandeFournisseur statut) {
        String sql = "UPDATE COMMANDE_FOURNISSEUR SET statut = ? WHERE id = ?";
        jdbcTemplate.update(sql, statut.name(), commandeId);
    }

    public void deleteById(Long id) {
        // 先删除关联的订单明细
        String deleteLignesSql = "DELETE FROM LIGNE_COMMANDE_FOURNISSEUR WHERE commande_fournisseur_id = ?";
        jdbcTemplate.update(deleteLignesSql, id);
        // 再删除订单
        String deleteCommandeSql = "DELETE FROM COMMANDE_FOURNISSEUR WHERE id = ?";
        jdbcTemplate.update(deleteCommandeSql, id);
    }

    public LigneCommandeFournisseur addLigne(Long commandeId, Long ingredientId,
                                             Double quantite, java.math.BigDecimal prixUnitaire) {
        String sql = "INSERT INTO LIGNE_COMMANDE_FOURNISSEUR "
                   + "(commande_fournisseur_id, ingredient_id, quantite_commandee, prix_unitaire) "
                   + "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, commandeId);
            ps.setLong(2, ingredientId);
            ps.setDouble(3, quantite);
            ps.setBigDecimal(4, prixUnitaire);
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            return findLigneById(key.longValue());
        }
        return null;
    }

    public List<LigneCommandeFournisseur> findLignesByCommandeId(Long commandeId) {
        String sql = "SELECT lcf.id, lcf.ingredient_id, lcf.quantite_commandee, lcf.prix_unitaire "
                   + "FROM LIGNE_COMMANDE_FOURNISSEUR lcf "
                   + "WHERE lcf.commande_fournisseur_id = ?";
        return jdbcTemplate.query(sql, new RowMapper<LigneCommandeFournisseur>() {
            @Override
            public LigneCommandeFournisseur mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long ingredientId = rs.getLong("ingredient_id");
                Ingredient ingredient = ingredientDao.findById(ingredientId);
                LigneCommandeFournisseur ligne = new LigneCommandeFournisseur();
                ligne.setId(rs.getLong("id"));
                ligne.setIngredient(ingredient);
                ligne.setQuantiteCommandee(rs.getDouble("quantite_commandee"));
                ligne.setPrixUnitaire(rs.getBigDecimal("prix_unitaire"));
                return ligne;
            }
        }, commandeId);
    }

    public LigneCommandeFournisseur findLigneById(Long id) {
        String sql = "SELECT lcf.id, lcf.ingredient_id, lcf.quantite_commandee, lcf.prix_unitaire "
                   + "FROM LIGNE_COMMANDE_FOURNISSEUR lcf "
                   + "WHERE lcf.id = ?";
        List<LigneCommandeFournisseur> results = jdbcTemplate.query(sql, new RowMapper<LigneCommandeFournisseur>() {
            @Override
            public LigneCommandeFournisseur mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long ingredientId = rs.getLong("ingredient_id");
                Ingredient ingredient = ingredientDao.findById(ingredientId);
                LigneCommandeFournisseur ligne = new LigneCommandeFournisseur();
                ligne.setId(rs.getLong("id"));
                ligne.setIngredient(ingredient);
                ligne.setQuantiteCommandee(rs.getDouble("quantite_commandee"));
                ligne.setPrixUnitaire(rs.getBigDecimal("prix_unitaire"));
                return ligne;
            }
        }, id);
        return results.stream().findFirst().orElse(null);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM COMMANDE_FOURNISSEUR WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public CommandeFournisseur.StatutCommandeFournisseur getStatut(Long id) {
        String sql = "SELECT statut FROM COMMANDE_FOURNISSEUR WHERE id = ?";
        return CommandeFournisseur.StatutCommandeFournisseur.valueOf(
                jdbcTemplate.queryForObject(sql, String.class, id));
    }
}