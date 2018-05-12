package ar.edu.itba.paw.interfaces.daos;

public interface VerifyEmailDao {
    public boolean insert(int userId, String key);

    public String getKey(int userId);
}
