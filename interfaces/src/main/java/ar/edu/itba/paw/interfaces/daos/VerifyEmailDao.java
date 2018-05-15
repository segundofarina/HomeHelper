package ar.edu.itba.paw.interfaces.daos;

import java.util.Optional;

public interface VerifyEmailDao {
    public boolean insert(int userId, String key);

    public Optional<String> getKey(int userId);

    public Optional<Integer> getUserId(String key);

    public boolean deleteEntry(String key);
}
