package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class UserJdbcDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<User> ROW_MAPPER = new RowMapper<User>() {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new User(rs.getString("username"), rs.getInt("userid"),rs.getString("password"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("email"),rs.getString("phone"),rs.getString("address"),rs.getBytes("image"),rs.getBoolean("verified"));

        }
    };

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("userid");
    }

    @Override
    public Optional<User> findById(int id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?;", ROW_MAPPER, id);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    @Override
    public boolean updatePasswordOfUser(int userId, String password) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE users SET password =? WHERE userid =?", password, userId);
        return true;
    }

    @Override
    public boolean updateFirstNameOfUser(int userId, String firstname) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE users SET firstname =? WHERE userid =?", firstname, userId);
        return true;
    }

    @Override
    public boolean updateLastNameOfUser(int userId, String lastname) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE users SET lastname =? WHERE userid =?", lastname, userId);
        return true;
    }

    @Override
    public boolean updateEmailOfUser(int userId, String email) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE users SET email =? WHERE userid =?", email, userId);
        return true;
    }

    @Override
    public boolean updatePhoneOfUser(int userId, String phone) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE users SET phone =? WHERE userid =?", phone, userId);
        return true;
    }

    @Override
    public boolean updateImageOfUser(int userId, byte[] image) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE users SET image =? WHERE userid =?", image, userId);
        return true;
    }

    @Override
    public boolean updateAddressOfUser(int userId, String address) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE users SET address =? WHERE userid =?", address, userId);
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE username = ?;",
                ROW_MAPPER, username);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }


    public User create(String username, String password, String firstname, String lastname, String email, String phone,String address,byte[] image) {

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("username", username);// la key es el nombre de la columna
        args.put("password",password);
        args.put("firstname",firstname);
        args.put("lastname",lastname);
        args.put("email",email);
        args.put("phone",phone);
        args.put("image",image);
        args.put("address", address);
        args.put("verified",false);
        final Number userId = jdbcInsert.executeAndReturnKey(args);
        return new User(username,userId.intValue(), password, firstname, lastname, email, phone,address,image,false);

    }

    @Override
    public User verifyUser(int userId) {
        Optional<User> user = findById(userId);
        if (!user.isPresent()) {
            return null;
        }
        jdbcTemplate.update("UPDATE users SET verified =? WHERE userid =?", true, userId);
        return user.get();
    }


}
