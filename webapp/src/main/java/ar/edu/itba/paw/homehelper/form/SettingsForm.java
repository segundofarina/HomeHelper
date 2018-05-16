package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SettingsForm {

    private int savedImgId;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-Z ]*")
    private String firstname;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-Z ]*")
    private String lastname;

    @Email
    @NotBlank
    private String email;

    @Size(max = 100)
    @NotBlank
    @Pattern(regexp = "[0-9+]*")
    private String phone;

    @Valid
    private SettingsPassForm passwordForm = new SettingsPassForm();

    private MultipartFile profilePicture;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SettingsPassForm getPasswordForm() {
        return passwordForm;
    }

    public void setPasswordForm(SettingsPassForm passwordForm) {
        this.passwordForm = passwordForm;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getSavedImgId() {
        return savedImgId;
    }

    public void setSavedImgId(int savedImgId) {
        this.savedImgId = savedImgId;
    }
}
