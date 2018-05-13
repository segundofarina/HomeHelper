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

@Repository
public class VerifyEmailJdbcDao implements VerifyEmailDao{


    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    private final static RowMapper<String> ROW_MAPPER = new RowMapper<String>() {

        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("key");

        }
    };

    @Autowired
    public VerifyEmailJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("verifyUsers");


    }

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

    public String getKey(int userId) {

        List<String> list = jdbcTemplate.query("SELECT keyCode FROM users WHERE userid = ?;", ROW_MAPPER, userId);

        if(list.size()==1){
            return list.get(0);
        }else{
            throw new IllegalArgumentException();
        }
    }


}
