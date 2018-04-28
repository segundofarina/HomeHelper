package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PostDao;
import ar.edu.itba.paw.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostJdbcDao implements PostDao{

    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<Post> ROW_MAPPER = new RowMapper<Post>() {
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Post(rs.getInt("postId"), rs.getString("title"), rs.getString("description"), rs.getString("serviceType"));
        }
    };

    @Autowired
    public PostJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public List<Post> getPostWithUserId(int userId) {
        final List<Post> list = jdbcTemplate.query("SELECT postId, title, description, serviceName as serviceType FROM serviceProviders NATURAL JOIN posts NATURAL JOIN serviceTypes WHERE userId = ?;", ROW_MAPPER, userId);

        return list;
    }
}
