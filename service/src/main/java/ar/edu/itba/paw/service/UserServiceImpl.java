package ar.edu.itba.paw.service;


import ar.edu.itba.paw.interfaces.daos.UserDao;
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
    UserDao userDao;


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

    public User create(String username, String password, String firstname, String lastname, String email, String phone, Blob image) {
        return userDao.create(username, password, firstname, lastname, email,  phone, image);
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
    public BufferedImage getProfileImage(int id){

        try {

            InputStream in = userDao.findById(id).get().getImage().getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            return image;
        } catch (Exception e){
            //habria que hacer algo aca;
        }
        return null;

    }


}

