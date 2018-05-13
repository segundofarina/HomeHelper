package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.WZoneDao;
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
public class WZoneJdbcDao implements WZoneDao {

    @Autowired
    SProviderDao sProviderDao;

    @Autowired
    NeighborhoodDao neighborhoodDao;

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static class Row{
        int userId;
        int ngId;

        public Row(int userId, int ngId) {
            this.userId = userId;
            this.ngId = ngId;
        }

        public int getUserId() {
            return userId;
        }

        public int getNgId() {
            return ngId;
        }
    }

    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {

        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("userId"), rs.getInt("ngId"));
        }
    };

    @Autowired
    public WZoneJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("workingzones");


    }

    @Override
    public boolean insertWorkingZoneOfProvider(int userId, int ngId) {
        Optional<SProvider> provider = sProviderDao.getServiceProviderWithUserId(userId);
        if(!provider.isPresent() || !neighborhoodDao.getAllNeighborhoods().contains(new Neighborhood(ngId))){
            return false;
        }
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId",userId);
        args.put("ngId",ngId);
        jdbcInsert.execute(args);
        return true;
    }

    @Override
    public Set<Neighborhood> getWorkingZonesOfProvider(int providerId) {
        List<Row> list = jdbcTemplate.query("SELECT * FROM workingzones WHERE userId = ?;", ROW_MAPPER, providerId);
        if (list.isEmpty()) {
            return null;
        }
        Set<Neighborhood> neighborhoods = new HashSet<>();
        for(Row row : list){
            neighborhoods.add(new Neighborhood(row.ngId));
        }

        return neighborhoods;
    }

    @Override
    public Set<SProvider> getServiceProvidersWorkingIn(int ngId) {
        List<Row> list = jdbcTemplate.query("SELECT * FROM workingzones WHERE ngId = ?;", ROW_MAPPER, ngId);
        Set<SProvider> providers = new HashSet<>();
        for(Row row : list){
            providers.add(sProviderDao.getServiceProviderWithUserId(row.userId).get());
        }
        return providers;
    }
}
