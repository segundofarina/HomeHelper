package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.model.User;

public interface MailService {

    void sendConfirmationEmail(int userId,String key);

    User verifyUserKey(String key);
}
