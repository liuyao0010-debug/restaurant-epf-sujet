package fr.epf.restaurant.dao;

import fr.epf.restaurant.model.Client;
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
public class ClientDao {

    private final JdbcTemplate jdbcTemplate;

    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Client> clientRowMapper = new RowMapper<Client>() {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setId(rs.getLong("id"));
            client.setNom(rs.getString("nom"));
            client.setPrenom(rs.getString("prenom"));
            client.setEmail(rs.getString("email"));
            client.setTelephone(rs.getString("telephone"));
            return client;
        }
    };

    public List<Client> findAll() {
        String sql = "SELECT id, nom, prenom, email, telephone FROM CLIENT ORDER BY id";
        return jdbcTemplate.query(sql, clientRowMapper);
    }

    public Client findById(Long id) {
        String sql = "SELECT id, nom, prenom, email, telephone FROM CLIENT WHERE id = ?";
        return jdbcTemplate.query(sql, clientRowMapper, id).stream().findFirst().orElse(null);
    }


    public Client save(Client client) {
    String sql = "INSERT INTO CLIENT (nom, prenom, email, telephone) VALUES (?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
        ps.setString(1, client.getNom());
        ps.setString(2, client.getPrenom());
        ps.setString(3, client.getEmail());
        ps.setString(4, client.getTelephone());
        return ps;
    }, keyHolder);
    Long id = keyHolder.getKey().longValue();
    client.setId(id);
    return client;
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM CLIENT WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}