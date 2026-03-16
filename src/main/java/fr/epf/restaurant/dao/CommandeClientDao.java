// CommandeClientDao.java
package fr.epf.restaurant.dao;

import fr.epf.restaurant.dto.CommandeClientDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CommandeClientDao {
    private final JdbcTemplate jdbcTemplate;
    public CommandeClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<CommandeClientDTO> findAll() {
        return jdbcTemplate.query("SELECT * FROM COMMANDE_CLIENT",
            (rs, rowNum) -> new CommandeClientDTO(
                rs.getLong("id"),
                rs.getLong("client_id"),
                rs.getTimestamp("date_commande").toLocalDateTime(),
                rs.getString("statut")
            ));
    }
}