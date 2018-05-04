package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.STypeDao;
import ar.edu.itba.paw.model.ServiceType;
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
public class STypeJdbcDao implements STypeDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public STypeJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("serviceTypes").usingGeneratedKeyColumns("serviceTypeId");
    }


    /* Row mapper for every row in the table */
    private final static RowMapper<ServiceType> ROW_MAPPER = new RowMapper<ServiceType>() {
        public ServiceType mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ServiceType(rs.getInt("serviceTypeId"), rs.getString("serviceName"));
        }
    };


    /** STypeDao implemented methods **/

    public ServiceType create(String name) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("serviceName", name);

        final Number serviceTypeId = jdbcInsert.executeAndReturnKey(args);

        return new ServiceType(serviceTypeId.intValue(), name);
    }

    public List<ServiceType> getServiceTypes() {
        final List<ServiceType> list = jdbcTemplate.query("SELECT * FROM serviceTypes", ROW_MAPPER);
        return list;
    }

    public ServiceType getServiceTypeWithId(int serviceTypeId) {
        final List<ServiceType> list = jdbcTemplate.query("SELECT * FROM serviceTypes WHERE serviceTypeId = ?", ROW_MAPPER, serviceTypeId);

        if(list.size() == 0) {
            return null; // SHOULD RETURN AN OPTIONAL
        }

        return list.get(0);
    }

    public ServiceType updateServiceTypeWithId(int serviceTypeId, String newServiceName) {
        jdbcTemplate.update("UPDATE serviceTypes SET serviceName = ? WHERE serviceTypeId = ?", newServiceName, serviceTypeId);

        return getServiceTypeWithId(serviceTypeId);
    }
}
