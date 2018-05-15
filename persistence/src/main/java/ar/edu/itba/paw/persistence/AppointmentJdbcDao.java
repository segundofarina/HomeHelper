package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
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
        Timestamp date;
        String address;
        Status status;
        String jobDescription;

        public Row(int appointmentId, int userId, int providerId, int serviceTypeId, Timestamp appointmentDate, String address, Status status, String jobDescription) {
            this.appointmentId = appointmentId;
            this.userId = userId;
            this.providerId = providerId;
            this.serviceTypeId = serviceTypeId;
            this.date = appointmentDate;
            this.address = address;
            this.status = status;
            this.jobDescription = jobDescription;
        }

    }

    private final static RowMapper<Row> ROW_MAPPER = new RowMapper<Row>() {
        public Row mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Row(rs.getInt("appointmentId"), rs.getInt("userId"), rs.getInt("providerId"), rs.getInt("serviceTypeId"), rs.getTimestamp("appointmentDate"), rs.getString("address"), Status.getStatus(rs.getString("status")), rs.getString("jobDescription"));
        }
    };

    @Override
    public List<Appointment> getAppointmentsByProviderId(int providerId) {
        if(!sProviderDao.getServiceProviderWithUserId(providerId).isPresent()){
            return null;
        }

        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM appointments WHERE providerId =? ", ROW_MAPPER, providerId);

        List<Appointment> appointments = new ArrayList<>();

        for (Row row : dbRowsList) {
            appointments.add(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), sProviderDao.getServiceProviderWithUserId(row.providerId).get(), serviceTypeDao.getServiceTypeWithId(row.serviceTypeId).get(), row.date, row.address, row.status, row.jobDescription));
        }

        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(int userId) {
        if(!userDao.findById(userId).isPresent()){
            return null;
        }

        List<Row> dbRowsList = jdbcTemplate.query("SELECT * FROM appointments WHERE userId =? ", ROW_MAPPER, userId);

        List<Appointment> appointments = new ArrayList<>();

        for (Row row : dbRowsList) {
            appointments.add(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), sProviderDao.getServiceProviderWithUserId(row.providerId).get(), serviceTypeDao.getServiceTypeWithId(row.serviceTypeId).get(), row.date, row.address, row.status, row.jobDescription));
        }

        return appointments;
    }


    @Override
    public Optional<Appointment> getAppointment(int appointmentId) {
        List<Row> dbRow = jdbcTemplate.query("SELECT * FROM appointments WHERE appointmentId =? ", ROW_MAPPER, appointmentId);

        if (dbRow.size() == 1) {
            Row row = dbRow.get(0);
            return Optional.of(new Appointment(row.appointmentId, userDao.findById(row.userId).get(), sProviderDao.getServiceProviderWithUserId(row.providerId).get(), serviceTypeDao.getServiceTypeWithId(row.serviceTypeId).get(), row.date, row.address, row.status, row.jobDescription));
        }

        return Optional.empty();
    }

    @Override
    public Appointment addAppointment(int clientId, int providerId, int serviceTypeId, Timestamp date, String address, String jobDescripcion){

        if(date==null || jobDescripcion==null || address==null){
            return null;
        }

        if (!userDao.findById(clientId).isPresent() || !sProviderDao.getServiceProviderWithUserId(providerId).isPresent() || !serviceTypeDao.getServiceTypeWithId(serviceTypeId).isPresent()) {
           return null;
        }

        final Map<String, Object> args = new HashMap<String, Object>();
        args.put("userId", clientId);
        args.put("providerId", providerId);
        args.put("serviceTypeId", serviceTypeId);
        args.put("appointmentDate", date);
        args.put("address", address);
        args.put("status", "Pending");
        args.put("jobDescription", jobDescripcion);

        int appointmentId = jdbcInsert.executeAndReturnKey(args).intValue();

        //jdbcInsert.execute(args);

        return new Appointment(appointmentId ,userDao.findById(clientId).get(),sProviderDao.getServiceProviderWithUserId(providerId).get(),serviceTypeDao.getServiceTypeWithId(serviceTypeId).get(),date,address,Status.Pending,jobDescripcion);


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
}
