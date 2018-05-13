package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//@FieldMatch(first = "password", second = "repeatPassword", message = "The password fields must match")
public class SignUpForm {


    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-Z ]*")
    private String firstname;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-Z ]*")
    private String lastname;


    @Size(min = 6, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9]*")
    private String username;

    @Valid
    private PasswordForm passwordForm = new PasswordForm();

    @Email
    @NotBlank
    private String email;

    @Size(max = 100)
    @NotBlank
    @Pattern(regexp = "[0-9+]*")
    private String phone;

    private MultipartFile profilePicture;

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PasswordForm getPasswordForm() {
        return passwordForm;
    }

    public void setPasswordForm(PasswordForm passwordForm) {
        this.passwordForm = passwordForm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
