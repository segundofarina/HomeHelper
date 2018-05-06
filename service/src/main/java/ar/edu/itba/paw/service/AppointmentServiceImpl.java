package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentDao appointmentDao;

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
            if(appointment.getEstatus().equals(status)){
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
            if(appointment.getEstatus().equals(status)){
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
    public Integer getAppointmentId(int clientId, int providerId, Timestamp date, String address) {
        Optional<Integer> id = appointmentDao.getAppointmentId(clientId,providerId,date,address);
        if(id.isPresent()){
            return id.get();
        }
        return null;
    }

    @Override
    public boolean addAppointment(int clientId, int providerId, int serviceTypeId, Timestamp date, String address, String jobDescripcion) {
        return appointmentDao.addAppointment(clientId,providerId,serviceTypeId,date,address,jobDescripcion);
    }

    @Override
    public boolean confirmAppointment(int appointmentId) {
        return appointmentDao.updateStatusOfAppointment(appointmentId,Status.Confirmed);
    }

    @Override
    public boolean completedAppointment(int appointmentId) {
        return appointmentDao.updateStatusOfAppointment(appointmentId,Status.Done);
    }

}
