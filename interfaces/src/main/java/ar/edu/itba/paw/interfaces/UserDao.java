package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserDao {

    User findById(int id);
}
