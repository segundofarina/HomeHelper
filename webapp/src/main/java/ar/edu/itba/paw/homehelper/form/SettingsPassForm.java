package ar.edu.itba.paw.homehelper.form;

import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "repeatPassword", message = "The password fields must match")
public class SettingsPassForm {
    private String password;

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
