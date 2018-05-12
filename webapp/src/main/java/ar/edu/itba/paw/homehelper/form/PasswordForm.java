package ar.edu.itba.paw.homehelper.form;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "repeatPassword", message = "The password fields must match")
public class PasswordForm {

    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 6, max = 100)
    private String repeatPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
