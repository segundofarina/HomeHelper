package ar.edu.itba.paw.homehelper.dto;

public class ActionDto {

    private String action;

    ActionDto() {
    }

    public ActionDto(String action){
        this.action=action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
