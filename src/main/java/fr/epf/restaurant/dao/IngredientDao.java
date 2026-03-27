package fr.epf.restaurant.dao;

import fr.epf.restaurant.model.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IngredientDao {

    private final JdbcTemplate jdbcTemplate;

    public IngredientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Ingredient> ingredientRowMapper = new RowMapper<Ingredient>() {
        @Override
        public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getLong("id"));
            ingredient.setNom(rs.getString("nom"));
            ingredient.setUnite(rs.getString("unite"));
            ingredient.setStockActuel(rs.getDouble("stock_actuel"));
            ingredient.setSeuilAlerte(rs.getDouble("seuil_alerte"));
            return ingredient;
        }
    };

    public List<Ingredient> findAll() {
        String sql = "SELECT id, nom, unite, stock_actuel, seuil_alerte " +
                "FROM INGREDIENT " +
                "ORDER BY id";
        return jdbcTemplate.query(sql, ingredientRowMapper);
    }

    public Ingredient findById(Long id) {
        String sql = "SELECT id, nom, unite, stock_actuel, seuil_alerte " +
                "FROM INGREDIENT " +
                "WHERE id = ?";
        return jdbcTemplate.query(sql, ingredientRowMapper, id).stream().findFirst().orElse(null);
    }

    public List<Ingredient> findUnderAlertThreshold() {
        String sql = "SELECT id, nom, unite, stock_actuel, seuil_alerte " +
                "FROM INGREDIENT " +
                "WHERE stock_actuel < seuil_alerte " +
                "ORDER BY id";
        return jdbcTemplate.query(sql, ingredientRowMapper);
    }

    public void updateStock(Long ingredientId, Double newStock) {
        String sql = "UPDATE INGREDIENT SET stock_actuel = ? WHERE id = ?";
        jdbcTemplate.update(sql, newStock, ingredientId);
    }

    public void decrementStock(Long ingredientId, Double quantity) {
        String sql = "UPDATE INGREDIENT SET stock_actuel = stock_actuel - ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, ingredientId);
    }

    public void incrementStock(Long ingredientId, Double quantity) {
        String sql = "UPDATE INGREDIENT SET stock_actuel = stock_actuel + ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, ingredientId);
    }

    public Double getStock(Long ingredientId) {
        String sql = "SELECT stock_actuel FROM INGREDIENT WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, ingredientId);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM INGREDIENT WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}