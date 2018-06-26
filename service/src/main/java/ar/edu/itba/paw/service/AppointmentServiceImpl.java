package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.interfaces.daos.AptitudeDao;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentDao appointmentDao;

    @Autowired
    AptitudeDao aptitudeDao;

    @Override
    public List<Appointment> getAppointmentsByProviderId(int providerId) {
        return appointmentDao.getAppointmentsByProviderId(providerId);
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(int userId) {
        return appointmentDao.getAppointmentsByUserId(userId);
    }

    @Override
    public List<Appointment> getAppointmentsByProviderId(int providerId, Status status) {
        List<Appointment> appointments = getAppointmentsByProviderId(providerId);
        List<Appointment> ans = new ArrayList<>();

        for(Appointment appointment : appointments){
            if(appointment.getStatus().equals(status)){
                ans.add(appointment);
            }
        }

        return ans;
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(int userId, Status status) {
        List<Appointment> appointments = getAppointmentsByUserId(userId);
        List<Appointment> ans = new ArrayList<>();

        for(Appointment appointment : appointments){
            if(appointment.getStatus().equals(status)){
                ans.add(appointment);
            }
        }

        return ans;
    }

    @Override
    public Appointment getAppointment(int appointmentId) {
        Optional<Appointment> appointment = appointmentDao.getAppointment(appointmentId);
       if(appointment.isPresent()){
           return appointment.get();
       }
       return null;
    }


    @Override
    public Appointment addAppointment(int clientId, int providerId, int serviceTypeId, String date, String address, String jobDescripcion) {
        Optional<Appointment> appointment = appointmentDao.addAppointment(clientId, providerId, serviceTypeId, stringToTimestamp(date), address, jobDescripcion);
        return appointment.isPresent()?appointment.get():null;
    }

    @Override
    public boolean confirmAppointment(int appointmentId) {
        return appointmentDao.updateStatusOfAppointment(appointmentId,Status.Confirmed);
    }

    @Override
    public boolean completedAppointment(int appointmentId) {
        return appointmentDao.updateStatusOfAppointment(appointmentId,Status.Done);
    }

    @Override
    public List<Appointment> getPendingAppointmentWithProviderId(int providerId) {
        List<Appointment> appointments = getAppointmentsByProviderId(providerId);
        List<Appointment> ans = new ArrayList<>();

        for(Appointment appointment : appointments){
            if(appointment.getStatus().equals(Status.Pending) || appointment.getStatus().equals(Status.Confirmed)){
                ans.add(appointment);
            }
        }

        return ans;
    }

    @Override
    public List<Appointment> getPendingAppointmentWithUserId(int userId) {
        List<Appointment> appointments = getAppointmentsByUserId(userId);
        List<Appointment> ans = new ArrayList<>();

        for(Appointment appointment : appointments) {
            if(appointment.getStatus().equals(Status.Pending) || appointment.getStatus().equals(Status.Confirmed)){
                ans.add(appointment);
            }
        }

        return ans;
    }

    @Override
    public List<Appointment> getCompleteAppointmentWithProviderId(int providerId) {
        List<Appointment> appointments = getAppointmentsByProviderId(providerId);
        List<Appointment> ans = new ArrayList<>();

        for(Appointment appointment : appointments){
            if(appointment.getStatus().equals(Status.Done) || appointment.getStatus().equals(Status.Reject)){
                ans.add(appointment);
            }
        }

        return ans;
    }

    @Override
    public List<Appointment> getCompleteAppointmentWithUserId(int userId) {
        List<Appointment> appointments = getAppointmentsByUserId(userId);
        List<Appointment> ans = new ArrayList<>();

        for(Appointment appointment : appointments) {
            if(appointment.getStatus().equals(Status.Done) || appointment.getStatus().equals(Status.Reject)){
                ans.add(appointment);
            }
        }

        return ans;
    }

    @Override
    public boolean updateDateOfAppointment(int appointmentId, String date) {
        Date appointmentDate = tryParse(date);
        if(appointmentDate==null){
            return false;
        }
        return appointmentDao.updateDateOfAppointment(appointmentId, (Timestamp) appointmentDate);
    }

    @Override
    public boolean rejectAppointment(int appointmentId) {
        return appointmentDao.updateStatusOfAppointment(appointmentId,Status.Reject);
    }

    @Override
    public List<Appointment> getLatestPendingAppointmentWithProviderId(int providerId) {
        final List<Appointment> ap = getPendingAppointmentWithProviderId(providerId);
        int start = 0, end = ap.size() >= 4 ? 4 : ap.size();
        return ap.subList(start, end);
    }


    @Override
    public void reviewAppointment(int appointmentId, int userId, int serviceTypeId, int quality, int cleanness, int price, int punctuality, int treatment, String comment) {
        int aptitudeId = aptitudeDao.getAptitudeId(userId, serviceTypeId);
        appointmentDao.reviewAppointment(appointmentId,userId,aptitudeId,quality,cleanness,price,punctuality,treatment,comment);
    }

    private Date stringToTimestamp(String str) {
        return new Date(tryParse(str).getTime());
    }

    private Date tryParse(String date) {
        List<String> dateFormats = Arrays.asList("dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy");
        for(String dateFormat : dateFormats) {
            try {
                return new SimpleDateFormat(dateFormat).parse(date);
            } catch (ParseException e) {

            }
        }

        //invalid date time
        return null;
    }


}
