package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.User;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

public interface UserService {

    User findById(int id);

    User findByUsername(String username);

    User create(String username, String password, String firstname, String lastname, String email, String phone, Blob image);

    boolean login(String username, String password);

    BufferedImage getProfileImage(int id);

}
