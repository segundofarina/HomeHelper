package ar.edu.itba.paw.model;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.util.Arrays;

public class User {

    private String username;
    private int id;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private byte[] image;
    private String address;



    public User(String username, int id, String password, String firstname, String lastname, String email, String phone, String address,byte[] image) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public byte[] getImage() {
        return this.image;
    }
    public String getAddress() {
        return address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (!getUsername().equals(user.getUsername())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (getFirstname() != null ? !getFirstname().equals(user.getFirstname()) : user.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(user.getLastname()) : user.getLastname() != null)
            return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (getPhone() != null ? !getPhone().equals(user.getPhone()) : user.getPhone() != null) return false;
        if (!Arrays.equals(getImage(), user.getImage())) return false;
        return getAddress() != null ? getAddress().equals(user.getAddress()) : user.getAddress() == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getId();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (getFirstname() != null ? getFirstname().hashCode() : 0);
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getImage());
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        return result;
    }
}
