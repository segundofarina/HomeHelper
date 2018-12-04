package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.UserImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Transactional
    @Override
    public User findById(int id) {
        Optional<User> user = userDao.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        Optional<User> user = userDao.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Transactional
    @Override
    public User create(String username, String password, String firstname, String lastname, String email, String phone, String address, byte[] image) {
        return userDao.create(username, password, firstname, lastname, email, phone, address, image);

    }

    @Transactional
    @Override
    public boolean login(String username, String password) {
        Optional<User> response = userDao.findByUsername(username);
        if (!response.isPresent()) {
            return false;
        }
        User user = response.get();
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public byte[] getProfileImage(int userId) {
        return userDao.getImage(userId).map(UserImage::getImage).orElse(null);

    }

    @Transactional
    @Override
    public boolean updatePasswordOfUser(int userId, String password) {
        return userDao.updatePasswordOfUser(userId, password);
    }

    @Transactional
    @Override
    public boolean updateFirstNameOfUser(int userId, String firstname) {
        return userDao.updateFirstNameOfUser(userId, firstname);
    }

    @Transactional
    @Override
    public boolean updateLastNameOfUser(int userId, String lastname) {
        return userDao.updateLastNameOfUser(userId, lastname);
    }

    @Transactional
    @Override
    public boolean updateEmailOfUser(int userId, String email) {
        return userDao.updateEmailOfUser(userId, email);
    }

    @Transactional
    @Override
    public boolean updatePhoneOfUser(int userId, String phone) {
        return userDao.updatePhoneOfUser(userId, phone);
    }

    @Transactional
    @Override
    public boolean updateImageOfUser(int userId, byte[] image) {
        return userDao.updateImageOfUser(userId, image);
    }

    @Transactional
    @Override
    public boolean updateAddressOfUser(int userId, String address) {
        return userDao.updateAddressOfUser(userId, address);
    }

}

