package fr.epf.restaurant.dao;

import fr.epf.restaurant.dto.PlatDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlatDao {

    private final JdbcTemplate jdbcTemplate;

    public PlatDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PlatDTO> findAll() {
        String sql = "SELECT * FROM PLAT";
        RowMapper<PlatDTO> rowMapper = (rs, rowNum) -> new PlatDTO(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("description"),
                rs.getDouble("prix")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    public PlatDTO findById(Long id) {
        String sql = "SELECT * FROM PLAT WHERE id = ?";
        RowMapper<PlatDTO> rowMapper = (rs, rowNum) -> new PlatDTO(
                rs.getLong("id"),
                rs.getString("nom"),
                rs.getString("description"),
                rs.getDouble("prix")
        );
        // 使用 query() + lambda 替代 deprecated queryForObject
        List<PlatDTO> results = jdbcTemplate.query(sql, rowMapper, id);
        if (results.isEmpty()) {
            throw new RuntimeException("Plat not found");
        }
        return results.get(0);
    }
}