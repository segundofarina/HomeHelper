package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.User;

public class BasicUserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String pictureUrl;

    public BasicUserDto() {
    }

    public BasicUserDto(User user) {
        this.id = user.getId();
        firstName = user.getFirstname();
        lastName = user.getLastname();
        pictureUrl = "http://pawserver.it.itba.edu.ar/paw-2018a-4/api/"+"users/"+this.id+"/image";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
