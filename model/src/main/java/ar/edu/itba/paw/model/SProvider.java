package ar.edu.itba.paw.model;

import java.util.List;
import java.util.Set;

public class SProvider extends User{
    private String description;
    private List<Aptitude> aptitudes;

    public SProvider(User user, String description, List<Aptitude> aptitudes) {
        super(user.getUsername(),user.getId(),user.getPassword(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhone());
        this.description = description;
        this.aptitudes = aptitudes;
    }

    public String getDescription() {
        return description;
    }

    public List<Aptitude> getAptitudes() {
        return aptitudes;
    }


}
