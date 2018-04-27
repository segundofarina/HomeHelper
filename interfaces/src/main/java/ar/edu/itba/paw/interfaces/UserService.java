package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserService {

    User findById(int id);

    User create(String username,String password);
}
