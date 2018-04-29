package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.SProviderDao;
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
    public SProviderJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("serviceProviders");
    }


    private static class DbRow {
        private int userId;
        private int postId;

        public DbRow(int userId, int postId) {
            this.userId = userId;
            this.postId = postId;
        }
    }


    /* Row mapper for every row in the table */
    private final static RowMapper<DbRow> ROW_MAPPER = new RowMapper<DbRow>() {
        public DbRow mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new DbRow(rs.getInt("userId"), rs.getInt("postId"));
        }
    };




    /** SProviderDao implemented methods **/

    public SProvider create(int userId, int postId) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", userId);
        args.put("postId", postId);

        jdbcInsert.execute(args);

        return null;

    }

    public List<SProvider> getServiceProviders() {
        List<DbRow> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders", ROW_MAPPER);

        Map<Integer, Set<Integer>> map = new HashMap<Integer, Set<Integer>>();

        for(DbRow row : dbRowsList) {
            if(!map.containsKey(row.userId)) {
                Set<Integer> set = new HashSet<Integer>();
                set.add(row.postId);
                map.put(row.userId, set);
            } else {
                map.get(row.userId).add(row.postId);
            }
        }


        List<SProvider> list = new ArrayList<SProvider>();

        for(Integer user : map.keySet()) {
            list.add(new SProvider(user, map.get(user)));
        }

        return list;
    }

    public SProvider getServiceProviderWithUserId(int userId) {
        List<DbRow> dbRowsList = jdbcTemplate.query("SELECT * FROM serviceProviders WHERE userId = ?", ROW_MAPPER, userId);

        Set<Integer> posts = new HashSet<Integer>();

        for(DbRow row : dbRowsList) {
            posts.add(row.postId);
        }

        return new SProvider(userId, posts);
    }
}
