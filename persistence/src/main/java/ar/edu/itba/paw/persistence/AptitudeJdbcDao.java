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
    }

    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("aptitudeId"), rs.getInt("userId"), rs.getInt("serviceTypeId"), rs.getString("description"));
        }
    };

    public List<Aptitude> getAptitudesOfUser(int id) {
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM aptitudes WHERE userId =? ", ROW_MAPPER, id);

        List<Aptitude> aptitudes = new ArrayList<Aptitude>();


        for(Row row : dbRowsList){
            aptitudes.add(new Aptitude(row.aptitudeId,sTypeDao.getServiceTypeWithId(row.serviceTypeId).get(),row.description,reviewDao.getReviewsOfAptitude(row.aptitudeId)));
        }

        return aptitudes;
    }

    @Override
    public boolean insertAptitude(int sProviderId, int serviceId, String description) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", sProviderId);
        args.put("serviceTypeId", serviceId);
        args.put("description", description);

        try {
            jdbcInsert.execute(args);
        } catch (Exception e) {
            return false;
        }


        return true;
    }

    @Override
    public boolean updateAptitude(int aptId, String description) {
        try {
            jdbcTemplate.update("UPDATE aptitudes SET description = ? WHERE aptitudeId = ?", description, aptId);
        } catch (Exception e) {
            return false;
        }
        List<Row> list;

        try {
            list = jdbcTemplate.query("SELECT * FROM aptitudes WHERE aptitudeId = ?", ROW_MAPPER, aptId);
        } catch (Exception e) {
            return false;
        }
        if (list.size() == 0) {
            return false;
        }
        if (list.get(0).description.equals(description)) {
                return true;
        }
        return false;

    }


}
