// FournisseurDao.java
package fr.epf.restaurant.dao;

import fr.epf.restaurant.dto.FournisseurDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FournisseurDao {
    private final JdbcTemplate jdbcTemplate;
    public FournisseurDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<FournisseurDTO> findAll() {
        return jdbcTemplate.query("SELECT * FROM FOURNISSEUR",
            (rs, rowNum) -> new FournisseurDTO(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("contact"),
                rs.getString("email")
            ));
    }
}