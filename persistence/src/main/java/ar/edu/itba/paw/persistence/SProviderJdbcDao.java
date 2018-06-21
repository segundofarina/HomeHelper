package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.WZoneDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.*;
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
        User user;

        public Row(int id, String description, User user) {
            this.id = id;
            this.description = description;
            this.user = user;
        }

    }
    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("userId"),rs.getString("description"),
                    new User(rs.getString("username"), rs.getInt("userid"),rs.getString("password"),
                            rs.getString("firstname"),rs.getString("lastname"),rs.getString("email"),
                            rs.getString("phone"),rs.getString("address"),rs.getBytes("image"),
                            rs.getBoolean("verified")));
        }
    };

    private final static RowMapper<Integer> ROW_MAPPER_ID_NG = new RowMapper<Integer>() {
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("userid");
        }
    };


    /** SProviderDao implemented methods **/

    public Optional<SProvider> create(int userId, String description) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", userId);
        args.put("description",description);
        jdbcInsert.execute(args);
        return Optional.of(new SProvider(userDao.findById(userId).get(),description, new ArrayList<Aptitude>() , new ArrayList<Neighborhood>()));

    }

    public List<SProvider> getServiceProviders() {
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders NATURAL JOIN users", ROW_MAPPER);

        List<SProvider> list = new ArrayList<SProvider>();

        for(Row row : dbRowsList) {
            list.add(new SProvider(row.user,row.description,aptitudeDao.getAptitudesOfUser(row.id),wZoneDao.getWorkingZonesOfProvider(row.id)));
        }

        return list;
    }

    public Optional<SProvider> getServiceProviderWithUserId(int userId) {
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders NATURAL JOIN users WHERE userId = ?", ROW_MAPPER, userId);
        if(dbRowsList.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new SProvider(dbRowsList.get(0).user,dbRowsList.get(0).description,aptitudeDao.getAptitudesOfUser(userId),wZoneDao.getWorkingZonesOfProvider(userId)));
    }

    @Override
    public List<SProvider> getServiceProvidersByNeighborhoodAndServiceType(int ngId, int stId) {
        List<Integer> dbList = jdbcTemplate.query("select userid from workingzones NATURAL JOIN aptitudes WHERE serviceTypeId = ? AND ngId =?;", ROW_MAPPER_ID_NG,stId,ngId);
        List<SProvider>list = new ArrayList<SProvider>();
        if(dbList.isEmpty()){
            return list;
        }
        for(Integer i : dbList){
            list.add(getServiceProviderWithUserId(i.intValue()).get());
        }

        return list;

    }


    @Override
    public void updateDescriptionOfServiceProvider(int userId, String description) {
        jdbcTemplate.update("UPDATE serviceProviders SET description =? WHERE userId =?", description, userId);
    }




}
