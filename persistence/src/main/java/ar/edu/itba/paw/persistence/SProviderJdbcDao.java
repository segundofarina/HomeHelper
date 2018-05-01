package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.SProviderDao;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.util.Optional.empty;

@Repository
public class SProviderJdbcDao implements SProviderDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    PostJdbcDao postDao;

    @Autowired
    UserJdbcDao userDao;

    @Autowired
    public SProviderJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("serviceProviders");
    }



    /* Table ServiceProviders has been redefined to only have userId as it's columns*/

    private final static RowMapper<Integer> ROW_MAPPER = new RowMapper<Integer>() {
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("userId");
        }
    };


    /** SProviderDao implemented methods **/

    public Optional<SProvider> create(int userId) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", userId);

        if(!userDao.findById(userId).isPresent()){
            return Optional.empty();
            /* O bien podria tirar una excepcion ya que no tendria que crear un Sprovider que no es user*/
        }

        jdbcInsert.execute(args);

        return Optional.of(new SProvider(postDao.getPostWithUserId(userId),userDao.findById(userId).get()));

    }

    public List<SProvider> getServiceProviders() {
        List<Integer> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders", ROW_MAPPER);

        List<SProvider> list = new ArrayList<SProvider>();

        for(Integer id : dbRowsList) {
            list.add(new SProvider( postDao.getPostWithUserId(id),userDao.findById(id).get()));
        }

        return list;
    }

    public Optional<SProvider> getServiceProviderWithUserId(int userId) {
        List<Integer> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders WHERE userId = ?"
                , ROW_MAPPER, userId);


        if(dbRowsList.size() == 1) {
            return Optional.of(new SProvider( postDao.getPostWithUserId(userId), userDao.findById(userId).get()));
        }else{
            return Optional.empty();
        }
    }
}
