package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.User;

public interface UserService {

    User findById(int id);

    User findByUsername(String username);

    User create(String username, String password, String firstname, String lastname, String email, String phone);

    boolean login(String username, String password);
}
