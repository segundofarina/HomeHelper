package ar.edu.itba.paw.model;

public class User {

    private String name;
    private int id;

    public User(String username, int userid) {
        name = username;
        id = userid;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
