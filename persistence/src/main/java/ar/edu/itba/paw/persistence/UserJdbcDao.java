package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserJdbcDao implements UserDao {
    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<User> ROW_MAPPER = new
            RowMapper<User>() {

                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("username"), rs.getInt("userid"));
        }
    };

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public User findById(int id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?;",
                ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


}