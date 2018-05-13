package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateSProviderForm {

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

    @NotBlank
    private String profileDesc;

    @NotBlank
    private String aptDesc;

    @NotBlank
    private String serviceType;

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

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    public String getAptDesc() {
        return aptDesc;
    }

    public void setAptDesc(String aptDesc) {
        this.aptDesc = aptDesc;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getServiceTypeId() {
        return Integer.valueOf(serviceType);
    }
}
