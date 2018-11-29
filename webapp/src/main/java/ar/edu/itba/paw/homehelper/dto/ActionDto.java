package ar.edu.itba.paw.homehelper.dto;

public enum ActionDto {
    CONFIRM, COMPLETE, REJECT;

    public static ActionDto parse(String action) {
        if("confirm".compareToIgnoreCase(action)==0){
           return CONFIRM;
       }else if("complete".compareToIgnoreCase(action)==0){
           return COMPLETE;
       }else if("reject".compareToIgnoreCase(action)==0){
           return REJECT;
       }else{
           return null;
       }
    }

}
