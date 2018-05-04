package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.model.Review;
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
public class ReviewJdbcDao implements ReviewDao {

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReviewJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reviews");
    }


    private static class Row{
        int userId;
        int aptitudeId;
        String reviewdate;
        int rating;
        String comment;

        public Row(int userId, int aptitudeId, String reviewdate, int rating, String comment) {
            this.userId = userId;
            this.aptitudeId = aptitudeId;
            this.reviewdate = reviewdate;
            this.rating = rating;
            this.comment = comment;
        }
    }
    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("userId"),rs.getInt("aptitudeId"),rs.getString("reviewdate"),rs.getInt("rating"),rs.getString("comment"));
        }
    };

    public List<Review> getReviewsOfAptitude(int aptitudeId){
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM reviews WHERE aptitudeId =?", ROW_MAPPER,aptitudeId);

        List<Review> reviews = new ArrayList<Review>();

        for(Row row : dbRowsList){
            reviews.add(new Review(row.rating,row.comment,row.reviewdate));
        }

        return reviews;
    }

    @Override
    public boolean insertReview(int userId, int aptitudeId, int rating, String comment) {
        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", userId);
        args.put("aptitudeId",aptitudeId);
        args.put("rating", rating);
        args.put("comment", comment);

        jdbcInsert.execute(args);

        return true;
    }
}
