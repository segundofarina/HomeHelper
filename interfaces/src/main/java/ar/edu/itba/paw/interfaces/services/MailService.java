package ar.edu.itba.paw.interfaces.services;

public interface MailService {

    public void sendConfirmationEmail(String email, int userId);
}
