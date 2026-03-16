// IngredientDao.java
package fr.epf.restaurant.dao;

import fr.epf.restaurant.dto.IngredientDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class IngredientDao {
    private final JdbcTemplate jdbcTemplate;
    public IngredientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<IngredientDTO> findAll() {
        return jdbcTemplate.query("SELECT * FROM INGREDIENT",
            (rs, rowNum) -> new IngredientDTO(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("unite"),
                rs.getDouble("stock_actuel"),
                rs.getDouble("seuil_alerte")
            ));
    }
    public List<IngredientDTO> findAlerte() {
        return jdbcTemplate.query("SELECT * FROM INGREDIENT WHERE stock_actuel <= seuil_alerte",
            (rs, rowNum) -> new IngredientDTO(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("unite"),
                rs.getDouble("stock_actuel"),
                rs.getDouble("seuil_alerte")
            ));
    }
}