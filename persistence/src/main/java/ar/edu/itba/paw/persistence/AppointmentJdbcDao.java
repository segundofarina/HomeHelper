package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.*;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class AppointmentJdbcDao implements AppointmentDao {

    @Autowired
    UserDao userDao;

    @Autowired
    SProviderDao sProviderDao;

    @Autowired
    STypeDao serviceTypeDao;

    @Autowired
    AptitudeDao aptitudeDao;

    @Autowired
    ReviewDao reviewDao;

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public AppointmentJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("appointments").usingGeneratedKeyColumns("appointmentid");
    }

    private static class Row {
        int appointmentId;
        int userId;
        int aptitudeId;
        int reviewId;
        Timestamp date;
        String address;
        Status status;
        String jobDescription;

        public Row(int appointmentId, int userId, int aptitudeId, int reviewId,Timestamp appointmentDate, String address, Status status, String jobDescription) {
            this.appointmentId = appointmentId;
            this.userId = userId;
            this.aptitudeId = aptitudeId;
            this.reviewId= reviewId;
            this.date = appointmentDate;
            this.address = address;
            this.status = status;
            this.jobDescription = jobDescription;
        }

    }

    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("appointmentId"), rs.getInt("userId"), rs.getInt("aptitudeId"), rs.getInt("reviewId"),rs.getTimestamp("appointmentDate"), rs.getString("address"), Status.getStatus(rs.getString("status")), rs.getString("jobDescription"));
        }
    };

    @Override
    public List<Appointment> getAppointmentsByProviderId(int providerId) {

        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM appointments WHERE providerId =? ", ROW_MAPPER, providerId);

        List<Appointment> appointments = new ArrayList<>();

        if(dbRowsList.isEmpty()){
            return appointments;
        }

        for (Row row : dbRowsList) {
            appointments.add(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), aptitudeDao.getAptitude(row.aptitudeId).get(), row.date, row.address, row.status, row.jobDescription));
        }

        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(int userId) {

        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM appointments WHERE userId =? ", ROW_MAPPER, userId);

        List<Appointment> appointments = new ArrayList<>();

        if(dbRowsList.isEmpty()){
            return appointments;
        }


        for (Row row : dbRowsList) {
            appointments.add(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), aptitudeDao.getAptitude(row.aptitudeId).get(), row.date, row.address, row.status, row.jobDescription));
        }

        return appointments;
    }


    @Override
    public Optional<Appointment> getAppointment(int appointmentId) {
        List<Row> dbRow = jdbcTemplate.query("SELECT * FROM appointments WHERE appointmentId =? ", ROW_MAPPER, appointmentId);

        if (dbRow.size() == 1) {
            Row row = dbRow.get(0);
            return Optional.of(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), aptitudeDao.getAptitude(row.aptitudeId).get(), row.date, row.address, row.status, row.jobDescription));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Appointment> addAppointment(int clientId, int aptitudeId, Timestamp date, String address, String jobDescripcion){

        if(date==null || jobDescripcion==null || address==null){
            return Optional.empty();
        }

        if (!userDao.findById(clientId).isPresent() || !aptitudeDao.getAptitude(aptitudeId).isPresent() ) {
           return Optional.empty();
        }

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", clientId);
        args.put("aptitudeId", aptitudeId);
        args.put("appointmentDate", date);
        args.put("address", address);
        args.put("status", "Pending");
        args.put("jobDescription", jobDescripcion);

        int appointmentId = jdbcInsert.executeAndReturnKey(args).intValue();

        return Optional.of(new Appointment(appointmentId, userDao.findById(clientId).get(), aptitudeDao.getAptitude(aptitudeId).get(), date, address, Status.Pending, jobDescripcion));


    }

    @Override
    public boolean updateStatusOfAppointment(int appointmentId, Status status) {
        Optional<Appointment> appointment = getAppointment(appointmentId);
        if (!appointment.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE appointments SET status =? WHERE appointmentId =?", status.toString(), appointmentId);
        return true;
    }

    @Override
    public boolean updateDateOfAppointment(int appointmentId, Timestamp date) {
        Optional<Appointment> appointment = getAppointment(appointmentId);
        if (!appointment.isPresent()) {
            return false;
        }
        jdbcTemplate.update("UPDATE appointments SET appointmentDate =? WHERE appointmentId =?", date, appointmentId);
        return true;
    }

    @Override
    public boolean removeAppointment(int appointmentId) {
        return jdbcTemplate.update("DELETE FROM appointments WHERE appointmentId = ?", appointmentId) != 0;
    }

    @Override
    public Optional<Review> getReviewOfAppointment(int appointmentId) {
        List<Row> dbRow = jdbcTemplate.query("SELECT * FROM appointments WHERE appointmentId =? ", ROW_MAPPER, appointmentId);

        if(dbRow.isEmpty()){
            return Optional.empty();
        }

        Row row = dbRow.get(0);
        if(row.reviewId == -1){
            return Optional.empty();
        }

        return reviewDao.getReview(row.reviewId);

    }
}
