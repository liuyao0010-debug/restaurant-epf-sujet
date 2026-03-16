// CommandeFournisseurDao.java
package fr.epf.restaurant.dao;

import fr.epf.restaurant.dto.CommandeFournisseurDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CommandeFournisseurDao {
    private final JdbcTemplate jdbcTemplate;
    public CommandeFournisseurDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<CommandeFournisseurDTO> findAll() {
        return jdbcTemplate.query("SELECT * FROM COMMANDE_FOURNISSEUR",
            (rs, rowNum) -> new CommandeFournisseurDTO(
                rs.getLong("id"),
                rs.getLong("fournisseur_id"),
                rs.getTimestamp("date_commande").toLocalDateTime(),
                rs.getString("statut")
            ));
    }
}