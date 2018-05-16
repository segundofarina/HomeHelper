package ar.edu.itba.paw.service;


import ar.edu.itba.paw.interfaces.daos.AppointmentDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AppointmentDao appointmentDao;


    public User findById(int id) {
        return userDao.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userDao.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    public User create(String username, String password, String firstname, String lastname, String email, String phone,String address,byte[] image) {
        return userDao.create(username, password, firstname, lastname, email,  phone,address ,image);

    }

    @Override
    public boolean login(String username, String password) {
        Optional<User> response =userDao.findByUsername(username);
        if(!response.isPresent()){
            return false;
        }
        User user = response.get();
        if(user.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public byte[] getProfileImage(int id) {

        if(userDao.findById(id).isPresent()) {

            return userDao.findById(id).get().getImage();
        }
        return null;

    }

    @Override
    public boolean updatePasswordOfUser(int userId, String password) {
        return userDao.updatePasswordOfUser(userId,password);
    }

    @Override
    public boolean updateFirstNameOfUser(int userId, String firstname) {
        return userDao.updateFirstNameOfUser(userId,firstname);
    }

    @Override
    public boolean updateLastNameOfUser(int userId, String lastname) {
        return userDao.updateLastNameOfUser(userId,lastname);
    }

    @Override
    public boolean updateEmailOfUser(int userId, String email) {
        return userDao.updateEmailOfUser(userId,email);
    }

    @Override
    public boolean updatePhoneOfUser(int userId, String phone) {
        return userDao.updatePhoneOfUser(userId,phone);
    }

    @Override
    public boolean updateImageOfUser(int userId, byte[] image) {
        return userDao.updateImageOfUser(userId,image);
    }

    @Override
    public boolean updateAddressOfUser(int userId, String address) {
        return userDao.updateAddressOfUser(userId,address);
    }

}

