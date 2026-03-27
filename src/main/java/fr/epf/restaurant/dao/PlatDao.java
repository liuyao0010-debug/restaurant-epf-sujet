package fr.epf.restaurant.dao;

import fr.epf.restaurant.model.Plat;
import fr.epf.restaurant.model.PlatIngredient;
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
public class PlatDao {

    private final JdbcTemplate jdbcTemplate;
    private final IngredientDao ingredientDao;

    public PlatDao(JdbcTemplate jdbcTemplate, IngredientDao ingredientDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.ingredientDao = ingredientDao;
    }

    private final RowMapper<Plat> platRowMapper = new RowMapper<Plat>() {
        @Override
        public Plat mapRow(ResultSet rs, int rowNum) throws SQLException {
            Plat plat = new Plat();
            plat.setId(rs.getLong("id"));
            plat.setNom(rs.getString("nom"));
            plat.setDescription(rs.getString("description"));
            plat.setPrix(rs.getBigDecimal("prix"));
            return plat;
        }
    };

    public List<Plat> findAll() {
        String sql = "SELECT id, nom, description, prix FROM PLAT ORDER BY id";
        List<Plat> plats = jdbcTemplate.query(sql, platRowMapper);
        for (Plat plat : plats) {
            plat.setIngredients(findIngredientsByPlatId(plat.getId()));
        }
        return plats;
    }

    public Plat findById(Long id) {
        String sql = "SELECT id, nom, description, prix FROM PLAT WHERE id = ?";
        Plat plat = jdbcTemplate.query(sql, platRowMapper, id).stream().findFirst().orElse(null);
        if (plat != null) {
            plat.setIngredients(findIngredientsByPlatId(id));
        }
        return plat;
    }



    public Plat save(Plat plat) {
    String sql = "INSERT INTO PLAT (nom, description, prix) VALUES (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
        ps.setString(1, plat.getNom());
        ps.setString(2, plat.getDescription());
        ps.setBigDecimal(3, plat.getPrix());
        return ps;
    }, keyHolder);
    Long id = keyHolder.getKey().longValue();
    plat.setId(id);
    return plat;
    }

    public void addPlatIngredient(Long platId, Long ingredientId, Double quantiteRequise) {
        String sql = "INSERT INTO PLAT_INGREDIENT (plat_id, ingredient_id, quantite_requise) "
                   + "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, platId, ingredientId, quantiteRequise);
    }

    public List<PlatIngredient> findIngredientsByPlatId(Long platId) {
        String sql = "SELECT pi.ingredient_id, pi.quantite_requise "
                   + "FROM PLAT_INGREDIENT pi "
                   + "WHERE pi.plat_id = ?";
        return jdbcTemplate.query(sql, new RowMapper<PlatIngredient>() {
            @Override
            public PlatIngredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long ingredientId = rs.getLong("ingredient_id");
                Ingredient ingredient = ingredientDao.findById(ingredientId);
                PlatIngredient platIngredient = new PlatIngredient();
                platIngredient.setIngredient(ingredient);
                platIngredient.setQuantiteRequise(rs.getDouble("quantite_requise"));
                return platIngredient;
            }
        }, platId);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM PLAT WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}