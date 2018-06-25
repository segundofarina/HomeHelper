package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.*;

@Repository
public class ReviewJdbcDao implements ReviewDao {

    @Autowired
    UserDao userDao;

    @Autowired
    AppointmentDao appointmentDao;

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
        Date reviewdate;
        int quality;
        int cleanness;
        int price;
        int punctuality;
        int treatment;
        String comment;

        public Row(int userId, int aptitudeId, Date reviewdate, int quality, int cleanness, int price, int punctuality, int treatment, String comment) {
            this.userId = userId;
            this.aptitudeId = aptitudeId;
            this.reviewdate = reviewdate;
            this.quality = quality;
            this.cleanness = cleanness;
            this.price = price;
            this.punctuality = punctuality;
            this.treatment = treatment;
            this.comment = comment;
        }
    }
    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("userId"),rs.getInt("aptitudeId"),rs.getDate("reviewdate"),rs.getInt("quality"),rs.getInt("cleanness"),rs.getInt("price"),rs.getInt("punctuality"),rs.getInt("treatment"),rs.getString("comment"));
        }
    };

    public List<Review> getReviewsOfAptitude(int aptitudeId){
        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM reviews WHERE aptitudeId =?", ROW_MAPPER,aptitudeId);

        List<Review> reviews = new ArrayList<Review>();

        if(dbRowsList.isEmpty()){
            return reviews;
        }

        for(Row row : dbRowsList){
            reviews.add(new Review(row.quality,row.cleanness,row.price,row.punctuality,row.treatment,row.comment,Date.from(Instant.now()), userDao.findById(row.userId).get()));
        }

        return reviews;
    }

    @Override
    public void insertReview(int userId, int aptitudeId, int quality, int cleanness, int price, int punctuality, int treatment, String comment) {

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", userId);
        args.put("aptitudeId",aptitudeId);
        args.put("quality", quality);
        args.put("cleanness", cleanness);
        args.put("price", price);
        args.put("punctuality", punctuality);
        args.put("treatment", treatment);
        if(comment == null) {
            args.put("comment",Types.NULL);
        }else{
            args.put("comment", comment);
        }
        args.put("reviewdate",Date.from(Instant.now()));
        jdbcInsert.execute(args);
    }


    @Override
    public boolean removeReviewsOfAptitude(int aptId) {
        return jdbcTemplate.update("DELETE FROM reviews WHERE aptitudeId = ?", aptId) != 0;
    }
}
