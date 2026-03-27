package fr.epf.restaurant.dao;

import fr.epf.restaurant.model.Fournisseur;
import fr.epf.restaurant.model.FournisseurIngredient;
import fr.epf.restaurant.model.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FournisseurDao {

    private final JdbcTemplate jdbcTemplate;
    private final IngredientDao ingredientDao;

    public FournisseurDao(JdbcTemplate jdbcTemplate, IngredientDao ingredientDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.ingredientDao = ingredientDao;
    }

    private final RowMapper<Fournisseur> fournisseurRowMapper = new RowMapper<Fournisseur>() {
        @Override
        public Fournisseur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setId(rs.getLong("id"));
            fournisseur.setNom(rs.getString("nom"));
            fournisseur.setContact(rs.getString("contact"));
            fournisseur.setEmail(rs.getString("email"));
            return fournisseur;
        }
    };

    public List<Fournisseur> findAll() {
        String sql = "SELECT id, nom, contact, email FROM FOURNISSEUR ORDER BY id";
        return jdbcTemplate.query(sql, fournisseurRowMapper);
    }

    public Fournisseur findById(Long id) {
        String sql = "SELECT id, nom, contact, email FROM FOURNISSEUR WHERE id = ?";
        return jdbcTemplate.query(sql, fournisseurRowMapper, id).stream().findFirst().orElse(null);
    }


    public Fournisseur save(Fournisseur fournisseur) {
    String sql = "INSERT INTO FOURNISSEUR (nom, contact, email) VALUES (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
        ps.setString(1, fournisseur.getNom());
        ps.setString(2, fournisseur.getContact());
        ps.setString(3, fournisseur.getEmail());
        return ps;
    }, keyHolder);
    Long id = keyHolder.getKey().longValue();
    fournisseur.setId(id);
    return fournisseur;
    }

    public List<FournisseurIngredient> findCatalogueByFournisseurId(Long fournisseurId) {
        String sql = "SELECT fi.ingredient_id, fi.prix_unitaire "
                   + "FROM FOURNISSEUR_INGREDIENT fi "
                   + "WHERE fi.fournisseur_id = ?";
        Fournisseur fournisseur = findById(fournisseurId);
        if (fournisseur == null) {
            return List.of();
        }
        return jdbcTemplate.query(sql, new RowMapper<FournisseurIngredient>() {
            @Override
            public FournisseurIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long ingredientId = rs.getLong("ingredient_id");
                Ingredient ingredient = ingredientDao.findById(ingredientId);
                FournisseurIngredient fi = new FournisseurIngredient();
                fi.setFournisseur(fournisseur);
                fi.setIngredient(ingredient);
                fi.setPrixUnitaire(rs.getBigDecimal("prix_unitaire"));
                return fi;
            }
        }, fournisseurId);
    }

    public BigDecimal findPrixUnitaire(Long fournisseurId, Long ingredientId) {
        String sql = "SELECT prix_unitaire FROM FOURNISSEUR_INGREDIENT "
                   + "WHERE fournisseur_id = ? AND ingredient_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, fournisseurId, ingredientId);
    }

    public List<FournisseurIngredient> findCheapestSuppliersForIngredient(Long ingredientId) {
        String sql = "SELECT f.id, f.nom, f.contact, f.email, fi.prix_unitaire "
                   + "FROM FOURNISSEUR_INGREDIENT fi "
                   + "JOIN FOURNISSEUR f ON fi.fournisseur_id = f.id "
                   + "WHERE fi.ingredient_id = ? "
                   + "ORDER BY fi.prix_unitaire ASC";
        Ingredient ingredient = ingredientDao.findById(ingredientId);
        if (ingredient == null) {
            return List.of();
        }
        return jdbcTemplate.query(sql, new RowMapper<FournisseurIngredient>() {
            @Override
            public FournisseurIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                Fournisseur fournisseur = new Fournisseur();
                fournisseur.setId(rs.getLong("id"));
                fournisseur.setNom(rs.getString("nom"));
                fournisseur.setContact(rs.getString("contact"));
                fournisseur.setEmail(rs.getString("email"));
                FournisseurIngredient fi = new FournisseurIngredient();
                fi.setFournisseur(fournisseur);
                fi.setIngredient(ingredient);
                fi.setPrixUnitaire(rs.getBigDecimal("prix_unitaire"));
                return fi;
            }
        }, ingredientId);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM FOURNISSEUR WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}