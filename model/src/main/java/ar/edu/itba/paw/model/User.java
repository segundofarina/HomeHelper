package ar.edu.itba.paw.model;

public class User {

    private String username;
    private int id;
    private String password;

    public User(String username,String password, int userid) {
        this.username = username;
        id = userid;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
