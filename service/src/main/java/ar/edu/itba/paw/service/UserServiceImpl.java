package ar.edu.itba.paw.service;


import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


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

    public User create(String username, String password, String firstname, String lastname, String email, String phone,String address) {
        return userDao.create(username, password, firstname, lastname, email,  phone, address);
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


}

