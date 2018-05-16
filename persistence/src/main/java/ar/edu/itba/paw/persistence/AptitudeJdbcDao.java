package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.model.Aptitude;
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
public class AptitudeJdbcDao implements AptitudeDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    STypeDao sTypeDao;

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    public AptitudeJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("aptitudes").usingGeneratedKeyColumns("aptitudeId");
    }

    private static class Row {
        int aptitudeId;
        int userId;
        int serviceTypeId;
        String description;

        public Row(int aptitudeId, int userId, int serviceTypeId, String description) {
            this.aptitudeId = aptitudeId;
            this.userId = userId;
            this.serviceTypeId = serviceTypeId;
            this.description = description;
        }

        public int getAptitudeId() {
            return aptitudeId;
        }

        public int getUserId() {
            return userId;
        }

        public int getServiceTypeId() {
            return serviceTypeId;
        }

        public String getDescription() {
            return description;
        }
    }

    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("aptitudeId"), rs.getInt("userId"), rs.getInt("serviceTypeId"), rs.getString("description"));
        }
    };

    public List<Aptitude> getAptitudesOfUser(int id) {
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM aptitudes WHERE userId =? ", ROW_MAPPER, id);

        List<Aptitude> aptitudes = new ArrayList<Aptitude>();

        if(dbRowsList.isEmpty()){
            return aptitudes;
        }

        for(Row row : dbRowsList){
            aptitudes.add(new Aptitude(row.aptitudeId,sTypeDao.getServiceTypeWithId(row.serviceTypeId).get(),row.description,reviewDao.getReviewsOfAptitude(row.aptitudeId)));
        }

        return aptitudes;
    }

    @Override
    public void insertAptitude(int sProviderId, int serviceId, String description) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", sProviderId);
        args.put("serviceTypeId", serviceId);
        args.put("description", description);
        jdbcInsert.execute(args);
    }

    @Override
    public boolean updateServiceTypeOfAptitude(int aptId, int stId) {
        return jdbcTemplate.update("UPDATE aptitudes SET serviceTypeId = ? WHERE aptitudeId = ?", stId, aptId) != 0;
    }

    @Override
    public boolean removeAptitude(int aptId) {
        reviewDao.removeReviewsOfAptitude(aptId);
        return jdbcTemplate.update("DELETE FROM aptitudes WHERE aptitudeId = ?", aptId) != 0;
    }

    @Override
    public int getAptitudeId(int userId, int stId) {
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM aptitudes WHERE userId =? AND serviceTypeId =?", ROW_MAPPER, userId,stId);
        if(dbRowsList.size() == 0){
            return -1;
        }
        return dbRowsList.get(0).aptitudeId;
    }

    @Override
    public boolean updateDescriptionOfAptitude(int aptId, String description) {
        return jdbcTemplate.update("UPDATE aptitudes SET description = ? WHERE aptitudeId = ?", description, aptId)!=0;
    }

    @Override
    public Optional<Aptitude> getAptitude(int id) {
        List<Row> dbRowList = jdbcTemplate.query("SELECT * FROM aptitudes WHERE aptitudeId =? ", ROW_MAPPER, id);
        if(dbRowList.isEmpty()) {
            return Optional.empty();
        }
        Row row = dbRowList.get(0);

        return Optional.of(new Aptitude(row.aptitudeId, sTypeDao.getServiceTypeWithId(row.serviceTypeId).get(), row.description, reviewDao.getReviewsOfAptitude(row.aptitudeId)));
    }


}
