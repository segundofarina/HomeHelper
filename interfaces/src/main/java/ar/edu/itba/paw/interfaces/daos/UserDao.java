package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.model.User;

import java.sql.Blob;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    User create(String username, String password, String firstName, String lastName, String email, String phone,Blob image);
}
