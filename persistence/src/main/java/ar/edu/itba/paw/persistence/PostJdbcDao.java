package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PostDao;
import ar.edu.itba.paw.interfaces.SProviderDao;
import ar.edu.itba.paw.model.Post;
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
public class PostJdbcDao implements PostDao{

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    private SProviderDao sProviderDao;

    private final static RowMapper<Post> ROW_MAPPER = new RowMapper<Post>() {
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Post(rs.getInt("postId"), rs.getString("title"), rs.getString("description"), rs.getString("serviceType"));
        }
    };

    @Autowired
    public PostJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("posts").usingGeneratedKeyColumns("postId");

    }

    public List<Post> getPostWithUserId(int userId) {
        final List<Post> list = jdbcTemplate.query("SELECT postId, title, description, serviceName as serviceType from posts NATURAL JOIN serviceTypes WHERE userId = ?;", ROW_MAPPER, userId);

        return list;
    }

    public Post create(int userId, int serviceTypeId, String title, String description) {

        /*Habria que checker que el user id existe en serviceProviders y que el serviceTypeId existe tambien*/


        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("title", title);
        args.put("description", description);
        args.put("serviceTypeId", serviceTypeId);
        args.put("userId",userId);

        final Number postId = jdbcInsert.executeAndReturnKey(args);

        /* Insert postId in service provider table */
        //sProviderDao.create(userId, postId.intValue());

        return new Post(postId.intValue(), title, description, String.valueOf(serviceTypeId));
    }

    public Post getPostWithId(int postId) {
        final List<Post> list = jdbcTemplate.query("SELECT postId, title, description, serviceName as serviceType FROM serviceProviders NATURAL JOIN posts NATURAL JOIN serviceTypes WHERE postId = ?;", ROW_MAPPER, postId);
        if(list.size() == 0) {
            return null; // SHOULD BE OPTIONAL
        }
        return list.get(0);
    }

    public List<Post> getPosts() {
        final List<Post> list = jdbcTemplate.query("SELECT postId, title, description, serviceName as serviceType FROM serviceProviders NATURAL JOIN posts;", ROW_MAPPER);
        return list;
    }

    public Post updatePostWithId(int postId, int serviceTypeId, String title, String description) {
        jdbcTemplate.update("UPDATE posts SET title = ?, description = ?, serviceType = ? WHERE postId = ?", title, description, serviceTypeId, postId);

        return getPostWithId(postId);
    }

    public boolean deletePostWithId(int postId) {
        jdbcTemplate.update("DELETE FROM post WHERE postId = ?", postId);
        return true;
    }

}
