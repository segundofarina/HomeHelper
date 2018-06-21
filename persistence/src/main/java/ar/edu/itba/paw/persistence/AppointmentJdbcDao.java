package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.*;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Status;
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
public class AppointmentJdbcDao implements AppointmentDao {

    @Autowired
    UserDao userDao;

    @Autowired
    SProviderDao sProviderDao;

    @Autowired
    STypeDao serviceTypeDao;

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
        int providerId;
        int serviceTypeId;
        Date date;
        String address;
        Status status;
        String jobDescription;
        boolean clientReview;

        public Row(int appointmentId, int userId, int providerId, int serviceTypeId, Date appointmentDate, String address, Status status, String jobDescription, boolean clientReview) {
            this.appointmentId = appointmentId;
            this.userId = userId;
            this.providerId = providerId;
            this.serviceTypeId = serviceTypeId;
            this.date = appointmentDate;
            this.address = address;
            this.status = status;
            this.jobDescription = jobDescription;
            this.clientReview= clientReview;
        }

    }

    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("appointmentId"), rs.getInt("userId"), rs.getInt("providerId"), rs.getInt("serviceTypeId"), rs.getDate("appointmentDate"), rs.getString("address"), Status.getStatus(rs.getString("status")), rs.getString("jobDescription"),rs.getBoolean("clientReview"));
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
            appointments.add(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), sProviderDao.getServiceProviderWithUserId(row.providerId).get(), serviceTypeDao.getServiceTypeWithId(row.serviceTypeId).get(), row.date, row.address, row.status, row.jobDescription,row.clientReview));
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
            appointments.add(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), sProviderDao.getServiceProviderWithUserId(row.providerId).get(), serviceTypeDao.getServiceTypeWithId(row.serviceTypeId).get(), row.date, row.address, row.status, row.jobDescription,row.clientReview));
        }

        return appointments;
    }


    @Override
    public Optional<Appointment> getAppointment(int appointmentId) {
        List<Row> dbRow = jdbcTemplate.query("SELECT * FROM appointments WHERE appointmentId =? ", ROW_MAPPER, appointmentId);

        if (dbRow.size() == 1) {
            Row row = dbRow.get(0);
            return Optional.of(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), sProviderDao.getServiceProviderWithUserId(row.providerId).get(), serviceTypeDao.getServiceTypeWithId(row.serviceTypeId).get(), row.date, row.address, row.status, row.jobDescription,row.clientReview));
        }

        return Optional.empty();
    }

    @Override
    public Appointment addAppointment(int clientId, int providerId, int serviceTypeId, Date date, String address, String jobDescripcion){

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", clientId);
        args.put("providerId", providerId);
        args.put("serviceTypeId", serviceTypeId);
        args.put("appointmentDate", date);
        args.put("address", address);
        args.put("status", "Pending");
        args.put("jobDescription", jobDescripcion);
        args.put("clientReview",false);

        int appointmentId = jdbcInsert.executeAndReturnKey(args).intValue();

        return new Appointment(appointmentId ,userDao.findById(clientId).get(),sProviderDao.getServiceProviderWithUserId(providerId).get(),serviceTypeDao.getServiceTypeWithId(serviceTypeId).get(),date,address,Status.Pending,jobDescripcion,false);

    }

    @Override
    public boolean updateStatusOfAppointment(int appointmentId, Status status) {
        return jdbcTemplate.update("UPDATE appointments SET status =? WHERE appointmentId =?", status.toString(), appointmentId)!=0;
    }

    @Override
    public boolean updateDateOfAppointment(int appointmentId, Date date) {
        return jdbcTemplate.update("UPDATE appointments SET appointmentDate =? WHERE appointmentId =?", date, appointmentId)!=0;
    }

    @Override
    public boolean removeAppointment(int appointmentId) {
        return jdbcTemplate.update("DELETE FROM appointments WHERE appointmentId = ?", appointmentId) != 0;
    }

    @Override
    public void reviewAppointment(int appointmentId, int userId, int aptitudeId, int quality, int cleanness, int price, int punctuality, int treatment, String comment){
        reviewDao.insertReview(userId,aptitudeId,quality,cleanness,price,punctuality,treatment,comment);
        updateReviewClientOfAppointment(appointmentId);
    }

    private void updateReviewClientOfAppointment(int appointmentId) {
        jdbcTemplate.update("UPDATE appointments SET clientReview =? WHERE appointmentId =?", true, appointmentId);
    }
}
