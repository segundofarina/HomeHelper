package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.VerifyEmailDao;
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
import java.util.Optional;

@Repository
public class VerifyEmailJdbcDao implements VerifyEmailDao{


    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    private final static RowMapper<Integer> ROW_MAPPER_ID = new RowMapper<Integer>() {

        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("userid");

        }
    };

    private final static RowMapper<String> ROW_MAPPER_KEY = new RowMapper<String>() {

        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("key");

        }
    };

    @Autowired
    public VerifyEmailJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("verifyUsers");


    }

    @Override
    public boolean insert(int userId, String key){
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", userId);// la key es el nombre de la columna
        args.put("keyCode",key);

        try {
            jdbcInsert.execute(args);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public Optional<String> getKey(int userId) {

        List<String> list = jdbcTemplate.query("SELECT keyCode FROM verifyUsers WHERE userid = ?;", ROW_MAPPER_KEY, userId);

        if(list.size()==1){
            return Optional.of(list.get(0));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getUserId(String key) {
        List<Integer> list = jdbcTemplate.query("SELECT userid FROM verifyUsers WHERE keycode = ?;", ROW_MAPPER_ID, key);

        if(list.size()==1){
            return Optional.of(list.get(0));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteEntry(String key) {
        return jdbcTemplate.update("DELETE FROM verifyUsers WHERE keyCode = ?",key)>0;

    }


}
