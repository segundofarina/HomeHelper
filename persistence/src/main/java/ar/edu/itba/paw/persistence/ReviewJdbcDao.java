package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.SProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class ReviewJdbcDao implements ReviewDao {

    @Autowired
    UserDao userDao;

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReviewJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reviews");
    }


    private static class Row{
        int appointmentId;
        Timestamp reviewdate;
        int quality;
        int cleanness;
        int price;
        int punctuality;
        int treatment;
        String comment;

        public Row(int appointmentId, Timestamp reviewdate, int quality, int cleanness, int price, int punctuality, int treatment, String comment) {
            this.appointmentId = appointmentId;
            this.reviewdate = reviewdate;
            this.quality = quality;
            this.cleanness = cleanness;
            this.price = price;
            this.punctuality = punctuality;
            this.treatment = treatment;
            this.comment = comment;
        }
    }
    private final static RowMapper<Review> ROW_MAPPER = new RowMapper<Review>() {
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Review(rs.getInt("appointmentId"),rs.getInt("quality"),rs.getInt("cleanness"),rs.getInt("price"),rs.getInt("punctuality"),rs.getInt("treatment"),rs.getString("description"),rs.getTimestamp("reviewdate"));
        }
    };

    @Override
    public boolean insertReview(int appointmentId, int quality, int cleanness, int price, int punctuality, int treatment, String comment) {

        if(comment==null){
            return false;
        }

       if(!isValidCalification(quality) || !isValidCalification(cleanness) || !isValidCalification(price) || !isValidCalification(punctuality) || !isValidCalification(treatment)){
           return false;
       }

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("appointmentId",appointmentId);
        args.put("quality", quality);
        args.put("cleanness", cleanness);
        args.put("price", price);
        args.put("punctuality", punctuality);
        args.put("treatment", treatment);
        args.put("comment", comment);
        args.put("reviewdate",Timestamp.from(Instant.now()));

        try {
            jdbcInsert.execute(args);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    private boolean isValidCalification(int calification) {

        if(calification>0 && calification<6){
            return true;
        }

        return false;
    }

    @Override
    public Optional<Review> getReview(int appointmentId) {
        List<Review> dbRowsList = jdbcTemplate.query("SELECT * FROM reviews WHERE appointmentId =?", ROW_MAPPER, appointmentId);

        if (dbRowsList.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(dbRowsList.get(0));
    }

    @Override
    public List<Review> getReviewsOfAptitude(int aptId) {
        List<Review> list = jdbcTemplate.query("SELECT appointmentId,reviewdate,quality,cleanness,price,punctuality,treatment," +
                "description FROM reviews NATURAL JOIN  appointments WHERE aptitudeId =?", ROW_MAPPER, aptId);


        return list;
    }

    @Override
    public boolean removeReview(int appointmentId){
        return jdbcTemplate.update("DELETE FROM reviews WHERE appointmentId = ?", appointmentId) != 0;
    }

}
