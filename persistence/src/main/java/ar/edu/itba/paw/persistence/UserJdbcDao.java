package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserJdbcDao implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<User> ROW_MAPPER = new
            RowMapper<User>() {

                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("username"),rs.getString("password"), rs.getInt("userid"));
        }
    };

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users")
                .usingGeneratedKeyColumns("userid");


    }

    public User findById(int id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?;",
                ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    public User create(final String username, final String password) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("username", username);// la key es el nombre de la columna
        args.put("password",password);
        final Number userId = jdbcInsert.executeAndReturnKey(args);

        return new User(username, password,userId.intValue());
    }




}