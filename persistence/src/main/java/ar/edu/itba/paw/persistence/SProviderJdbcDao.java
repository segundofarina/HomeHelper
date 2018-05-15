package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.WZoneDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Aptitude;
import ar.edu.itba.paw.model.Neighborhood;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class SProviderJdbcDao implements SProviderDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    AptitudeDao aptitudeDao;

    @Autowired
    UserDao userDao;

    @Autowired
    WZoneDao wZoneDao;

    @Autowired
    public SProviderJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("serviceProviders");
    }



    /* Table ServiceProviders has been redefined to only have userId as it's columns*/


    private static class Row{
        int id;
        String description;

        public Row(int id, String description) {
            this.id = id;
            this.description = description;
        }

    }
    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("userId"),rs.getString("description"));
        }
    };


    /** SProviderDao implemented methods **/

    public Optional<SProvider> create(int userId, String description) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", userId);
        args.put("description",description);
        if(!userDao.findById(userId).isPresent()){
            return Optional.empty();
            /* O bien podria tirar una excepcion ya que no tendria que crear un Sprovider que no es user*/
        }

        jdbcInsert.execute(args);

        return Optional.of(new SProvider(userDao.findById(userId).get(),description, new ArrayList<Aptitude>() , new ArrayList<Neighborhood>()));

    }

    public List<SProvider> getServiceProviders() {
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders", ROW_MAPPER);

        List<SProvider> list = new ArrayList<SProvider>();

        for(Row row : dbRowsList) {
            list.add(new SProvider(userDao.findById(row.id).get(),row.description,aptitudeDao.getAptitudesOfUser(row.id),wZoneDao.getWorkingZonesOfProvider(row.id)));
        }

        return list;
    }

    public Optional<SProvider> getServiceProviderWithUserId(int userId) {
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders WHERE userId = ?", ROW_MAPPER, userId);


        if(dbRowsList.size() == 1) {
            return Optional.of(new SProvider(userDao.findById(userId).get(),dbRowsList.get(0).description,aptitudeDao.getAptitudesOfUser(userId),wZoneDao.getWorkingZonesOfProvider(userId)));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public boolean updateDescriptionOfServiceProvider(int userId, String description) {
        if(description == null){
            return false;
        }
        Optional<SProvider> provider = getServiceProviderWithUserId(userId);
        if (!provider.isPresent()) {
            return false;
        }
        return jdbcTemplate.update("UPDATE serviceProviders SET description =? WHERE userId =?", description, userId)==1;
    }

}
