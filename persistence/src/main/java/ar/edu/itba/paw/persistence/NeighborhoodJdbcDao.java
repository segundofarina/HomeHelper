package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
//import ar.edu.itba.paw.interfaces.daos.SProviderDao;
//import ar.edu.itba.paw.model.Neighborhood;
//import ar.edu.itba.paw.model.Review;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//import javax.sql.DataSource;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//public class NeighborhoodJdbcDao implements NeighborhoodDao {
//
//
//    @Autowired
//    SProviderDao sProviderDao;
//
//    private JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert jdbcInsert;
//
//    @Autowired
//    public NeighborhoodJdbcDao(final DataSource ds) {
//        jdbcTemplate = new JdbcTemplate(ds);
//        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("neighborhoods").usingGeneratedKeyColumns("ngid");
//    }
//
//    /* Row mapper for every row in the table */
//    private final static RowMapper<Neighborhood> ROW_MAPPER = new RowMapper<Neighborhood>() {
//        public Neighborhood mapRow(ResultSet rs, int rowNum) throws SQLException {
//            return new Neighborhood(rs.getInt("ngId"));
//        }
//    };
//
//    @Override
//    public int insertNeighborhood(String ngname) {
//
//        final Map<String, Object> args = new HashMap<String, Object>();
//        args.put("ngname",ngname);
//        return  jdbcInsert.executeAndReturnKey(args).intValue();
//    }
//
//    @Override
//    public List<Neighborhood> getAllNeighborhoods() {
//        List<Neighborhood> dbRowsList = jdbcTemplate.query("SELECT * FROM neighborhoods", ROW_MAPPER);
//        List<Neighborhood> ans = new ArrayList<>();
//        ans.addAll(dbRowsList);
//        return ans;
//    }
//}
