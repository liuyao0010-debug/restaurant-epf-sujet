// ClientDao.java
package fr.epf.restaurant.dao;

import fr.epf.restaurant.dto.ClientDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDao {
    private final JdbcTemplate jdbcTemplate;
    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<ClientDTO> findAll() {
        return jdbcTemplate.query("SELECT * FROM CLIENT",
            (rs, rowNum) -> new ClientDTO(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone")
            ));
    }
}