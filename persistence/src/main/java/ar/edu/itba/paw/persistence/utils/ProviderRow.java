package ar.edu.itba.paw.persistence.utils;

public class ProviderRow{
    int id;

    String username;

    String password;

    String firstname;

    String lastname;

    String email;

    String phone;

    String address;

    boolean verified;

    String description;

    public ProviderRow(int id, String username, String password, String firstname, String lastname, String email, String phone, String address, boolean verified, String description) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.verified = verified;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getDescription() {
        return description;
    }
}