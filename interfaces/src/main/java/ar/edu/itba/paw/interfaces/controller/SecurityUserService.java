package ar.edu.itba.paw.interfaces.controller;

import ar.edu.itba.paw.model.User;

public interface SecurityUserService {

    User getLoggedInUser();
}
