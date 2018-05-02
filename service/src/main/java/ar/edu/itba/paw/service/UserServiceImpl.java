package ar.edu.itba.paw.service;


import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;


    public User findById(int id) {
        return userDao.findById(id).get();
    }

    public User create(String username, String password, String firstname, String lastname, String email, String phone) {
        return userDao.create(username, password, firstname, lastname, email,  phone);
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

